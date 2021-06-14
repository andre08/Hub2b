package br.com.ventisol.hub2b.inverso.vo;

import br.com.ventisol.hub2b.model.categoria.Categoria;
import java.util.List;

public class CategoriaList {

    List<Categoria> list;

    public List<Categoria> getCategoriaInverse() {
        return list;
    }

    public void setCategoriaInverse(List<Categoria> categoria) {
        this.list = categoria;
    }

    

}
