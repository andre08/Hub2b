package br.com.ventisol.sankhya.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class SankhyaFaturar {

    String serieNota;
    String chaveNfe;
    String numProtoc;
    Double vlrNota;
    Date dtFatur;
    BigDecimal numPed;
    BigDecimal pedidoId;

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    public String getChaveNfe() {
        return chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        this.chaveNfe = chaveNfe;
    }

    public String getNumProtoc() {
        return numProtoc;
    }

    public void setNumProtoc(String numProtoc) {
        this.numProtoc = numProtoc;
    }

    public Double getVlrNota() {
        return vlrNota;
    }

    public void setVlrNota(Double vlrNota) {
        this.vlrNota = vlrNota;
    }

    public Date getDtFatur() {
        return dtFatur;
    }

    public void setDtFatur(Date dtFatur) {
        this.dtFatur = dtFatur;
    }

    public BigDecimal getNumPed() {
        return numPed;
    }

    public void setNumPed(BigDecimal numPed) {
        this.numPed = numPed;
    }

    public BigDecimal getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(BigDecimal pedidoId) {
        this.pedidoId = pedidoId;
    }
    
}
