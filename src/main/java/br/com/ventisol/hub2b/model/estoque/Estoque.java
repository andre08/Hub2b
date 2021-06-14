package br.com.ventisol.hub2b.model.estoque;

public class Estoque {
    private Integer wareHouseId;
    private String itemId;
    private Integer quantity;
    private Integer crossDocking;

    public Integer getWareHouseId() {
        return wareHouseId;
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getCrossDocking() {
        return crossDocking;
    }

    public void setWareHouseId(Integer wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCrossDocking(Integer crossDocking) {
        this.crossDocking = crossDocking;
    }
    
    
    
    
}
