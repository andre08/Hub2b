package br.com.ventisol.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Requisicao {

    public static String GET(String rota, String type, String authorization) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(rota)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header(type, authorization)
                    .header("Content-Type", "application/json")
                    .get();
            return response.readEntity(String.class);
        } catch (Exception e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao fatrurar: " + e.getMessage(), Logs.COLOR_ERRO);
            return null;
        }
    }

    public static String POST(String rota, String type, String authorization, Object dados) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(rota)
                    .request(MediaType.APPLICATION_JSON)
                    .header(type, authorization)
                    //                    .post(Entity.entity(dados, MediaType.APPLICATION_JSON));
                    .post(Entity.entity(Util.classeParaGson(dados), MediaType.APPLICATION_JSON));
            String retorno = response.readEntity(String.class).trim();
            Integer statuscode = response.getStatus();
            String stringStatusCode = ", \"statusCode\" : " + statuscode + "}";
            String tempRetorno = retorno.substring(0, retorno.length() - 1);
            tempRetorno += stringStatusCode;
            return tempRetorno;
        } catch (Exception e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao realizar Requisição: " + e.getMessage(), Logs.COLOR_ERRO);
            return null;
        }
    }

    public static String PUT(String rota, String type, String authorization, Object dados) {
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(rota)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Content-Type", "application/json")
                    .header(type, authorization)
                    .put(Entity.entity(Util.classeParaGson(dados), MediaType.APPLICATION_JSON));
            return response.readEntity(String.class);
        } catch (Exception e) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao realizar Requisição: " + e.getMessage(), Logs.COLOR_ERRO);
            return null;
        }
    }
}
