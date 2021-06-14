package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.hub2b.model.pedido.PedidoStatus;
import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaFaturar;
import br.com.ventisol.sankhya.model.vo.SankhyaPedido;
import br.com.ventisol.util.Converter;
import br.com.ventisol.util.Logs;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoDao {

    Connection connection = null;

    public PedidoDao(Connection connection) {
        this.connection = connection;
    }

    public PedidoDao() {
    }

    public SankhyaPedido inserirPedido(SankhyaPedido pedido, Connection connectionRequest) throws SQLException {
        this.connection = connectionRequest;
        if (this.connection == null) {
            this.connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt = connection.prepareCall(
                "BEGIN INSERT INTO AD_HMPPED("
                + "PEDIDOID,DATAVENDA,VALORTOTAL,PAGAMENTOVALOR,PAGAMENTOVALORPARCELA,VALORFRETE,VALORDESCONTO, OBSERVACOES,OBSERVACOESLOJA,STATUSID"
                + ", CODIGOPEDIDOEXTERNO,ORIGEMPEDIDO,NUMCLI, FORMAPAGAMENTOID,PRAZOENVIO"
                + ", ATIVO,PROCESSADO,CARTAOQUANTIDADEPARCELAS,STATUS,CLIENTEID,CARRINHO"
                + ")"
                + " VALUES ("
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,"
                + "?"
                + ")"
                + " RETURNING NUMPED INTO ? ; END;");
        stmt.setBigDecimal(1, Converter.nuloParaZero(pedido.getPedidoId()));
        stmt.setDate(2, Converter.stringParaDataSql(pedido.getDataVenda(), "yyyy-MM-dd'T'HH:mm:ss"));
        stmt.setDouble(3, Converter.nuloParaZero(pedido.getValorTotal()));
        stmt.setDouble(4, Converter.nuloParaZero(pedido.getPagamentoValor()));
        stmt.setDouble(5, Converter.nuloParaZero(pedido.getPagamentoValorParcela()));
        stmt.setDouble(6, Converter.nuloParaZero(pedido.getValorFrete()));
        stmt.setDouble(7, Converter.nuloParaZero(pedido.getValorDesconto()));
        stmt.setString(8, pedido.getObservacoes());
        stmt.setString(9, pedido.getObservacoesLoja());
//            stmt.setString(10, pedido.getStatusId()); // SITUAÇÃO AINDA NÃO CADASTRADA
        stmt.setInt(10, 0); // SITUAÇÃO AINDA NÃO CADASTRADA
        stmt.setString(11, pedido.getCodigoPedidoExterno());
        stmt.setString(12, pedido.getOrigemPedido());
        stmt.setBigDecimal(13, Converter.nuloParaZero(pedido.getNumCli()));
        stmt.setString(14, pedido.getFormaPagamentoId());
        stmt.setDate(15, Converter.stringParaDataSql(pedido.getDtPrevisao(), "yyyy-MM-dd'T'HH:mm:ss"));
        stmt.setString(16, pedido.getAtivo());
        stmt.setString(17, pedido.getProcessado());
        stmt.setInt(18, Converter.nuloParaZero(pedido.getCartaoQuantidadeParcelas()));
        stmt.setString(19, pedido.getStatusId());
        stmt.setBigDecimal(20, pedido.getNumCli());
        stmt.setString(21, pedido.getCarrinho());
        stmt.registerOutParameter(22, java.sql.Types.BIGINT);
        stmt.executeUpdate();
        pedido.setNumPed(stmt.getBigDecimal(22));
        Logs.gerarLog(Logs.TITULO_AVISO, "Pedido Inserido com Sucesso! Número do Pedido no Sankhya-W: " + pedido.getNumPed(), Logs.COLOR_AVISO);
        return pedido;
    }

    public List<SankhyaFaturar> buscarFaturamentosPendentes() throws SQLException {
        List<SankhyaFaturar> faturamentos = new ArrayList<>();
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;

        stmt = connection.prepareCall("SELECT "
                + "    CAB.SERIENOTA, CAB.CHAVENFE,CAB.NUMPROTOC, CAB.VLRNOTA , CAB.DTFATUR, PED.NUMPED, PED.PEDIDOID "
                + " FROM "
                + "    AD_HMPPED PED "
                + " INNER JOIN TGFCAB CAB ON "
                + "    CAB.NUNOTA = (SELECT DISTINCT NUNOTA FROM TGFVAR WHERE NUNOTAORIG IN (SELECT AD_NUREM FROM TGFCAB WHERE NUNOTA = PED.NUNOTA)) "
                + " WHERE "
                + "    (RESULTPROCESSO  NOT IN (7,8,9) OR RESULTPROCESSO IS NULL) "
                + "    AND CAB.STATUSNFE = 'A' "
                + "    AND CODTIPVENDA = 303 ");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaFaturar sankhyaFaturar = new SankhyaFaturar();
            sankhyaFaturar.setSerieNota(rs.getString("SERIENOTA"));
            sankhyaFaturar.setChaveNfe(rs.getString("CHAVENFE"));
            sankhyaFaturar.setNumProtoc("NUMPROTOC");
            sankhyaFaturar.setVlrNota(rs.getDouble("NUMPROTOC"));
            sankhyaFaturar.setDtFatur(Converter.dataSqlParaUtil(rs.getTimestamp("DTFATUR")));
            sankhyaFaturar.setNumPed(Converter.nuloParaZero(rs.getBigDecimal("NUMPED")));
            sankhyaFaturar.setPedidoId(Converter.nuloParaZero(rs.getBigDecimal("PEDIDOID")));
            faturamentos.add(sankhyaFaturar);
        }
        Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Faturamentos pendentes Resgatados com Sucesso! Quantidade: " + faturamentos.size() + "!!", Logs.COLOR_AVISO);
        return faturamentos;
    }

    public List<SankhyaFaturar> buscarFaturamentosManuais() throws SQLException {
        List<SankhyaFaturar> faturamentos = new ArrayList<>();
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;

        stmt = connection.prepareCall("SELECT "
                + "    CAB.SERIENOTA, CAB.CHAVENFE,CAB.NUMPROTOC, CAB.VLRNOTA , CAB.DTFATUR, PED.NUMPED, PED.PEDIDOID "
                + " FROM "
                + "    AD_HMPPED PED "
                + " INNER JOIN TGFCAB CAB ON "
                + "    CAB.NUNOTA = PED.NOTAFISCALMANUAL"
                + " WHERE "
                + "    (RESULTPROCESSO  IN (10)) "
                + "    AND CAB.STATUSNFE = 'A' "
                + "    AND CODTIPVENDA = 303 ");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaFaturar sankhyaFaturar = new SankhyaFaturar();
            sankhyaFaturar.setSerieNota(rs.getString("SERIENOTA"));
            sankhyaFaturar.setChaveNfe(rs.getString("CHAVENFE"));
            sankhyaFaturar.setNumProtoc("NUMPROTOC");
            sankhyaFaturar.setVlrNota(rs.getDouble("NUMPROTOC"));
            sankhyaFaturar.setDtFatur(Converter.dataSqlParaUtil(rs.getTimestamp("DTFATUR")));
            sankhyaFaturar.setNumPed(Converter.nuloParaZero(rs.getBigDecimal("NUMPED")));
            sankhyaFaturar.setPedidoId(Converter.nuloParaZero(rs.getBigDecimal("PEDIDOID")));
            faturamentos.add(sankhyaFaturar);
        }
        Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Faturamentos pendentes Resgatados com Sucesso! Quantidade: " + faturamentos.size() + "!!", Logs.COLOR_AVISO);
        return faturamentos;
    }

    public void marcarComoFaturado(SankhyaFaturar faturar) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall("UPDATE AD_HMPPED SET RESULTPROCESSO = 7 WHERE PEDIDOID = ?");
        stmt.setBigDecimal(1, faturar.getPedidoId());
        stmt.executeUpdate();
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Pedido: " + faturar.getPedidoId()+ ", marcado como faturado!", Logs.COLOR_SUCESSO);
    }

    public void marcarManualComoFaturado(SankhyaFaturar faturar) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall("UPDATE AD_HMPPED SET RESULTPROCESSO = 11 WHERE PEDIDOID = ?");
        stmt.setBigDecimal(1, faturar.getPedidoId());
        stmt.executeUpdate();
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Pedido: " + faturar.getPedidoId() + ", marcado como faturado!", Logs.COLOR_SUCESSO);
    }

    public void marcarComoCancelado(SankhyaPedido pedido) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt;
        stmt = connection.prepareCall("UPDATE AD_HMPPED SET RESULTPROCESSO = 9 WHERE PEDIDOID = ?");
        stmt.setBigDecimal(1, pedido.getPedidoId());
        stmt.executeUpdate();
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Pedido: " + pedido.getPedidoId() + ", marcado como cancelado!", Logs.COLOR_SUCESSO);
    }

    public Boolean buscarExistenciaDoPedido(BigDecimal pedidoId) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT "
                + "     1 "
                + "FROM "
                + "     AD_HMPPED "
                + "WHERE "
                + "     PEDIDOID = ? ";
        stmt = connection.prepareCall(sql);
        stmt.setBigDecimal(1, pedidoId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public Integer buscarQuantidadeDePedidosCancelados() throws SQLException {
        List<SankhyaFaturar> faturamentos = new ArrayList<>();
        Integer quantidade = (99999999);
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        try {
            stmt = connection.prepareCall("SELECT "
                    + "    COUNT(PED.NUMPED) AS QTD "
                    + "FROM "
                    + "    AD_HMPPED PED "
                    + "WHERE "
                    + "    PED.RESULTPROCESSO = 9 ");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                quantidade = rs.getInt("QTD");
            }
            Logs.gerarLog(Logs.TITULO_AVISO, "Quantidade de Pedidos Cancelados resgatados do Sankhya-W!!", Logs.COLOR_AVISO);
        } catch (SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return quantidade;
    }

    public boolean verificarSePedidoEstaCancelado(BigDecimal pedidoId) throws SQLException {
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT "
                + "     1 "
                + "FROM "
                + "     AD_HMPPED "
                + "WHERE "
                + "     PEDIDOID = ? "
                + "     AND RESULTPROCESSO = 9 ";
        stmt = connection.prepareCall(sql);
        stmt.setBigDecimal(1, pedidoId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public List<PedidoStatus> buscarCancelamentosPendentes() throws SQLException {
        List<PedidoStatus> pedidoStatuses = new ArrayList<>();
        if (this.connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        String sql = "SELECT "
                + "'CANCELED' AS STATUS, SYSDATE AS UPDATEDATE, 'TRUE' AS ATIVO, '' AS MENSAGEM, PEDIDOID "
                + "FROM "
                + "     AD_HMPPED "
                + "WHERE "
                + "     RESULTPROCESSO = 8 ";
        stmt = connection.prepareCall(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            PedidoStatus pedidoStatus = new PedidoStatus();
            pedidoStatus.setActive(rs.getString("ATIVO"));
            pedidoStatus.setMessage(rs.getString("MENSAGEM"));
            pedidoStatus.setStatus(rs.getString("STATUS"));
            Date updateDate = Converter.dataSqlParaUtil(rs.getTimestamp("UPDATEDATE"));
            pedidoStatus.setUpdateDate(Converter.converterDataParaInstant(updateDate));
            pedidoStatus.setPedidoId(rs.getString("PEDIDOID"));
            pedidoStatuses.add(pedidoStatus);
        }
        return pedidoStatuses;
    }
}
