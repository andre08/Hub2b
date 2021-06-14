package br.com.ventisol.hub2b.model.produto;

public class Produtos {

    private String error;
    private ProdutosResponse data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ProdutosResponse getData() {
        if(data == null){
            data = new ProdutosResponse();
        }
        return data;
    }

    public void setData(ProdutosResponse data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Produtos{" + "error=" + error + ", data=" + data + '}';
    }

}
