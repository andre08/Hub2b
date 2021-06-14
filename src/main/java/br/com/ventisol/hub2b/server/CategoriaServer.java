package br.com.ventisol.hub2b.server;

import br.com.ventisol.hub2b.model.auxiliar.Erro;
import br.com.ventisol.hub2b.model.categoria.Categoria;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.sankhya.model.dao.ErroDao;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaServer {

    
    public static Boolean inserirOuAlterarCategoria(Categoria categoria, LoginResponse loginResponse) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(categoria);
        String json = (Requisicao.POST(Rotas.comVariaveis(Rotas.Categoria.CADASTRAROUALTERARCATEGORIA, "idTenant", loginResponse.getIdTenant()), "Auth", loginResponse.getAcessKey(), categorias));
        Erro erro = ((Erro) Util.gsonParaClasse(Erro.class, json));
        if (erro == null) {
            return true;
        } else {
            if (erro.getError() == null || erro.getError().equals("")) {
                return true;
            } else {
                Logs.gerarLog(Logs.TITULO_ERRO, json, Logs.COLOR_ERRO);
                ErroDao.inserirRetorno("AD_HMPCAT", erro.getError(), "CODHUB = " + categoria.getCode());
                return false;
            }
        }
    }

}
