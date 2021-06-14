package br.com.ventisol.hub2b.model.produto;


public class ProdutoData {
    ProdutoList data;
    String erro;

    public ProdutoData() {
    }
    
    
    public ProdutoList getData() {
        return data;
    }

    public void setData(ProdutoList data) {
        this.data = data;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    @Override
    public String toString() {
        return "ProdutoData{" + "data=" + data + ", erro=" + erro + '}';
    }

    
    
    
    
}
