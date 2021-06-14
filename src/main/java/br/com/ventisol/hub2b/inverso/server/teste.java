package br.com.ventisol.hub2b.inverso.server;

import br.com.ventisol.sankhya.model.dao.EspecificacaoDao;
import br.com.ventisol.sankhya.model.vo.SankhyaEspecificacao;
import java.sql.SQLException;

public class teste {

    public static void main(String[] args) throws SQLException {
        EspecificacaoDao especificacaoDao = new EspecificacaoDao();
        SankhyaEspecificacao sankhyaEspecificacao = new SankhyaEspecificacao();
        sankhyaEspecificacao.setNome("Material");
        sankhyaEspecificacao.setValor("Plástico");
        sankhyaEspecificacao.setTipo("Specification");
        especificacaoDao.inserir(sankhyaEspecificacao, "370");
    }
}
