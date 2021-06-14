package br.com.ventisol.sankhya.model.vo;

public class SankhyaCategoria {

    private Integer numCat;
    private String codHub;
    private String codHubPai;
    private String controle;
    private String ativo;
    private String processado;
    private String nome;
    private String retorno;
    private Integer categoriaPai;
    private Integer categoriaPaiHub;
    

    public Integer getNumCat() {
        return numCat;
    }

    public void setNumCat(Integer numCat) {
        this.numCat = numCat;
    }

    public String getCodHub() {
        return codHub;
    }

    public void setCodHub(String codHub) {
        this.codHub = codHub;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getProcessado() {
        return processado;
    }

    public void setProcessado(String processado) {
        this.processado = processado;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public Integer getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(Integer categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    public Integer getCategoriaPaiHub() {
        return categoriaPaiHub;
    }

    public void setCategoriaPaiHub(Integer categoriaPaiHub) {
        this.categoriaPaiHub = categoriaPaiHub;
    }

    public String getCodHubPai() {
        return codHubPai;
    }

    public void setCodHubPai(String codHubPai) {
        this.codHubPai = codHubPai;
    }

    
    
    @Override
    public String toString() {
        return "SankhyaCategoria{" + "numCat=" + numCat + ", codHub=" + codHub + ", controle=" + controle + ", ativo=" + ativo + ", processado=" + processado + ", nome=" + nome + ", retorno=" + retorno + ", categoriaPai=" + categoriaPai + ", categoriaPaiHub=" + categoriaPaiHub + '}';
    }

    
}
