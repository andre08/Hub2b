package br.com.ventisol.hub2b.model.produto;

public class ProdutoSku {

    private String sku;
    private String skuLojista;
    private String description;
    private Integer salesChannel;
    private String marketplaceSKU;
    private String status;
    private String statusMessage;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(Integer salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getSkuLojista() {
        return skuLojista;
    }

    public void setSkuLojista(String skuLojista) {
        this.skuLojista = skuLojista;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarketplaceSKU() {
        return marketplaceSKU;
    }

    public void setMarketplaceSKU(String marketplaceSKU) {
        this.marketplaceSKU = marketplaceSKU;
    }

    
    
}
