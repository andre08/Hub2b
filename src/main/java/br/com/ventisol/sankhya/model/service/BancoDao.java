package br.com.ventisol.sankhya.model.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BancoDao extends DaoOracle {

    public static void inserir(String query) {
        PreparedStatement stmt;
        try {
            stmt = ConexaoOracle.ObterConexao().prepareStatement(query);
            int teste = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERRO" + ex.getMessage());
        }
    }

    public static Object inserir(PreparedStatement stmt) {
        try {
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserior (DETALHES):" + ex.getMessage());
        }
        return null;
    }
}
