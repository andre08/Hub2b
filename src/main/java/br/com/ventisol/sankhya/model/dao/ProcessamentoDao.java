package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaProcessamento;
import br.com.ventisol.util.Logs;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcessamentoDao {

    Connection connection = null;

    public ProcessamentoDao(Connection connection) {
        this.connection = connection;
    }

    public ProcessamentoDao() {
    }

    public List<SankhyaProcessamento> listaProcessamentos() throws SQLException {
        List<SankhyaProcessamento> sankhyaProcessamentos = new ArrayList<>();
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall("SELECT NUPROCESSO,MODULO,PARAMETRO,REFERENCIA FROM AD_HMPPCS WHERE PROCESSADO = 'N'");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaProcessamento processamento = new SankhyaProcessamento();
            processamento.setNuProcesso(rs.getBigDecimal("NUPROCESSO"));
            processamento.setModulo(rs.getBigDecimal("MODULO"));
            processamento.setParametro(rs.getString("PARAMETRO"));
            processamento.setReferencia(rs.getString("REFERENCIA"));
            sankhyaProcessamentos.add(processamento);
        }
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Sucesso ao Carregar Todos os Processamentos!", Logs.COLOR_SUCESSO);
        return sankhyaProcessamentos;
    }

    public void concluirProcessamento(BigDecimal nuProcesso) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall("UPDATE AD_HMPPED SET RESULTPROCESSO = 7 WHERE NUMPED = ?");
        stmt.setBigDecimal(1, nuProcesso);
        stmt.executeUpdate();
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Processo: " + nuProcesso + ", marcado como processado!", Logs.COLOR_SUCESSO);
    }

}
