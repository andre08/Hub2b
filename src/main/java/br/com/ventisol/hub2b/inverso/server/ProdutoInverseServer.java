package br.com.ventisol.hub2b.inverso.server;

import br.com.ventisol.hub2b.inverso.vo.ProdutoJson;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.hub2b.model.produto.Produto;
import br.com.ventisol.hub2b.server.LoginServer;
import br.com.ventisol.sankhya.model.dao.AnuncioDao;
import br.com.ventisol.sankhya.model.dao.ProdutoDao;
import br.com.ventisol.util.Dicionario;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdutoInverseServer {

    private static Connection connection;

   public static ProdutoJson listarTodosOsProdutos(LoginResponse loginResponse, Integer pagina) throws SQLException {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.comVariaveis(Rotas.Produto.BUSCARTODOSSPRODUTOS, "idTenant", loginResponse.getIdTenant()),"offSet",String.valueOf(pagina)), "Auth", loginResponse.getAcessKey()));
        System.out.println(json);
        ProdutoJson produtoJson = ((ProdutoJson) Util.gsonParaClasse(ProdutoJson.class, json));
        return produtoJson;
    }
}
