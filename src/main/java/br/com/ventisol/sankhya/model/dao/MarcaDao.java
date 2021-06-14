package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MarcaDao {

    Connection connection = null;

    public MarcaDao(Connection connection) {
        this.connection = connection;
    }

    public MarcaDao() {
    }

    public Integer buscarIdMarcaPeloNome(String nome) {
        try {
            String sql = "SELECT NUMMAR FROM AD_HMPMAR WHERE NOME = UPPER(? ";
            connection = ConexaoOracle.ObterConexao();
            CallableStatement stmt = connection.prepareCall(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("NUMMAR");
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }
}
