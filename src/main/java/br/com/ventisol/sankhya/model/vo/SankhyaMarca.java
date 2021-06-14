package br.com.ventisol.sankhya.model.vo;

    public class SankhyaMarca {

    private Integer numMarca;
    private String nome;
    private String logotipo;
    private String controle;
    private char ativo;
    private String url;
    private String retorno;
    private String processado;

    public Integer getNumMarca() {
        return numMarca;
    }

    public void setNumMarca(Integer numMarca) {
        this.numMarca = numMarca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public char getAtivo() {
        return ativo;
    }

    public void setAtivo(char ativo) {
        this.ativo = ativo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public String getProcessado() {
        return processado;
    }

    public void setProcessado(String processado) {
        this.processado = processado;
    }

}
