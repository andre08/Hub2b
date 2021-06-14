package br.com.ventisol.hub2b.model.pedido;

import br.com.ventisol.hub2b.model.auxiliar.Erro;

public class PedidoFaturar extends Erro {

    String xml;
    String key;
    String number;
    String cfop;
    String series;
    Double totalAmount;
    String issueDate;
    String message;
    String xmlReference;
    Integer statusCode;

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getXmlReference() {
        return xmlReference;
    }

    public void setXmlReference(String xmlReference) {
        this.xmlReference = xmlReference;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "PedidoFaturar{" + "xml=" + xml + ", key=" + key + ", number=" + number + ", cfop=" + cfop + ", series=" + series + ", totalAmount=" + totalAmount + ", issueDate=" + issueDate + ", message=" + message + ", xmlReference=" + xmlReference + ", statusCode=" + statusCode + '}';
    }

    
    

}
