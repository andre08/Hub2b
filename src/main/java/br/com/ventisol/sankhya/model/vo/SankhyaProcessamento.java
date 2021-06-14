package br.com.ventisol.sankhya.model.vo;

import java.math.BigDecimal;

public class SankhyaProcessamento {
    
    private BigDecimal nuProcesso;
    private BigDecimal modulo;
    private String parametro;
    private String referencia;
    
    public BigDecimal getNuProcesso() {
        return nuProcesso;
    }

    public void setNuProcesso(BigDecimal nuProcesso) {
        this.nuProcesso = nuProcesso;
    }

    public BigDecimal getModulo() {
        return modulo;
    }

    public void setModulo(BigDecimal modulo) {
        this.modulo = modulo;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
