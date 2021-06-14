package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaEspecificacao;
import br.com.ventisol.sankhya.model.vo.SankhyaProduto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecificacaoDao {

    Connection connection = null;

    public EspecificacaoDao(Connection connection) {
        this.connection = connection;
    }

    public EspecificacaoDao() {
    }

    public List<SankhyaEspecificacao> buscarSankhyaProdutosEspecificacoes(SankhyaProduto sankhyaProduto) throws SQLException {
        List<SankhyaEspecificacao> sankhyaEspecificacaos = new ArrayList<>();
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt = connection.prepareCall(""
                + "SELECT  "
                + "    NUMPRO "
                + "    , NUESPE "
                + "    , NOME "
                + "    , VALOR "
                + "    , TIPO  "
                + "FROM  "
                + "    AD_HMPPES PES "
                + "WHERE PES.NUMPRO = ?");
        stmt.setInt(1, sankhyaProduto.getNumPro());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaEspecificacao especificacao = new SankhyaEspecificacao();
            especificacao.setNome(rs.getString("NOME"));
            especificacao.setValor(rs.getString("VALOR"));
            especificacao.setTipo(rs.getString("TIPO"));
            especificacao.setNumEspe(rs.getInt("NUESPE"));
            especificacao.setNumPro(rs.getInt("NUMPRO"));
            sankhyaEspecificacaos.add(especificacao);
        }
        return sankhyaEspecificacaos;
    }

    public List<SankhyaEspecificacao> buscarSankhyaVariacaoEspecificacoes(SankhyaProduto sankhyaProduto) throws SQLException {
        List<SankhyaEspecificacao> sankhyaEspecificacaos = new ArrayList<>();
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "SELECT  "
                + "    NUMPRO "
                + "    , NUESPE "
                + "    , NOME "
                + "    , VALOR "
                + "    , TIPO  "
                + "FROM  "
                + "    AD_HMPVES vES "
                + "WHERE vES.NUMPRO = ?");
        stmt.setInt(1, sankhyaProduto.getNumPro());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaEspecificacao especificacao = new SankhyaEspecificacao();
            especificacao.setNome(rs.getString("NOME"));
            especificacao.setValor(rs.getString("VALOR"));
            especificacao.setTipo(rs.getString("TIPO"));
            especificacao.setNumEspe(rs.getInt("NUESPE"));
            especificacao.setNumPro(rs.getInt("NUMPRO"));
            sankhyaEspecificacaos.add(especificacao);
        }
        return sankhyaEspecificacaos;
    }

    public int inserir(SankhyaEspecificacao especificacao, String sku) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "INSERT INTO "
                + "    AD_HMPPES "
                + " (NUESPE,NUMPRO, NOME, VALOR, TIPO) "
                + " VALUES( "
                + " (SELECT NVL(MAX(NUESPE) + 1,1) FROM AD_HMPPES)"
                + ", (SELECT NUMPRO FROM AD_HMPPRO WHERE SKU = ?)"
                + ", ?"
                + ", ?"
                + ", ?"
                + ")"
        );
        stmt.setString(1, sku);
        stmt.setString(2, especificacao.getNome());
        stmt.setString(3, especificacao.getValor());
        stmt.setString(4, especificacao.getTipo());
        int executeUpdate = stmt.executeUpdate();
        return executeUpdate;
    }


}
