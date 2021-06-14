package br.com.ventisol.hub2b.model.pedido;

import br.com.ventisol.hub2b.model.auxiliar.Erro;
import java.util.List;

public class PedidosResponse extends Erro{

    Integer totalObjects;
    List<Pedido> response;

    public Integer getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(Integer totalObjects) {
        this.totalObjects = totalObjects;
    }

    public List<Pedido> getResponse() {
        return response;
    }

    public void setResponse(List<Pedido> response) {
        this.response = response;
    }

}
