package br.com.ventisol.hub2b.server;

import br.com.ventisol.hub2b.model.auxiliar.Erro;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.hub2b.model.produto.Produto;
import br.com.ventisol.hub2b.model.produto.ProdutoData;
import br.com.ventisol.sankhya.model.dao.ErroDao;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosServer {

    public static ProdutoData buscarProdutosComErros(LoginResponse loginResponse, Integer offSet) {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.comVariaveis(Rotas.Produto.BUSCARPRODUTOSCOMERRO, "offset", String.valueOf(offSet)), "idTenant", loginResponse.getIdTenant()), "Auth", loginResponse.getAcessKey()));
        ProdutoData produtos = ((ProdutoData) Util.gsonParaClasse(ProdutoData.class, json));
        return produtos;
    }

    public static ProdutoData buscarProdutosPendentes(LoginResponse loginResponse, Integer offSet) {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.comVariaveis(Rotas.Produto.BUSCARPRODUTOSPENDENTE, "offset", String.valueOf(offSet)), "idTenant", loginResponse.getIdTenant()), "Auth", loginResponse.getAcessKey()));
        ProdutoData produtos = ((ProdutoData) Util.gsonParaClasse(ProdutoData.class, json));
        Logs.gerarLog(Logs.TITULO_AVISO, "Todos os Produtos foram Resgatados!! Página: " + offSet, Logs.COLOR_AVISO);
        return produtos;
    }

    public static boolean criarOuAtualizarProduto(Produto produto, LoginResponse login) throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        String json = (Requisicao.POST(Rotas.comVariaveis(Rotas.Produto.INSERIROUALTERAR, "idTenant", login.getIdTenant()), "Auth", login.getAcessKey(), produtos));
        Erro erro = (Erro) (Util.gsonParaClasse(Erro.class, json));
        if (erro.getError() == null || erro.getError().equals("")) {
            return true;
        } else {
            Logs.gerarLog(Logs.TITULO_ERRO, json, Logs.COLOR_ERRO);
            if(produto != null){
                ErroDao.inserirRetorno("AD_HMPPRO", erro.getError(), "SKU = '" + produto.getSku() + "'");
            }else{
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro null produto", Logs.COLOR_ERRO);
            }
            return false;
        }
    }

    public static ProdutoData buscarProduto(LoginResponse login, String sku, Integer salesChannel) {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.comVariaveis(Rotas.comVariaveis(Rotas.Produto.BUSCARPRODUTO, "saleschannel", String.valueOf(salesChannel)), "idTenant", login.getIdTenant()), "sku", sku), "Auth", login.getAcessKey()));
        ProdutoData produtos = ((ProdutoData) Util.gsonParaClasse(ProdutoData.class, json));
        return produtos;
    }

}
