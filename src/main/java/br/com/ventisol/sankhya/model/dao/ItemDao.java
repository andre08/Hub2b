package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaItem;
import br.com.ventisol.util.Converter;
import br.com.ventisol.util.Logs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ItemDao {

    Connection connection = null;

    public ItemDao(Connection connection) {
        this.connection = connection;
    }

    public ItemDao() {
    }

    public SankhyaItem inserirItem(SankhyaItem item) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;

        stmt = connection.prepareCall(
                "BEGIN INSERT INTO AD_HMPITE(NUMPED, CODIGOINTEGRACAO, PRECOUNITARIO, PRECOBASE, QUANTIDADE, NOMEDOPRODUTO, DESCONTOITEM,REFERENCIA) "
                + " VALUES (?,?,?,?,?,?,?,?) RETURNING NUMITE INTO ?; end;");
        stmt.setBigDecimal(1, Converter.nuloParaZero(item.getNumPed()));
        stmt.setBigDecimal(2, Converter.nuloParaZero(item.getCodigoIntegracao()));
        stmt.setDouble(3, Converter.nuloParaZero(item.getPrecoUnitario()));
        stmt.setDouble(4, Converter.nuloParaZero(item.getPrecoBase()));
        stmt.setDouble(5, Converter.nuloParaZero(item.getQuantidade()));
        stmt.setString(6, item.getNomeDoProduto());
        stmt.setDouble(7, Converter.nuloParaZero(item.getDescontoItem()));
        stmt.setString(8, item.getReferencia());
        stmt.registerOutParameter(9, java.sql.Types.NUMERIC);
        stmt.executeUpdate();
        item.setNumIte(stmt.getBigDecimal(9));
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Item Inserido com Sucesso! Número do Item no Sankhya-W: " + item.getNumIte(), Logs.COLOR_SUCESSO);        
        return item;
    }
}
