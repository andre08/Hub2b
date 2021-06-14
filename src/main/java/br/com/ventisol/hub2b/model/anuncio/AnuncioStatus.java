package br.com.ventisol.hub2b.model.anuncio;

public class AnuncioStatus {

    private String itemId;
    private Integer salesChannel;
    private Integer status;

    public AnuncioStatus(String itemId, Integer salesChannel, Integer status) {
        this.itemId = itemId;
        this.salesChannel = salesChannel;
        this.status = status;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AnuncioStatus{" + "itemId=" + itemId + ", salesChannel=" + salesChannel + ", status=" + status + '}';
    }

}
