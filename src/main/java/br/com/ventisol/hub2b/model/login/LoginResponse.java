package br.com.ventisol.hub2b.model.login;

import br.com.ventisol.hub2b.model.auxiliar.Erro;

public class LoginResponse extends Erro{

    private String refresh_token;
    private String token_type;
    private String access_token;
    private Integer expires_in;
        private String idTenant;
    private String acessKey;

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public String getIdTenant() {
        return idTenant;
    }

    public void setIdTenant(String idTenant) {
        this.idTenant = idTenant;
    }

    public String getAcessKey() {
        return acessKey;
    }

    public void setAcessKey(String acessKey) {
        this.acessKey = acessKey;
    }

    
    
}
