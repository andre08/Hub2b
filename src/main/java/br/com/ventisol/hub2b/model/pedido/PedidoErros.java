package br.com.ventisol.hub2b.model.pedido;

import br.com.ventisol.hub2b.model.auxiliar.Erro;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "erros")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidoErros {

    @XmlElement(name = "erros")
    private PedidoErros dadosErros;

    @XmlElement(name = "erro")
    private List<Erro> erros = new ArrayList<Erro>();


    public void addErro(Erro erro) {
        erros.add(erro);
    }

    public PedidoErros getDadosErros() {
        return dadosErros;
    }

    public List<Erro> getErroList() {
        return erros;
    }

    public void setErroList(List<Erro> erros) {
        this.erros = erros;
    }

    
    
    public void setDadosErros(PedidoErros dadosErros) {
        this.dadosErros = dadosErros;
    }
    
    

}
