package br.com.ventisol.sankhya.model.vo;

import java.math.BigDecimal;

public class SankhyaItem {

    private BigDecimal numPed;
    private BigDecimal numIte;
    private BigDecimal codigoIntegracao;
    private Double precoUnitario;
    private Double precoBase;
    private Double quantidade;
    private String nomeDoProduto;
    private Double descontoItem;
    private String referencia;

    public BigDecimal getNumPed() {
        return numPed;
    }

    public void setNumPed(BigDecimal numPed) {
        this.numPed = numPed;
    }

    public BigDecimal getNumIte() {
        return numIte;
    }

    public void setNumIte(BigDecimal numIte) {
        this.numIte = numIte;
    }

    public BigDecimal getCodigoIntegracao() {
        return codigoIntegracao;
    }

    public void setCodigoIntegracao(BigDecimal codigoIntegracao) {
        this.codigoIntegracao = codigoIntegracao;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(Double precoBase) {
        this.precoBase = precoBase;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeDoProduto() {
        return nomeDoProduto;
    }

    public void setNomeDoProduto(String nomeDoProduto) {
        this.nomeDoProduto = nomeDoProduto;
    }

    public Double getDescontoItem() {
        return descontoItem;
    }

    public void setDescontoItem(Double descontoItem) {
        this.descontoItem = descontoItem;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return "SankhyaItem{" + "numPed=" + numPed + ", numIte=" + numIte + ", codigoIntegracao=" + codigoIntegracao + ", precoUnitario=" + precoUnitario + ", precoBase=" + precoBase + ", quantidade=" + quantidade + ", nomeDoProduto=" + nomeDoProduto + ", descontoItem=" + descontoItem + ", referencia=" + referencia + '}';
    }

}
