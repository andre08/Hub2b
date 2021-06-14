package br.com.ventisol.sankhya.model.vo;

import java.util.Date;

public class SankhyaAnuncio {

    private Integer numPro;
    private Integer numAnu;
    private Integer codMpk;
    private Double vlrPrecoAnterior;
    private Double vlrPreco;
    private Integer canalDeVenda;
    private Date dtInicial;
    private Date dtFinal;
    private String processado;
    private String controle;
    private String sku;
    private Integer status;

    public Integer getNumPro() {
        return numPro;
    }

    public void setNumPro(Integer numPro) {
        this.numPro = numPro;
    }

    public Integer getNumAnu() {
        return numAnu;
    }

    public void setNumAnu(Integer numAnu) {
        this.numAnu = numAnu;
    }

    public Integer getCodMpk() {
        return codMpk;
    }

    public void setCodMpk(Integer codMpk) {
        this.codMpk = codMpk;
    }

    public Double getVlrPrecoAnterior() {
        return vlrPrecoAnterior;
    }

    public void setVlrPrecoAnterior(Double vlrPrecoAnterior) {
        this.vlrPrecoAnterior = vlrPrecoAnterior;
    }

    public Double getVlrPreco() {
        return vlrPreco;
    }

    public void setVlrPreco(Double vlrPreco) {
        this.vlrPreco = vlrPreco;
    }

    public Date getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(Date dtInicial) {
        this.dtInicial = dtInicial;
    }

    public Date getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

    public String getProcessado() {
        return processado;
    }

    public void setProcessado(String processado) {
        this.processado = processado;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public Integer getCanalDeVenda() {
        return canalDeVenda;
    }

    public void setCanalDeVenda(Integer canalDeVenda) {
        this.canalDeVenda = canalDeVenda;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    

}
