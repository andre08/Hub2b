package br.com.ventisol.sankhya.model.vo;

import java.math.BigDecimal;

public class SankhyaMarketPlace {

    private BigDecimal numMkpt;
    private String marketingplace;

    public BigDecimal getNumMkpt() {
        return numMkpt;
    }

    public void setNumMkpt(BigDecimal numMkp) {
        this.numMkpt = numMkp;
    }

    public String getMarketingplace() {
        return marketingplace;
    }

    public void setMarketingplace(String marketingplace) {
        this.marketingplace = marketingplace;
    }
    
}
