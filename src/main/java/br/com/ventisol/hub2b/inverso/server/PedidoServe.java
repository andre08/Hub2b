package br.com.ventisol.hub2b.inverso.server;

import br.com.ventisol.hub2b.inverso.vo.ProdutoJson;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.hub2b.model.pedido.Pedido;
import br.com.ventisol.hub2b.model.pedido.PedidosResponse;
import br.com.ventisol.hub2b.model.produto.Produto;
import br.com.ventisol.hub2b.server.LoginServer;
import br.com.ventisol.hub2b.server.PedidoServer;
import br.com.ventisol.sankhya.model.dao.AnuncioDao;
import br.com.ventisol.sankhya.model.dao.ClienteDao;
import br.com.ventisol.sankhya.model.dao.ItemDao;
import br.com.ventisol.sankhya.model.dao.PedidoDao;
import br.com.ventisol.sankhya.model.dao.ProdutoDao;
import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaCliente;
import br.com.ventisol.sankhya.model.vo.SankhyaItem;
import br.com.ventisol.sankhya.model.vo.SankhyaPedido;
import br.com.ventisol.util.Dicionario;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PedidoServe {

    static Connection connection;

    public static ProdutoJson listarTodosOsPedidos(LoginResponse loginResponse, Integer pagina) throws SQLException {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.comVariaveis(Rotas.Produto.BUSCARTODOSSPRODUTOS, "idTenant", loginResponse.getIdTenant()), "offSet", String.valueOf(pagina)), "Auth", loginResponse.getAcessKey()));
        System.out.println(json);
        ProdutoJson produtoJson = ((ProdutoJson) Util.gsonParaClasse(ProdutoJson.class, json));
        return produtoJson;
    }

    private static void integrarProdutosPedido(SankhyaPedido sankhyaPedido, Pedido pedido, Connection connection) throws SQLException {
        Logs.gerarLog(Logs.TITULO_AVISO, "Começando Integração do Produtos", Logs.COLOR_AVISO);
        List<SankhyaItem> sankhyaItems = Dicionario.ConverterItem.hubParaSankhya(pedido, sankhyaPedido.getNumPed());
        for (SankhyaItem sankhyaItem : sankhyaItems) {
            ItemDao itemDao = new ItemDao(connection);
            itemDao.inserirItem(sankhyaItem);
        }
        Logs.gerarLog(Logs.TITULO_AVISO, "Finalizando Integração do Produtos", Logs.COLOR_AVISO);
    }

    private static SankhyaCliente integrarClientePedido(Pedido pedido, Connection connection) throws SQLException {
        ClienteDao clienteDao = new ClienteDao(connection);
        Logs.gerarLog(Logs.TITULO_AVISO, "Integrando Cliente: " + pedido.getCustomer().getName(), Logs.COLOR_AVISO);
        SankhyaCliente sankhyaCliente = clienteDao.inserir(Dicionario.ConverterCliente.hubParaSankhya(pedido));
        Logs.gerarLog(Logs.TITULO_AVISO, "Integração do Cliente Finalizada", Logs.COLOR_AVISO);
        return sankhyaCliente;
    }

}
