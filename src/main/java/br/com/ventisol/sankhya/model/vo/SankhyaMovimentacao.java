package br.com.ventisol.sankhya.model.vo;

public class SankhyaMovimentacao {

    private Integer nuMov;
    private Integer nuNota;
    private Integer codProd;
    private Integer qtdNeg;
    private Integer vlrMov;
    private Double vlrUnit;
    private Integer estoqueAtual;
    private String sku;

    public Integer getNuMov() {
        return nuMov;
    }

    public void setNuMov(Integer nuMov) {
        this.nuMov = nuMov;
    }

    public Integer getNuNota() {
        return nuNota;
    }

    public void setNuNota(Integer nuNota) {
        this.nuNota = nuNota;
    }

    public Integer getCodProd() {
        return codProd;
    }

    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    public Integer getQtdNeg() {
        return qtdNeg;
    }

    public void setQtdNeg(Integer qtdNeg) {
        this.qtdNeg = qtdNeg;
    }

    public Integer getVlrMov() {
        return vlrMov;
    }

    public void setVlrMov(Integer vlrMov) {
        this.vlrMov = vlrMov;
    }

    public Double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(Double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }

    public Integer getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(Integer estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

}
