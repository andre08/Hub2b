package br.com.ventisol.hub2b.model.pedido;

import java.math.BigDecimal;

public class PedidoReferencia {

    private Integer idTenant;
    private String store;
    private BigDecimal id;
    private PedidoSistema system;
    private String virtual;
    private String source;

    public Integer getIdTenant() {
        return idTenant;
    }

    public void setIdTenant(Integer idTenant) {
        this.idTenant = idTenant;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public PedidoSistema getSystem() {
        return system;
    }

    public void setSystem(PedidoSistema system) {
        this.system = system;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVirtual() {
        return virtual;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }
        
}
