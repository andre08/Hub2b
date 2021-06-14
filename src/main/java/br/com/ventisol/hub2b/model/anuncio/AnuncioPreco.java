package br.com.ventisol.hub2b.model.anuncio;

public class AnuncioPreco {

    private String itemId;
    private Integer salesChannel;
    private Double price;
    private Double listPrice;
    private String validFrom;
    private String validTo;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(Integer salesChannel) {
        this.salesChannel = salesChannel;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        return "AnuncioPreco{" + "itemId=" + itemId + ", salesChannel=" + salesChannel + ", price=" + price + ", listPrice=" + listPrice + ", validFrom=" + validFrom + ", validTo=" + validTo + '}';
    }



}
