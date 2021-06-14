package br.com.ventisol.hub2b.inverso.vo;

import br.com.ventisol.hub2b.model.pagina.Pagina;

public class ProdutoJson {
    String error;
    ProdutoList data;
    
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ProdutoList getData() {
        return data;
    }

    public void setData(ProdutoList data) {
        this.data = data;
    }

    
    
}
