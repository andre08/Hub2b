package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaMarketPlace;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketPlaceDao {
    
Connection connection;
        
    public SankhyaMarketPlace buscarPeloNome(String marketPlace) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(
                "SELECT NUMMKTP,ORIGEMEXTERNO FROM AD_HMPMKTP WHERE UPPER(ORIGEMEXTERNO) = UPPER(?)");
        stmt.setString(1, marketPlace);
        ResultSet executeQuery = stmt.executeQuery();
        SankhyaMarketPlace sankhyaMarketPlace = new SankhyaMarketPlace();
        if (executeQuery.next()) {
            System.out.println("ENTROU");
            sankhyaMarketPlace.setMarketingplace(executeQuery.getString("ORIGEMEXTERNO"));
            sankhyaMarketPlace.setNumMkpt(executeQuery.getBigDecimal("NUMMKTP"));
        }
        return sankhyaMarketPlace;
    }

}
