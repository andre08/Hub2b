package br.com.ventisol.hub2b.model.login;

public class LoginCreate {

    private String client_id;
    private String client_secret;
    private String grant_type;
    private String scope;
    private String username;
    private String password;
    private String idTenant;
    private String acessKey;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
