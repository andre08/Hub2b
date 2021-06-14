package br.com.ventisol.hub2b.inverso.vo;

import br.com.ventisol.hub2b.model.pagina.Pagina;
import br.com.ventisol.hub2b.model.produto.Produto;
import java.util.List;

public class ProdutoList {

    List<Produto> list;
    Pagina paging;

    public List<Produto> getProdutoList() {
        return list;
    }

    public void setProdutoList(List<Produto> categoria) {
        this.list = categoria;
    }

    public List<Produto> getList() {
        return list;
    }

    public void setList(List<Produto> list) {
        this.list = list;
    }

    public Pagina getPaging() {
        return paging;
    }

    public void setPaging(Pagina paging) {
        this.paging = paging;
    }

}
