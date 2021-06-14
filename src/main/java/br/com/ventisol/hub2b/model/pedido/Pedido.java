package br.com.ventisol.hub2b.model.pedido;

import java.util.List;

public class Pedido {

    private String canceledDate;
    private PedidoReferencia reference;
    private PedidoStatus status;
    private List<PedidoProduto> products;
    private PedidoCliente customer;
    private PedidoRemessa shipping;
    private PedidoPagamento payment;

    public String getCanceledDate() {
        return canceledDate;
    }

    public void setCanceledDate(String canceledDate) {
        this.canceledDate = canceledDate;
    }

    public PedidoReferencia getReference() {
        return reference;
    }

    public void setReference(PedidoReferencia reference) {
        this.reference = reference;
    }

    public PedidoStatus getStatus() {
        if (status == null) {
            status = new PedidoStatus();
        }
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }

    public List<PedidoProduto> getProducts() {
        return products;
    }

    public void setProducts(List<PedidoProduto> products) {
        this.products = products;
    }

    public PedidoCliente getCustomer() {
        return customer;
    }

    public void setCustomer(PedidoCliente customer) {
        this.customer = customer;
    }

    public PedidoRemessa getShipping() {
        return shipping;
    }

    public void setShipping(PedidoRemessa shipping) {
        this.shipping = shipping;
    }

    public PedidoPagamento getPayment() {
        return payment;
    }

    public void setPayment(PedidoPagamento payment) {
        this.payment = payment;
    }

}
