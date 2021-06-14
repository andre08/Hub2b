package br.com.ventisol.hub2b.inverso.server;

import br.com.ventisol.hub2b.inverso.vo.CategoriaJson;
import br.com.ventisol.hub2b.model.categoria.Categoria;
import br.com.ventisol.hub2b.server.*;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.sankhya.model.dao.CategoriaDao;
import br.com.ventisol.util.Dicionario;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.sql.SQLException;
import java.util.List;

public class CategoriaInverseServer {



    public static List<Categoria> listarTodasAsCategorias(LoginResponse loginResponse) throws SQLException {
        String json = (Requisicao.GET(Rotas.comVariaveis(Rotas.Categoria.LISTARTODAS, "idTenant", loginResponse.getIdTenant()), "Auth", loginResponse.getAcessKey()));
        CategoriaJson categoriaJson = ((CategoriaJson) Util.gsonParaClasse(CategoriaJson.class, json));
        return categoriaJson.getData().getCategoriaInverse();
    }

}
