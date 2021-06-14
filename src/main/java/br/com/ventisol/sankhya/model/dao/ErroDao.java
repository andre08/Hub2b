package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ErroDao {

    private static Connection connection = null;

    public static void inserirRetorno(String tabela, String retorno, String condicao) throws SQLException {
        if(connection == null){
         connection = ConexaoOracle.ObterConexao();   
        }
        retorno = retorno.replaceAll("'", "\"");
        CallableStatement stmt;
        stmt = connection.prepareCall(
                "UPDATE " + tabela + " SET RETORNO = ?, CONTROLE = 'INTEGRACAO' WHERE " + condicao);
        stmt.setString(1, retorno);
        stmt.executeUpdate();
    }
}
