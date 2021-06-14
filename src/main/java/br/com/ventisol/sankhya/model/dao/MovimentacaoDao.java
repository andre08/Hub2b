package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaMovimentacao;
import br.com.ventisol.util.Logs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDao {

    Connection connection = null;

    public MovimentacaoDao(Connection connection) {
        this.connection = connection;
    }

    public MovimentacaoDao() {
    }

    public List<SankhyaMovimentacao> buscarMovimentacoes() throws SQLException {
        List<SankhyaMovimentacao> faturamentos = new ArrayList<>();
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT  "
                + "   1 AS VLRMOV, PRO.CODPROD, QTDNEG, VLRUNIT, NUNOTA, NVL(ESTOQUEATUAL,0) AS ESTOQUEATUAL, PRO.SKU "
                + "FROM  "
                + "    TGFITE ITE "
                + "INNER JOIN AD_HMPPRO PRO ON "
                + "    PRO.CODPROD = ITE.CODPROD     "
                + "WHERE  "
                + "    CODLOCALORIG = (SELECT CODLOCAL FROM AD_HMPPREF) AND SEQUENCIA < 0 "
                + "    AND NOT EXISTS (SELECT 1 FROM AD_HMPMOV WHERE NUNOTA = ITE.NUNOTA AND CODPROD = ITE.CODPROD) "
                + "UNION ALL         "
                + "SELECT  "
                + "    -1 AS VLRMOV, PRO.CODPROD, QTDNEG, VLRUNIT, NUNOTA,  NVL(ESTOQUEATUAL,0) AS ESTOQUEATUAL, PRO.SKU "
                + "FROM  "
                + "    TGFITE ITE "
                + "INNER JOIN AD_HMPPRO PRO ON "
                + "    PRO.CODPROD = ITE.CODPROD        "
                + "WHERE  "
                + "    CODLOCALORIG = (SELECT CODLOCAL FROM AD_HMPPREF) AND SEQUENCIA > 0 "
                + "    AND NOT EXISTS (SELECT 1 FROM AD_HMPMOV WHERE NUNOTA = ITE.NUNOTA AND CODPROD = ITE.CODPROD) ";
        stmt = connection.prepareCall(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaMovimentacao sankhyaMovimentacao = new SankhyaMovimentacao();
            sankhyaMovimentacao.setCodProd(rs.getInt("CODPROD"));
            sankhyaMovimentacao.setNuNota(rs.getInt("NUNOTA"));
            sankhyaMovimentacao.setQtdNeg(rs.getInt("QTDNEG"));
            sankhyaMovimentacao.setSku(rs.getString("SKU"));
            sankhyaMovimentacao.setVlrMov(rs.getInt("VLRMOV"));
            sankhyaMovimentacao.setEstoqueAtual(rs.getInt("ESTOQUEATUAL"));
            sankhyaMovimentacao.setVlrUnit(rs.getDouble("VLRUNIT"));
            faturamentos.add(sankhyaMovimentacao);
        }
        return faturamentos;
    }

    public void cadastrarMovimentacao(SankhyaMovimentacao sankhyaMovimentacao) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall("BEGIN INSERT INTO AD_HMPMOV( NUMOV, VLRMOV, CODPROD, QTDNEG, VLRUNIT, NUNOTA) VALUES ((SELECT NVL(MAX(NUMOV) + 1,1) FROM AD_HMPMOV),?,?,?,?,?)  RETURNING NUMOV INTO ? ; END;");
        stmt.setInt(1, sankhyaMovimentacao.getVlrMov());
        stmt.setInt(2, sankhyaMovimentacao.getCodProd());
        stmt.setInt(3, sankhyaMovimentacao.getQtdNeg());
        stmt.setDouble(4, sankhyaMovimentacao.getVlrUnit());
        stmt.setInt(5, sankhyaMovimentacao.getNuNota());
        stmt.registerOutParameter(6, java.sql.Types.NUMERIC);
        stmt.executeUpdate();
        sankhyaMovimentacao.setNuMov(stmt.getInt(6));
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Gerado a Movimentacao: " + sankhyaMovimentacao.getNuMov() + "!", Logs.COLOR_SUCESSO);
    }

}
