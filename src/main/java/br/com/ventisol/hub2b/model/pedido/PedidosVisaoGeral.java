package br.com.ventisol.hub2b.model.pedido;

import br.com.ventisol.hub2b.model.auxiliar.Erro;

public class PedidosVisaoGeral extends Erro{
  private Integer pending;
  private Integer approved;;
  private Integer invoiced;
  private Integer shipped;
  private Integer delivered;
  private Integer completed;
  private Integer canceled;

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public Integer getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(Integer invoiced) {
        this.invoiced = invoiced;
    }

    public Integer getShipped() {
        return shipped;
    }

    public void setShipped(Integer shipped) {
        this.shipped = shipped;
    }

    public Integer getDelivered() {
        return delivered;
    }

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Integer getCanceled() {
        return canceled;
    }

    public void setCanceled(Integer canceled) {
        this.canceled = canceled;
    }

    @Override
    public String toString() {
        return "(VISÃO GERAL) Pendentes: " + pending + ", Aprovados: " + approved + ", Faturados: " + invoiced + ", Enviados: " + shipped + ", Entregue: " + delivered + ", Completos: " + completed + ", Cancelados: " + canceled;
    }
  
  
}
