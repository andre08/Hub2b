package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.hub2b.model.produto.Produto;
import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaAnuncio;
import br.com.ventisol.util.Converter;
import br.com.ventisol.util.Logs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnuncioDao {

    Connection connection = null;

    public Integer inserir(Produto produto) throws SQLException {
        try {
            connection = ConexaoOracle.ObterConexao();
            CallableStatement stmt = connection.prepareCall(
                    "INSERT INTO AD_HMPANU(NUMPRO, NUANU, CODMPK, SKULOJISTA, VLRPRECOANTERIOR,VLRPRECO, PROCESSADO,CONTROLE,STATUS) "
                    + "VALUES( "
                    + "(SELECT NUMPRO FROM AD_HMPPRO WHERE SKU = ? AND ROWNUM = 1)" // NUMPRO
                    + ", (SELECT NVL((MAX(NUANU) + 1),1) FROM AD_HMPANU) " // NUANU
                    + ",(SELECT NUMMKTP FROM AD_HMPMKTP WHERE CANALDEVENDA = ?) " // CODMPK
                    + ", ?,?,?,'S','INTEGRACAO',?) "
            );
            Integer codStatus;
            if (produto.getStatus().equals("Synchronized")) {
                codStatus = (3);
            } else {
                codStatus = (1);
            }
            stmt.setString(1, produto.getSku());
            stmt.setDouble(2, produto.getSalesChannel());
            stmt.setString(3, produto.getMarketplaceSKU());
            stmt.setDouble(4, produto.getPriceBase());
            stmt.setDouble(5, produto.getPriceSale());
            stmt.setInt(6, codStatus);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, e.getMessage(), Logs.COLOR_ERRO);
            return null;
        }

    }

    public AnuncioDao(Connection connection) {
        this.connection = connection;
    }

    public AnuncioDao() {
    }

    public List<SankhyaAnuncio> buscarAnunciosDeProdutosPendentes() throws SQLException {
        List<SankhyaAnuncio> anuncios = new ArrayList<>();

        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT  "
                + "    ANU.NUMPRO,ANU.NUANU,ANU.CODMPK,ANU.VLRPRECOANTERIOR,ANU.VLRPRECO,ANU.DTINICIAL,ANU.DTFINAL,ANU.PROCESSADO,MKP.CANALDEVENDA, MPRO.SKU, ANU.STATUS "
                + "FROM  "
                + "   AD_HMPANU ANU "
                + "INNER JOIN AD_HMPPRO MPRO ON "
                + "   MPRO.NUMPRO = ANU.NUMPRO "
                + "INNER JOIN AD_HMPMKTP MKP ON  "
                + "   ANU.CODMPK = MKP.NUMMKTP "
                + "WHERE ANU.PROCESSADO = 'N' "
                + " OR STATUS = 2";
        stmt = connection.prepareCall(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaAnuncio sankhyaAnuncio = new SankhyaAnuncio();
            sankhyaAnuncio.setNumPro(rs.getInt("NUMPRO"));
            sankhyaAnuncio.setNumAnu(rs.getInt("NUANU"));
            sankhyaAnuncio.setCodMpk(rs.getInt("CODMPK"));
            sankhyaAnuncio.setVlrPrecoAnterior(rs.getDouble("VLRPRECOANTERIOR"));
            sankhyaAnuncio.setVlrPreco(rs.getDouble("VLRPRECO"));
            sankhyaAnuncio.setDtInicial(Converter.dataSqlParaUtil(rs.getTimestamp("DTINICIAL")));
            sankhyaAnuncio.setDtFinal(Converter.dataSqlParaUtil(rs.getTimestamp("DTFINAL")));
            sankhyaAnuncio.setProcessado(rs.getString("PROCESSADO"));
            sankhyaAnuncio.setCanalDeVenda(rs.getInt("CANALDEVENDA"));
            sankhyaAnuncio.setSku(rs.getString("SKU"));
            sankhyaAnuncio.setStatus(rs.getInt("STATUS"));
            anuncios.add(sankhyaAnuncio);
        }
        return anuncios;
    }

    public List<SankhyaAnuncio> buscarAnunciosDeVariacaoPendentes() throws SQLException {
        List<SankhyaAnuncio> anuncios = new ArrayList<>();
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT   "
                + "       ANU.NUMPRO,ANU.NUANU,ANU.CODMPK,ANU.VLRPRECOANTERIOR,ANU.VLRPRECO,ANU.DTINICIAL,ANU.DTFINAL,ANU.PROCESSADO,MKP.CANALDEVENDA, VAR.SKU, ANU.STATUS "
                + "FROM   "
                + "  AD_HMPVANU ANU  "
                + "INNER JOIN AD_HMPVAR VAR ON "
                + "  VAR.NUMVAR = ANU.NUMVAR "
                + "INNER JOIN AD_HMPMKTP MKP ON   "
                + "  ANU.CODMPK = MKP.NUMMKTP  "
                + "WHERE ANU.PROCESSADO = 'N'"
                + " OR STATUS = 2";
        stmt = connection.prepareCall(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaAnuncio sankhyaAnuncio = new SankhyaAnuncio();
            sankhyaAnuncio.setNumPro(rs.getInt("NUMPRO"));
            sankhyaAnuncio.setNumAnu(rs.getInt("NUANU"));
            sankhyaAnuncio.setCodMpk(rs.getInt("CODMPK"));
            sankhyaAnuncio.setVlrPrecoAnterior(rs.getDouble("VLRPRECOANTERIOR"));
            sankhyaAnuncio.setVlrPreco(rs.getDouble("VLRPRECO"));
            sankhyaAnuncio.setDtInicial(Converter.dataSqlParaUtil(rs.getTimestamp("DTINICIAL")));
            sankhyaAnuncio.setDtFinal(Converter.dataSqlParaUtil(rs.getTimestamp("DTFINAL")));
            sankhyaAnuncio.setProcessado(rs.getString("PROCESSADO"));
            sankhyaAnuncio.setCanalDeVenda(rs.getInt("CANALDEVENDA"));
            sankhyaAnuncio.setSku(rs.getString("SKU"));
            sankhyaAnuncio.setStatus(rs.getInt("STATUS"));
            anuncios.add(sankhyaAnuncio);
        }
        return anuncios;
    }

    public Integer marcarAnuncioComoSicronizando(SankhyaAnuncio SankhyaAnuncio) throws SQLException {
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt = connection.prepareCall(""
                + "UPDATE "
                + "    AD_HMPANU "
                + "SET PROCESSADO = 'S', STATUS = 5, CONTROLE = 'INTEGRACAO'"
                + "WHERE NUANU = ?"
        );
        stmt.setInt(1, SankhyaAnuncio.getNumAnu());
        int executeUpdate = stmt.executeUpdate();
        connection.commit();
        return executeUpdate;

    }

    public Integer marcarVariacaoComoProcessada(SankhyaAnuncio SankhyaAnuncio) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "UPDATE "
                + "    AD_HMPVANU "
                + "SET PROCESSADO = 'S', CONTROLE = 'INTEGRACAO'"
                + "WHERE NUANU = ?"
        );
        stmt.setInt(1, SankhyaAnuncio.getNumAnu());
        int executeUpdate = stmt.executeUpdate();
        return executeUpdate;
    }

    public Integer inserir(Produto produto, Integer codProd) {
        try {
            connection = ConexaoOracle.ObterConexao();
            CallableStatement stmt = connection.prepareCall(
                    "INSERT INTO AD_HMPANU(NUMPRO, NUANU, CODMPK,SKULOJISTA, VLRPRECOANTERIOR,VLRPRECO, PROCESSADO,CONTROLE,STATUS) "
                    + "VALUES( "
                    + "?  "
                    + ", (SELECT NVL((MAX(NUANU) + 1),1) FROM AD_HMPANU) "
                    + ",(SELECT NUMMKTP FROM AD_HMPMKTP WHERE CANALDEVENDA = ?) "
                    + ",?,?,?,'S','INTEGRACAO',?) "
            );
            Integer codStatus;
            if (produto.getStatus().equals("Synchronized")) {
                codStatus = (3);
            } else {
                codStatus = (1);
            }
            stmt.setInt(1, codProd);
            stmt.setDouble(2, produto.getSalesChannel());
            stmt.setString(3, produto.getMarketplaceSKU());
            stmt.setDouble(4, produto.getPriceBase());
            stmt.setDouble(5, produto.getPriceSale());
            stmt.setInt(6, codStatus);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, e.getMessage(), Logs.COLOR_ERRO);
            return null;
        }
    }

    public Integer marcarAnuncioComErro(SankhyaAnuncio sankhyaAnuncio) throws SQLException {
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt = connection.prepareCall(""
                + "UPDATE "
                + "    AD_HMPANU "
                + "SET PROCESSADO = 'S', STATUS = 4, CONTROLE = 'INTEGRACAO'"
                + "WHERE NUANU = ?"
        );
        stmt.setInt(1, sankhyaAnuncio.getNumAnu());
        int executeUpdate = stmt.executeUpdate();
        return executeUpdate;

    }

}
