package br.com.ventisol.hub2b.model.produto;

import br.com.ventisol.hub2b.model.auxiliar.Paging;
import java.util.List;

public class ProdutoList {

    List<ProdutoSku> list;
    Paging paging;

    public ProdutoList() {
    }

    

    public List<ProdutoSku> getList() {
        return list;
    }

    public void setList(List<ProdutoSku> list) {
        this.list = list;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "ProdutoList{" + "list=" + list + ", paging=" + paging + '}';
    }

    
}
