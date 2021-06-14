package br.com.ventisol.sankhya.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoOracle {

    public static Connection ObterConexao() {
        Connection conexao = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conexao = DriverManager.getConnection(
                    "jdbc:oracle:thin:@10.0.30.145:1521:prod", "SANKHYA", "tecsis");
//                    "jdbc:oracle:thin:@10.0.30.154:1521:xe", "TESTE1", "TECSIS");
        } catch (SQLException e) {
            Logger.getLogger(ConexaoOracle.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexaoOracle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexao;
    }
}
