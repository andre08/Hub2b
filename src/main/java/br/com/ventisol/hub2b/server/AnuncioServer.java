package br.com.ventisol.hub2b.server;

import br.com.ventisol.hub2b.model.auxiliar.Erro;
import br.com.ventisol.hub2b.model.anuncio.AnuncioPreco;
import br.com.ventisol.hub2b.model.anuncio.AnuncioStatus;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.sankhya.model.dao.ErroDao;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnuncioServer {

    public static boolean criarOuAtualizarPrecoAnuncio(AnuncioPreco anuncio, LoginResponse loginResponse) throws SQLException {
        List<AnuncioPreco> anuncios = new ArrayList<>();
        anuncios.add(anuncio);
        String json = (Requisicao.POST(Rotas.comVariaveis(Rotas.Produto.INSERIROUALTERARPRECO, "idTenant", loginResponse.getIdTenant()), "Auth", loginResponse.getAcessKey(), anuncios));
        Erro erro = (Erro) Util.gsonParaClasse(Erro.class, json);
        if (erro == null) {
            return true;
        } else {
            if (erro.getError() == null || erro.getError().equals("")) {
                return true;
            } else {
                Logs.gerarLog(Logs.TITULO_ERRO, json, Logs.COLOR_ERRO);
                ErroDao.inserirRetorno("AD_HMPPRO", erro.getError(), "SKU = '" + anuncio.getItemId() + "' ");
                ErroDao.inserirRetorno("AD_HMPVAR", erro.getError(), "SKU = '" + anuncio.getItemId() + "' ");
                return false;
            }
        }
    }

    public static boolean atualizarStatus(AnuncioStatus anuncioStatus, LoginResponse login) throws SQLException {
        List<AnuncioStatus> anuncioStatusList = new ArrayList<>();
        anuncioStatusList.add(anuncioStatus);
        String json = (Requisicao.POST(Rotas.comVariaveis(Rotas.Anuncio.ATUALIZARSTATUS, "idTenant", login.getIdTenant()), "Auth", login.getAcessKey(), anuncioStatusList));
        Erro erro = (Erro) (Util.gsonParaClasse(Erro.class, json));
        if (erro != null) {
            if (erro.getError() == null || erro.getError().equals("")) {
                return true;
            } else {
                Logs.gerarLog(Logs.TITULO_ERRO, anuncioStatus.toString() + "-" + json, Logs.COLOR_ERRO);
                ErroDao.inserirRetorno("AD_HMPPRO", erro.getError(), "SKU = '" + anuncioStatus.getItemId() + "' ");
                ErroDao.inserirRetorno("AD_HMPVAR", erro.getError(), "SKU = '" + anuncioStatus.getItemId() + "' ");
                return false;
            }
        }
        return true;
    }
}
