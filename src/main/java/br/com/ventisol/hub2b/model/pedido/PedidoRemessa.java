package br.com.ventisol.hub2b.model.pedido;

public class PedidoRemessa {

    private String estimatedDeliveryDate;
    private String responsible;
    private String provider;
    private String service;
    private Double price;
    private String receiverName;
    private PedidoEndereco address;

    public String getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public PedidoEndereco getAddress() {
        return address;
    }

    public void setAddress(PedidoEndereco address) {
        this.address = address;
    }
    
    
}
