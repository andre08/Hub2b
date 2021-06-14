package br.com.ventisol.hub2b.model.produto;

import java.math.BigDecimal;
import java.util.List;

public class Produto {

    private Integer salesChannel;
    private String sku;
    private String marketplaceSKU;
    private String parentSKU;
    private String name;
    private String sourceDescription;
    private String description;
    private String ean13;
    private String brand;
    private Double height_m;
    private Double width_m;
    private Double length_m;
    private Double height;
    private Double width;
    private Double length;
    private String dimensionUnit;
    private Double weightKg;
    private Integer warrantyMonths;
    private String videoURL;
    private String status;
    private String statusMessage;
    private String productType;
    private String productURL;
    private String listingType;
    private String sourceId;
    private String marketplaceId;
    private String category;
    private String ncm;
    private Double priceBase;
    private Double priceSale;
    private String priceCurrency;
    private String handlingTime;
    private Integer stock;
    private Integer idTypeCondition;
    private List<ProdutoEspecificacoes> specifications;
    private List<ProdutoImagem> image;

    public Integer getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(Integer salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMarketplaceSKU() {
        return marketplaceSKU;
    }

    public void setMarketplaceSKU(String marketplaceSKU) {
        this.marketplaceSKU = marketplaceSKU;
    }

    public String getParentSKU() {
        return parentSKU;
    }

    public void setParentSKU(String parentSKU) {
        this.parentSKU = parentSKU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getHeight_m() {
        return height_m;
    }

    public void setHeight_m(Double height_m) {
        this.height_m = height_m;
    }

    public Double getWidth_m() {
        return width_m;
    }

    public void setWidth_m(Double width_m) {
        this.width_m = width_m;
    }

    public Double getLength_m() {
        return length_m;
    }

    public void setLength_m(Double length_m) {
        this.length_m = length_m;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getDimensionUnit() {
        return dimensionUnit;
    }

    public void setDimensionUnit(String dimensionUnit) {
        this.dimensionUnit = dimensionUnit;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public Double getPriceBase() {
        return priceBase;
    }

    public void setPriceBase(Double priceBase) {
        this.priceBase = priceBase;
    }

    public Double getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(Double priceSale) {
        this.priceSale = priceSale;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getIdTypeCondition() {
        return idTypeCondition;
    }

    public void setIdTypeCondition(Integer idTypeCondition) {
        this.idTypeCondition = idTypeCondition;
    }

    public List<ProdutoEspecificacoes> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<ProdutoEspecificacoes> specifications) {
        this.specifications = specifications;
    }

    public List<ProdutoImagem> getImage() {
        return image;
    }

    public void setImage(List<ProdutoImagem> image) {
        this.image = image;
    }

    
    
}
