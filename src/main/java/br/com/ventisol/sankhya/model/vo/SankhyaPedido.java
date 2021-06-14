package br.com.ventisol.sankhya.model.vo;

import java.math.BigDecimal;

public class SankhyaPedido {

    private BigDecimal pedidoId;
    private BigDecimal numPed;
    private String dataVenda;
    private Double valorTotal;
    private Double pagamentoValor;
    private Double pagamentoValorParcela;
    private Double valorFrete;
    private Double valorDesconto;
    private String observacoes;
    private String observacoesLoja;
    private String statusId;
    private String codigoPedidoExterno;
    private String carrinho;
    private String origemPedido;
    private BigDecimal numCli;
    private String freteCodEnvio;
    private String formaPagamentoId;
    private String dtPrevisao;
    private Integer formaEntregaId;
    private String protocolo;
    private String entregaNome;
    private String entregaDocumento;
    private String entregaEmail;
    private String entregaTelefone;
    private String entregaLogradouro;
    private String entregaNumero;
    private String entregaComplementoEndereco;
    private String entregaBairro;
    private String entregaCidade;
    private String entregaEstado;
    private String entregaPais;
    private String entregaCep;
    private String formaEntregaAlias;
    private String ativo;
    private String processado;
    private Integer cartaoQuantidadeParcelas;
    private String formaDePagamentoAlias;
    private BigDecimal codMpk;

    public BigDecimal getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(BigDecimal pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getPagamentoValor() {
        return pagamentoValor;
    }

    public void setPagamentoValor(Double pagamentoValor) {
        this.pagamentoValor = pagamentoValor;
    }

    public Double getPagamentoValorParcela() {
        return pagamentoValorParcela;
    }

    public void setPagamentoValorParcela(Double pagamentoValorParcela) {
        this.pagamentoValorParcela = pagamentoValorParcela;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getObservacoesLoja() {
        return observacoesLoja;
    }

    public void setObservacoesLoja(String observacoesLoja) {
        this.observacoesLoja = observacoesLoja;
    }

    public String getCodigoPedidoExterno() {
        return codigoPedidoExterno;
    }

    public void setCodigoPedidoExterno(String codigoPedidoExterno) {
        this.codigoPedidoExterno = codigoPedidoExterno;
    }

    public String getOrigemPedido() {
        return origemPedido;
    }

    public void setOrigemPedido(String origemPedido) {
        this.origemPedido = origemPedido;
    }

    public BigDecimal getNumCli() {
        return numCli;
    }

    public void setNumCli(BigDecimal numCli) {
        this.numCli = numCli;
    }

    public String getFreteCodEnvio() {
        return freteCodEnvio;
    }

    public void setFreteCodEnvio(String freteCodEnvio) {
        this.freteCodEnvio = freteCodEnvio;
    }

    public String getFormaPagamentoId() {
        return formaPagamentoId;
    }

    public void setFormaPagamentoId(String formaPagamentoId) {
        this.formaPagamentoId = formaPagamentoId;
    }

    public String getDtPrevisao() {
        return dtPrevisao;
    }

    public void setDtPrevisao(String dtPrevisao) {
        this.dtPrevisao = dtPrevisao;
    }

    public Integer getFormaEntregaId() {
        return formaEntregaId;
    }

    public void setFormaEntregaId(Integer formaEntregaId) {
        this.formaEntregaId = formaEntregaId;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getEntregaNome() {
        return entregaNome;
    }

    public void setEntregaNome(String entregaNome) {
        this.entregaNome = entregaNome;
    }

    public String getEntregaDocumento() {
        return entregaDocumento;
    }

    public void setEntregaDocumento(String entregaDocumento) {
        this.entregaDocumento = entregaDocumento;
    }

    public String getEntregaEmail() {
        return entregaEmail;
    }

    public void setEntregaEmail(String entregaEmail) {
        this.entregaEmail = entregaEmail;
    }

    public String getEntregaTelefone() {
        return entregaTelefone;
    }

    public void setEntregaTelefone(String entregaTelefone) {
        this.entregaTelefone = entregaTelefone;
    }

    public String getEntregaLogradouro() {
        return entregaLogradouro;
    }

    public void setEntregaLogradouro(String entregaLogradouro) {
        this.entregaLogradouro = entregaLogradouro;
    }

    public String getEntregaNumero() {
        return entregaNumero;
    }

    public void setEntregaNumero(String entregaNumero) {
        this.entregaNumero = entregaNumero;
    }

    public String getEntregaComplementoEndereco() {
        return entregaComplementoEndereco;
    }

    public void setEntregaComplementoEndereco(String entregaComplementoEndereco) {
        this.entregaComplementoEndereco = entregaComplementoEndereco;
    }

    public String getEntregaBairro() {
        return entregaBairro;
    }

    public void setEntregaBairro(String entregaBairro) {
        this.entregaBairro = entregaBairro;
    }

    public String getEntregaCidade() {
        return entregaCidade;
    }

    public void setEntregaCidade(String entregaCidade) {
        this.entregaCidade = entregaCidade;
    }

    public String getEntregaEstado() {
        return entregaEstado;
    }

    public void setEntregaEstado(String entregaEstado) {
        this.entregaEstado = entregaEstado;
    }

    public String getEntregaPais() {
        return entregaPais;
    }

    public void setEntregaPais(String entregaPais) {
        this.entregaPais = entregaPais;
    }

    public String getEntregaCep() {
        return entregaCep;
    }

    public void setEntregaCep(String entregaCep) {
        this.entregaCep = entregaCep;
    }

    public String getFormaEntregaAlias() {
        return formaEntregaAlias;
    }

    public void setFormaEntregaAlias(String formaEntregaAlias) {
        this.formaEntregaAlias = formaEntregaAlias;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getProcessado() {
        return processado;
    }

    public void setProcessado(String processado) {
        this.processado = processado;
    }

    public Integer getCartaoQuantidadeParcelas() {
        return cartaoQuantidadeParcelas;
    }

    public void setCartaoQuantidadeParcelas(Integer cartaoQuantidadeParcelas) {
        this.cartaoQuantidadeParcelas = cartaoQuantidadeParcelas;
    }

    public BigDecimal getCodMpk() {
        return codMpk;
    }

    public void setCodMpk(BigDecimal codMpk) {
        this.codMpk = codMpk;
    }

    public String getFormaDePagamentoAlias() {
        return formaDePagamentoAlias;
    }

    public void setFormaDePagamentoAlias(String formaDePagamentoAlias) {
        this.formaDePagamentoAlias = formaDePagamentoAlias;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public BigDecimal getNumPed() {
        return numPed;
    }

    public void setNumPed(BigDecimal numPed) {
        this.numPed = numPed;
    }

    public String getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(String carrinho) {
        this.carrinho = carrinho;
    }

    @Override
    public String toString() {
        return "SankhyaPedido{" + "pedidoId=" + pedidoId + ", numPed=" + numPed + ", dataVenda=" + dataVenda + ", valorTotal=" + valorTotal + ", pagamentoValor=" + pagamentoValor + ", pagamentoValorParcela=" + pagamentoValorParcela + ", valorFrete=" + valorFrete + ", valorDesconto=" + valorDesconto + ", observacoes=" + observacoes + ", observacoesLoja=" + observacoesLoja + ", statusId=" + statusId + ", codigoPedidoExterno=" + codigoPedidoExterno + ", origemPedido=" + origemPedido + ", numCli=" + numCli + ", freteCodEnvio=" + freteCodEnvio + ", formaPagamentoId=" + formaPagamentoId + ", dtPrevisao=" + dtPrevisao + ", formaEntregaId=" + formaEntregaId + ", protocolo=" + protocolo + ", entregaNome=" + entregaNome + ", entregaDocumento=" + entregaDocumento + ", entregaEmail=" + entregaEmail + ", entregaTelefone=" + entregaTelefone + ", entregaLogradouro=" + entregaLogradouro + ", entregaNumero=" + entregaNumero + ", entregaComplementoEndereco=" + entregaComplementoEndereco + ", entregaBairro=" + entregaBairro + ", entregaCidade=" + entregaCidade + ", entregaEstado=" + entregaEstado + ", entregaPais=" + entregaPais + ", entregaCep=" + entregaCep + ", formaEntregaAlias=" + formaEntregaAlias + ", ativo=" + ativo + ", processado=" + processado + ", cartaoQuantidadeParcelas=" + cartaoQuantidadeParcelas + ", formaDePagamentoAlias=" + formaDePagamentoAlias + ", codMpk=" + codMpk + '}';
    }

  
}
