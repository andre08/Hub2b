package br.com.ventisol.sankhya.model.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SankhyaProduto {

    private Integer numPro;
    private Integer numVar;
    private Integer estoque;
    private Double altura;
    private Double largura;
    private Double profundidade;
    private String promocao;
    private String ativo;
    private String frete;
    private String processado;
    private Integer codProd;
    private String controle;
    private String descricao;
    private String descricaoLonga;
    private Date dtFinal;
    private Date dtInicio;
    private String modelo;
    private SankhyaCategoria categoria;
    private SankhyaMarca marca;
    private Double peso;
    private Double preco;
    private Double precoPromocao;
    private String unidade;
    private String ean;
    private Integer garantia;
    private BigDecimal ncm;
    private String tipoProduto;
    private SankhyaProduto ProdutoPai;
    private String video;
    private String sku;
    private String skuLojista;
    private String retorno;
    private String statusIntegracao;
    private List<SankhyaImagem> images;
    private Integer salesChannel;
    private Integer codStatus;
    private List<SankhyaEspecificacao> especificacoes;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(Double profundidade) {
        this.profundidade = profundidade;
    }

    public String getPromocao() {
        return promocao;
    }

    public void setPromocao(String promocao) {
        this.promocao = promocao;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getFrete() {
        return frete;
    }

    public void setFrete(String frete) {
        this.frete = frete;
    }

    public String getProcessado() {
        return processado;
    }

    public void setProcessado(String processado) {
        this.processado = processado;
    }


    public Integer getCodProd() {
        return codProd;
    }

    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoLonga() {
        return descricaoLonga;
    }

    public void setDescricaoLonga(String descricaoLonga) {
        this.descricaoLonga = descricaoLonga;
    }

    public Date getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getNumPro() {
        return numPro;
    }

    public void setNumPro(Integer numPro) {
        this.numPro = numPro;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPrecoPromocao() {
        return precoPromocao;
    }

    public void setPrecoPromocao(Double precoPromocao) {
        this.precoPromocao = precoPromocao;
    }

    public SankhyaCategoria getCategoria() {
        if(this.categoria == null){
            this.categoria = new SankhyaCategoria();
        }
        return categoria;
    }

    public void setCategoria(SankhyaCategoria categoria) {
        this.categoria = categoria;
    }

    public SankhyaMarca getMarca() {
        if (this.marca == null) {
            this.marca = new SankhyaMarca();
        }
        return marca;
    }

    public void setMarca(SankhyaMarca marca) {
        this.marca = marca;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public BigDecimal getNcm() {
        return ncm;
    }

    public void setNcm(BigDecimal ncm) {
        this.ncm = ncm;
    }

    public SankhyaProduto getProdutoPai() {
        if(ProdutoPai == null){
            ProdutoPai = new SankhyaProduto();
        }
        return ProdutoPai;
    }

    public void setProdutoPai(SankhyaProduto ProdutoPai) {
        this.ProdutoPai = ProdutoPai;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuLojista() {
        return skuLojista;
    }

    public void setSkuLojista(String skuLojista) {
        this.skuLojista = skuLojista;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public String getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(String statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public List<SankhyaImagem> getImages() {
        return images;
    }

    public void setImages(List<SankhyaImagem> images) {
        this.images = images;
    }

    public Integer getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(Integer salesChannel) {
        this.salesChannel = salesChannel;
    }

    public Integer getNumVar() {
        return numVar;
    }

    public void setNumVar(Integer numVar) {
        this.numVar = numVar;
    }

    public List<SankhyaEspecificacao> getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(List<SankhyaEspecificacao> especificacoes) {
        this.especificacoes = especificacoes;
    }

    public Integer getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(Integer codStatus) {
        this.codStatus = codStatus;
    }

    
    
}
