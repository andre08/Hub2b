package br.com.ventisol.hub2b.server;

import br.com.ventisol.hub2b.model.auxiliar.Erro;
import br.com.ventisol.hub2b.model.estoque.Estoque;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.sankhya.model.dao.ErroDao;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstoqueServer {

    public static boolean atualizarEstoque(Estoque estoque, LoginResponse login) throws SQLException {
        List<Estoque> estoques = new ArrayList<>();
        estoques.add(estoque);
        String json = (Requisicao.POST(Rotas.comVariaveis(Rotas.Estoque.ATUALIZARESTOQUE, "idTenant", login.getIdTenant()
        ), "Auth", login.getAcessKey(), estoques));
        Erro erro = (Erro) (Util.gsonParaClasse(Erro.class, json));
        if (erro == null) {
            return true;
        } else {
            if (erro.getError() == null || erro.getError().equals("")) {
                return true;
            } else {
                Logs.gerarLog(Logs.TITULO_ERRO, json, Logs.COLOR_ERRO);
                ErroDao.inserirRetorno("AD_HMPPRO", erro.getError(), "SKU = '" + estoque.getItemId() + "'");
                ErroDao.inserirRetorno("AD_HMPVAR", erro.getError(), "SKU = '" + estoque.getItemId() + "'");
                return false;
            }
        }
    }

}
