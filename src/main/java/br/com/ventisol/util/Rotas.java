package br.com.ventisol.util;

public class Rotas {

    public static String comVariaveis(String rota, String variavel, String valor) {
        return rota.replaceAll("\\{" + variavel + "\\}", valor);
    }

    public static class Produto {

        public static final String INSERIROUALTERAR = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/setsku/{idTenant}";
        public static final String INSERIROUALTERARPRECO = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/setprice/{idTenant}";
        public static final String BUSCARPRODUTOSCOMERRO = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/listskus/{idTenant}?FILTER=STATUS%3A4&limit=100&OFFSET={offset}";
        public static final String BUSCARPRODUTOSPENDENTE = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/listskus/{idTenant}?FILTER=STATUS%3A2&limit=100&OFFSET={offset}";
        public static final String BUSCARPRODUTO = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/listskus/{idTenant}?FILTER=SalesChannel:{saleschannel}%7CSKU:{sku}";
        public static final String BUSCARTODOSSPRODUTOS = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/listskus/{idTenant}?limit=100&OFFSET={offset}";

    }

    public class Pedido {

        /**
         * Necessita Utilizar Variaveis = [offset]
         */
        public static final String BUSCARTODOSOSPEDIDOS = "https://rest.hub2b.com.br/Orders?limit=100&offset={offset}";
        
        /**
         * Necessita Utilizar Variaveis = [offset]
         */
        public static final String RESGATARPEDIDOSAPROVADOS = "https://rest.hub2b.com.br/Orders?statuses=approved&limit=100&offset={offset}";

        /**
         * Necessita Utilizar Variaveis = [offset]
         */
        public static final String RESGATARPEDIDOSCANCELADOS = "https://rest.hub2b.com.br/Orders?statuses=canceled&limit=100&offset={offset}";

        public static final String BUSCARVISAOGERAL = "https://rest.hub2b.com.br/Orders/Overview";

        /**
         * Necessita Utilizar Variaveis = [idOrder]
         */
        public static final String FATURARPEDIDO = "https://rest.hub2b.com.br/Orders/{idOrder}/Invoice";

        /**
         * Necessita Utilizar Variaveis = [idOrder]
         */
        public static final String ALTERARSTATUS = "https://rest.hub2b.com.br/Orders/{idOrder}/Status";
    }

    public class Anuncio {

        public static final String ATUALIZARSTATUS = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/setproductstatus/{idTenant}";

    }

    public class Categoria {

        public static final String CADASTRAROUALTERARCATEGORIA = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/setcategory/{idTenant}";
        public static final String LISTARTODAS = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/listcategories/{idTenant}";
    }

    public class Login {

        public static final String REALIZARLOGIN = "https://rest.hub2b.com.br/oauth2/login";
    }

    public class Estoque {

        public static final String ATUALIZARESTOQUE = "https://eb-api.plataformahub.com.br/RestServiceImpl.svc/setstock/{idTenant}";
    }
}
