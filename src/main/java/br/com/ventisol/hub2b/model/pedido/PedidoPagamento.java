package br.com.ventisol.hub2b.model.pedido;

public class PedidoPagamento {

    private String purchaseDate;
    private String approvedDate;
    private Double totalAmount;
    private Double totalAmountPlusShipping;
    private Double totalDiscount;
    private String method;
    private Integer installments;
    private PedidoEndereco address;

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmountPlusShipping() {
        return totalAmountPlusShipping;
    }

    public void setTotalAmountPlusShipping(Double totalAmountPlusShipping) {
        this.totalAmountPlusShipping = totalAmountPlusShipping;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public PedidoEndereco getAddress() {
        return address;
    }

    public void setAddress(PedidoEndereco address) {
        this.address = address;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }


    
}
