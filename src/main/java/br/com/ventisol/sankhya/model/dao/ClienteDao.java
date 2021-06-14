package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaCliente;
import br.com.ventisol.util.Converter;
import br.com.ventisol.util.Logs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ClienteDao {

    Connection connection = null;

    public ClienteDao(Connection connection) {
        this.connection = connection;
    }

    public ClienteDao() {
    }

    public SankhyaCliente inserir(SankhyaCliente cliente) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall(
                "BEGIN INSERT INTO AD_HMPCLI(CLIENTEID,NOME,EMAIL,CPF,RG,LOGRADOURO,NUMERO,COMPLEMENTO,CEP,BAIRRO,CIDADE,ESTADO,PAIS,TELEFONE,ATIVO,PROCESSADO,RAZAOSOCIAL,TIPOCLIENTE) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING NUMCLI INTO ?; end;");
        stmt.setInt(1, Converter.nuloParaZero(cliente.getClienteId()));
        stmt.setString(2, cliente.getNome());
        stmt.setString(3, cliente.getEmail());
        stmt.setString(4, cliente.getCpf());
        stmt.setString(5, cliente.getRg());
        stmt.setString(6, cliente.getLogradouro());
        stmt.setString(7, cliente.getNumero());
        stmt.setString(8, cliente.getComplemento());
        stmt.setString(9, cliente.getCep());
        stmt.setString(10, cliente.getBairro());
        stmt.setString(11, cliente.getCidade());
        stmt.setString(12, cliente.getEstado());
        stmt.setString(13, cliente.getPais());
        stmt.setString(14, cliente.getTelefone());
        stmt.setString(15, cliente.getAtivo());
        stmt.setString(16, cliente.getProcessado());
        stmt.setString(17, cliente.getNome());
        stmt.setString(18, cliente.getTipoCliente());
        stmt.registerOutParameter(19, java.sql.Types.VARCHAR);
        stmt.executeUpdate();
        CallableStatement stmt2;
        stmt2 = connection.prepareCall("UPDATE AD_HMPCLI SET CLIENTEID = ? WHERE NUMCLI = ?");
        stmt2.setBigDecimal(1, stmt.getBigDecimal(19));
        stmt2.setBigDecimal(2, stmt.getBigDecimal(19));
        stmt2.executeUpdate();
        cliente.setNumcli(stmt.getBigDecimal(19));
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Cliente Inserido com Sucesso! Número do Cliente no Sankhya-W: " + cliente.getNumcli(), Logs.COLOR_SUCESSO);
        return cliente;

    }
}
