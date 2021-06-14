package br.com.ventisol.hub2b.server;

import br.com.ventisol.hub2b.model.login.LoginCreate;
import br.com.ventisol.hub2b.model.login.LoginResponse;
import br.com.ventisol.util.Logs;
import br.com.ventisol.util.Requisicao;
import br.com.ventisol.util.Rotas;
import br.com.ventisol.util.Util;

public class LoginServer {

    public static LoginResponse realizarLoginConta1() {

        LoginResponse loginResponse = null;
        try {
            LoginCreate loginCreate = new LoginCreate();
            loginCreate.setClient_id("PNYMhwXmKi4SayDEukBaXwa60bxe44");
            loginCreate.setClient_secret("TOcUUTuk4NhiOwsbzSJ5aJoel9wEjy");
            loginCreate.setGrant_type("password");
            loginCreate.setScope("inventory orders catalog");
            loginCreate.setUsername("ventisol@hub2b");
            loginCreate.setPassword("2jLCaJ");
            loginCreate.setIdTenant("2542");
            loginCreate.setAcessKey("dmVudGlzb2w6cHdk");
            String json = Requisicao.POST(Rotas.Login.REALIZARLOGIN, "", "", loginCreate);
            loginResponse = (LoginResponse) Util.gsonParaClasse(LoginResponse.class, json);
            loginResponse.setIdTenant(loginCreate.getIdTenant());
            loginResponse.setAcessKey(loginCreate.getAcessKey());
            if (loginResponse.getError() != null) {
                Logs.gerarLog(Logs.TITULO_AVISO, json, Logs.COLOR_AVISO);
            }
//            Logs.gerarLog(Logs.TITULO_SUCESSO, "Login Realizado", Logs.COLOR_SUCESSO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logs.gerarLog(Logs.TITULO_ERRO, e.getMessage(), Logs.COLOR_ERRO);
        }
        return loginResponse;
    }
    public static LoginResponse realizarLoginConta2() {

        LoginResponse loginResponse = null;
        try {
            LoginCreate loginCreate = new LoginCreate();
            loginCreate.setClient_id("PNYMhwXmKi4SayDEukBaXwa60bxe44");
            loginCreate.setClient_secret("TOcUUTuk4NhiOwsbzSJ5aJoel9wEjy");
            loginCreate.setGrant_type("password");
            loginCreate.setScope("inventory orders catalog");
            loginCreate.setUsername("ventisol@hub2b");
            loginCreate.setPassword("2jLCaJ");
            loginCreate.setIdTenant("2542");
            loginCreate.setAcessKey("dmVudGlzb2w6cHdk");
            String json = Requisicao.POST(Rotas.Login.REALIZARLOGIN, "", "", loginCreate);
            loginResponse = (LoginResponse) Util.gsonParaClasse(LoginResponse.class, json);
            loginResponse.setIdTenant(loginCreate.getIdTenant());
            loginResponse.setAcessKey(loginCreate.getAcessKey());
            if (loginResponse.getError() != null) {
                Logs.gerarLog(Logs.TITULO_AVISO, json, Logs.COLOR_AVISO);
            }
            Logs.gerarLog(Logs.TITULO_SUCESSO, "Login Realizado", Logs.COLOR_SUCESSO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logs.gerarLog(Logs.TITULO_ERRO, e.getMessage(), Logs.COLOR_ERRO);
        }
        return loginResponse;
    }
}
