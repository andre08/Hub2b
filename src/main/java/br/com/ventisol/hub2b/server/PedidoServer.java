package br.com.ventisol.hub2b.server;

import br.com.ventisol.hub2b.model.auxiliar.Erro;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.hub2b.model.pedido.PedidoFaturar;
import br.com.ventisol.hub2b.model.pedido.PedidoStatus;
import br.com.ventisol.hub2b.model.pedido.PedidosResponse;
import br.com.ventisol.hub2b.model.pedido.PedidosVisaoGeral;
import br.com.ventisol.sankhya.model.dao.ErroDao;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;

public class PedidoServer {

    public static PedidosResponse buscarPedidosAprovados(LoginResponse loginResponse, Integer offSet) {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.Pedido.RESGATARPEDIDOSAPROVADOS, "offset", String.valueOf(offSet)), "Authorization", "Bearer " + loginResponse.getAccess_token()));
        PedidosResponse pedidos = (PedidosResponse) Util.gsonParaClasse(PedidosResponse.class, json);
        Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Pedidos foram Resgatados!! Página: " + offSet, Logs.COLOR_AVISO);
        return pedidos;
    }

    public static PedidosResponse buscarTodosOsPedidos(LoginResponse loginResponse, Integer offSet) {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.Pedido.BUSCARTODOSOSPEDIDOS, "offset", String.valueOf(offSet)), "Authorization", "Bearer " + loginResponse.getAccess_token()));
        PedidosResponse pedidos = (PedidosResponse) Util.gsonParaClasse(PedidosResponse.class, json);
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Todos os Pedidos foram Resgatados!!", Logs.COLOR_SUCESSO);
        return pedidos;
    }

    public static PedidosVisaoGeral buscarVisaoGeral(LoginResponse loginResponse) {
        String json = (Requisicao.GET(Rotas.Pedido.BUSCARVISAOGERAL, "Authorization", "Bearer " + loginResponse.getAccess_token()));
        PedidosVisaoGeral pedidos = (PedidosVisaoGeral) Util.gsonParaClasse(PedidosVisaoGeral.class, json);
        Logs.gerarLog(Logs.TITULO_SUCESSO, "Visão Geral foi Resgatado com Sucesso!", Logs.COLOR_SUCESSO);
        return pedidos;
    }

    public static Boolean faturarNota(PedidoFaturar pedido, BigDecimal idPedido, LoginResponse loginResponse) throws SQLException {
        String json = (Requisicao.POST(Rotas.comVariaveis(Rotas.Pedido.FATURARPEDIDO, "idOrder", String.valueOf(idPedido)), "Authorization", "Bearer " + loginResponse.getAccess_token(), pedido));
        PedidoFaturar pedidoFaturar = (PedidoFaturar) Util.gsonParaClasse(PedidoFaturar.class, json);
        if (pedidoFaturar != null) {
            if (pedidoFaturar.getStatusCode() == 201) {
                return true;
            } else {
                if (pedidoFaturar.getErrors() != null) {
                    Logs.gerarLog(Logs.TITULO_ERRO,Arrays.toString(pedidoFaturar.getErrors().toArray()), Logs.COLOR_ERRO);
                    ErroDao.inserirRetorno("AD_HMPPED", Arrays.toString(pedidoFaturar.getErrors().toArray()), "PEDIDOID = " + idPedido);
                    String erro = Arrays.toString(pedidoFaturar.getErrors().toArray());
                    return erro.contains("invoiced");
                }
                Logs.gerarLog(Logs.TITULO_ERRO, pedido.toString() + " / idPedido: " + idPedido, Logs.COLOR_ERRO);
                try {
                    ErroDao.inserirRetorno("AD_HMPPED", pedidoFaturar.toString(), "PEDIDOID = " + idPedido);
                } catch (SQLException error) {
                }
                return false;
            }
        } else {
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Tentar Faturar!", Logs.COLOR_ERRO);
            Logs.gerarLog(Logs.TITULO_ERRO, pedido.toString() + " / idPedido: " + idPedido, Logs.COLOR_ERRO);
            return false;
        }
    }

    public static PedidosResponse buscarPedidosCancelados(LoginResponse loginResponse, Integer offSet) {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.Pedido.RESGATARPEDIDOSCANCELADOS, "offset", String.valueOf(offSet)), "Authorization", "Bearer " + loginResponse.getAccess_token()));
        PedidosResponse pedidos = (PedidosResponse) Util.gsonParaClasse(PedidosResponse.class, json);
        Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Pedidos foram Resgatados com Sucesso! Página: " + offSet + "!!", Logs.COLOR_AVISO);
        return pedidos;
    }

    public static Boolean alterarStatus(LoginResponse loginResponse, PedidoStatus pedidoStatus) {
        String json = (Requisicao.PUT(Rotas.comVariaveis(Rotas.Pedido.ALTERARSTATUS, "idOrder", pedidoStatus.getPedidoId()), "Authorization", "Bearer " + loginResponse.getAccess_token(), pedidoStatus));
        Erro erro = (Erro) Util.gsonParaClasse(Erro.class, json);
        if (erro != null) {
            if (erro.getErrors() != null) {
                if (erro.getErrors().isEmpty()) {
                    Logs.gerarLog(Logs.TITULO_SUCESSO, "Status do pedido: " + pedidoStatus.getPedidoId() + " alterado para " + pedidoStatus.getStatus() + "!", Logs.COLOR_SUCESSO);
                    return true;
                } else {
                    Logs.gerarLog(Logs.TITULO_ERRO, erro.toString(), Logs.COLOR_ERRO);
                    return false;
                }
            } else {
                Logs.gerarLog(Logs.TITULO_SUCESSO, "Status do pedido: " + pedidoStatus.getPedidoId() + " alterado para " + pedidoStatus.getStatus() + "!", Logs.COLOR_SUCESSO);
                return true;
            }
        } else {
            if (erro.getStatus() != null) {
                Logs.gerarLog(Logs.TITULO_SUCESSO, "Status do pedido: " + pedidoStatus.getPedidoId() + " alterado para " + pedidoStatus.getStatus() + "!", Logs.COLOR_SUCESSO);
                return true;
            } else {
                Logs.gerarLog(Logs.TITULO_ERRO, erro.toString(), Logs.COLOR_ERRO);
                return false;
            }
        }

    }
}
