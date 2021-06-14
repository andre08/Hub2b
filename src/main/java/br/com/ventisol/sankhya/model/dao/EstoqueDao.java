package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.vo.SankhyaEstoque;
import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.util.Logs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDao {

    Connection connection = null;

    public EstoqueDao(Connection connection) {
        this.connection = connection;
    }

    public EstoqueDao() {
    }

    public List<SankhyaEstoque> buscarEstoqueNaoProcessados() throws SQLException {
        List<SankhyaEstoque> estoques = new ArrayList<>();
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT   "
                + "      MPRO.CODPROD,MPRO.SKU, EST.ESTOQUE "
                + "FROM   "
                + "      TGFEST EST  "
                + "RIGHT JOIN AD_HMPPRO MPRO ON  "
                + "     MPRO.CODPROD = EST.CODPROD  "
                + "WHERE   "
                + "     NVL(EST.AD_PROCESSADOHUB,'N') = 'N'  "
                + "     AND (EST.CODLOCAL = (SELECT CODLOCAL FROM AD_HMPPREF WHERE ROWNUM = 1) OR EST.CODLOCAL IS NULL) "
                + "UNION ALL "
                + "SELECT  "
                + "     MPRO.CODPROD,MPRO.SKU, 0 AS ESTOQUE "
                + "FROM  "
                + "    AD_HMPPRO MPRO "
                + "WHERE  "
                + "    EXISTS (SELECT 1 FROM AD_HMPEST WHERE CODPROD = MPRO.CODPROD AND PROCESSADO = 'N') "
                + "UNION ALL  "
                + "SELECT   "
                + "      VAR.CODPROD,VAR.SKU, EST.ESTOQUE  "
                + "FROM   "
                + "      TGFEST EST  "
                + "RIGHT JOIN AD_HMPVAR VAR ON  "
                + "     VAR.CODPROD = EST.CODPROD  "
                + "WHERE   "
                + "     NVL(EST.AD_PROCESSADOHUB,'N') = 'N'  "
                + "     AND (EST.CODLOCAL = (SELECT CODLOCAL FROM AD_HMPPREF WHERE ROWNUM = 1) OR EST.CODLOCAL IS NULL) "
                + "UNION ALL "
                + "SELECT  "
                + "     VPRO.CODPROD,VPRO.SKU, 0 AS ESTOQUE "
                + "FROM  "
                + "    AD_HMPVAR VPRO "
                + "WHERE  "
                + "    EXISTS (SELECT 1 FROM AD_HMPEST WHERE CODPROD = VPRO.CODPROD AND PROCESSADO = 'N') ";
        stmt = connection.prepareCall(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaEstoque sankhyaEstoque = new SankhyaEstoque();
            sankhyaEstoque.setCodProd(rs.getInt("CODPROD"));
            sankhyaEstoque.setEstoque(rs.getInt("ESTOQUE"));
            sankhyaEstoque.setSku(rs.getString("SKU"));
            estoques.add(sankhyaEstoque);
        }
        return estoques;
    }

    public int marcarComoProcessado(SankhyaEstoque sankhyaEstoque) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "UPDATE "
                + "    TGFEST "
                + "SET AD_PROCESSADOHUB = 'S', AD_CONTROLEHUB = 'INTEGRACAO' "
                + "WHERE CODLOCAL = (SELECT CODLOCAL FROM AD_HMPPREF WHERE ROWNUM = 1) AND CODPROD = ? "
        );
        stmt.setInt(1, sankhyaEstoque.getCodProd());
        int executeUpdate = stmt.executeUpdate();
        CallableStatement stmt2 = connection.prepareCall(""
                + "UPDATE "
                + "    AD_HMPEST "
                + "SET PROCESSADO = 'S' "
                + "WHERE CODPROD = ? "
        );
        stmt2.setInt(1, sankhyaEstoque.getCodProd());
        int executeUpdate2= stmt2.executeUpdate();
        return executeUpdate + executeUpdate2;
    }

    public Integer buscarEstoqueDoProduto(Integer numPro) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT  "
                + "        EST.ESTOQUE "
                + " FROM  "
                + "        TGFEST EST "
                + " INNER JOIN AD_HMPPRO MPRO ON "
                + "       MPRO.CODPROD = EST.CODPROD "
                + " WHERE  "
                + "       EST.CODLOCAL = (SELECT CODLOCAL FROM AD_HMPPREF WHERE ROWNUM = 1) "
                + "       AND NUMPRO = ?"
                + " UNION ALL "
                + " SELECT  "
                + "       EST.ESTOQUE "
                + " FROM  "
                + "        TGFEST EST "
                + " INNER JOIN AD_HMPVAR VAR ON "
                + "       VAR.CODPROD = EST.CODPROD "
                + " WHERE  "
                + "       EST.CODLOCAL = (SELECT CODLOCAL FROM AD_HMPPREF WHERE ROWNUM = 1) "
                + "       AND NUMPRO = ?";
        stmt = connection.prepareCall(sql);
        stmt.setInt(1, numPro);
        stmt.setInt(2, numPro);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Integer retorno = rs.getInt("ESTOQUE");
            return retorno;
        }
        return 0;
    }

}
