package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.hub2b.model.produto.ProdutoSku;
import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaCategoria;
import br.com.ventisol.sankhya.model.vo.SankhyaEspecificacao;
import br.com.ventisol.sankhya.model.vo.SankhyaProduto;
import br.com.ventisol.util.Logs;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDao {

    Connection connection = null;

    public ProdutoDao(Connection connection) {
        this.connection = connection;
    }

    public ProdutoDao() {
    }

    public List<SankhyaProduto> buscarSankhyaProdutosSemSkuLojista() throws SQLException {
        List<SankhyaProduto> produtos = new ArrayList<>();
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt = connection.prepareCall(
                "SELECT DISTINCT "
                + "    PRO.NUMPRO "
                + "    , PRO.SKU "
                + "    , ANU.SKULOJISTA "
                + "    , MKT.CANALDEVENDA AS SALESCHANNEL "
                + "FROM AD_HMPPRO PRO "
                + "INNER JOIN AD_HMPANU ANU ON PRO.NUMPRO = ANU.NUMPRO "
                + "INNER JOIN AD_HMPMKTP MKT ON MKT.NUMMKTP = ANU.CODMPK "
                + "WHERE"
                + "   ANU.SKULOJISTA IS NULL "
                + "   AND PRO.ATIVO = 'S' ");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SankhyaProduto produto = new SankhyaProduto();
            produto.setNumPro(rs.getInt("NUMPRO"));
            produto.setSku(rs.getString("SKU"));
            produto.setSkuLojista(rs.getString("SKULOJISTA"));
            produto.setSalesChannel(rs.getInt("SALESCHANNEL"));
            produtos.add(produto);
        }
        return produtos;
    }

    public List<SankhyaProduto> buscarSankhyaProdutosSicronizando() throws SQLException {
        List<SankhyaProduto> produtos = new ArrayList<>();
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt = connection.prepareCall(
                "SELECT DISTINCT "
                + "    PRO.NUMPRO "
                + "    , PRO.SKU "
                + "    , ANU.SKULOJISTA "
                + "    , MKT.CANALDEVENDA AS SALESCHANNEL "
                + "FROM AD_HMPPRO PRO "
                + "INNER JOIN AD_HMPANU ANU ON PRO.NUMPRO = ANU.NUMPRO "
                + "INNER JOIN AD_HMPMKTP MKT ON MKT.NUMMKTP = ANU.CODMPK "
                + "WHERE"
                + "   ANU.STATUS = 5 "
                + "   AND PRO.ATIVO = 'S' ");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SankhyaProduto produto = new SankhyaProduto();
            produto.setNumPro(rs.getInt("NUMPRO"));
            produto.setSku(rs.getString("SKU"));
            produto.setSkuLojista(rs.getString("SKULOJISTA"));
            produto.setSalesChannel(rs.getInt("SALESCHANNEL"));
            produtos.add(produto);
        }
        return produtos;
    }

    public List<SankhyaProduto> buscarSankhyaProdutosNaoProcessados() throws SQLException {
        List<SankhyaProduto> produtos = new ArrayList<>();
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }

        CallableStatement stmt = connection.prepareCall(""
                + "SELECT  "
                + "    MPRO.NUMPRO, MPRO.ALTURA, MPRO.LARGURA, MPRO.PROFUNDIDADE "
                + "    , MPRO.PROMOCAO,MPRO.ATIVO,FRETE,MPRO.PROCESSADO,PRO.CODPROD,MPRO.CONTROLE,MPRO.DESCRICAO "
                + "    , DESCRICAOLONGA,MODELO,CAT.DESCRICAO AS CATEGORIA,MAR.NOME AS MARCA "
                + "    , PESO, PRECO, PRECOPROMOCAO,MPRO.UNIDADE,PRO.REFERENCIA AS EAN,GARANTIA,NCM,TIPOPRODUTO,PRODUTOPAI "
                + "    , VIDEO,SKU,SKULOJISTA,MPRO.RETORNO,STATUSINTEGRACAO,MPRO.SALESCHANNEL "
                + "    , (SELECT COUNT(*) FROM AD_HMPPES WHERE NUMPRO = MPRO.NUMPRO) AS QTDE_ESP "
                + "FROM  "
                + "    AD_HMPPRO MPRO "
                + "INNER JOIN TGFPRO PRO ON "
                + "    PRO.CODPROD = MPRO.CODPROD "
                + "LEFT JOIN TGFCAT CAT ON "
                + "    CAT.CODCAT = MPRO.NUMCAT "
                + "LEFT JOIN AD_HMPMAR MAR ON "
                + "    MAR.NUMMAR = MPRO.NUMMAR "
                + "WHERE "
                + "     MPRO.PROCESSADO = 'N' "
                + "     AND MPRO.ATIVO = 'S'");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SankhyaProduto produto = new SankhyaProduto();
            produto.setNumPro(rs.getInt("NUMPRO"));
            EstoqueDao estoqueDao = new EstoqueDao(connection);
            Integer estoque = estoqueDao.buscarEstoqueDoProduto(produto.getNumPro());
            produto.setEstoque(estoque);
            produto.setAltura(rs.getDouble("ALTURA"));
            produto.setLargura(rs.getDouble("LARGURA"));
            produto.setProfundidade(rs.getDouble("PROFUNDIDADE"));
            produto.setPromocao(rs.getString("PROMOCAO"));
            produto.setAtivo(rs.getString("ATIVO"));
            produto.setFrete(rs.getString("FRETE"));
            produto.setProcessado(rs.getString("PROCESSADO"));
            produto.setCodProd(rs.getInt("CODPROD"));
            produto.setControle(rs.getString("CONTROLE"));
            produto.setDescricao(rs.getString("DESCRICAO"));
            produto.setDescricaoLonga(rs.getString("DESCRICAOLONGA"));
            produto.setModelo("MODELO");
            produto.getMarca().setNome(rs.getString("MARCA"));
            produto.setPeso(rs.getDouble("PESO"));
            produto.setPreco(rs.getDouble("PRECO"));
            produto.setPrecoPromocao((rs.getDouble("PRECOPROMOCAO")));
            produto.setUnidade(rs.getString("UNIDADE"));
            produto.setEan(rs.getString("EAN"));
            produto.setNcm(rs.getBigDecimal("NCM"));
            produto.setTipoProduto(rs.getString("TIPOPRODUTO"));
            produto.getProdutoPai().setSku(rs.getString("PRODUTOPAI"));
            produto.setVideo(rs.getString("VIDEO"));
            produto.setSku(rs.getString("SKU"));
            produto.setSkuLojista(rs.getString("SKULOJISTA"));
            produto.setRetorno(rs.getString("RETORNO"));
            produto.setStatusIntegracao(rs.getString("STATUSINTEGRACAO"));
            produto.setSalesChannel(rs.getInt("SALESCHANNEL"));
            if(rs.getInt("QTDE_ESP") >= 1){
                EspecificacaoDao especificacaoDao = new EspecificacaoDao(connection);
                produto.setEspecificacoes(especificacaoDao.buscarSankhyaProdutosEspecificacoes(produto));
            }else{
                produto.setEspecificacoes(null);
            }
            produtos.add(produto);
        }
        return produtos;
    }

    public List<SankhyaProduto> buscarSankhyaVariedadesDeProdutosNaoProcessados() throws SQLException {
        List<SankhyaProduto> produtos = new ArrayList();
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "SELECT"
                + "      MPRO.NUMPRO, MPRO.ESTOQUE, MPRO.ALTURA, MPRO.LARGURA, MPRO.PROFUNDIDADE  "
                + "     , MPRO.PROMOCAO,MPRO.ATIVO,FRETE,VAR.PROCESSADO,PRO.CODPROD, VAR.CONTROLE,MPRO.DESCRICAO  "
                + "     , DESCRICAOLONGA,MODELO,CAT.DESCRICAO AS CATEGORIA,MAR.NOME AS MARCA  "
                + "     , PESO, PRECO, PRECOPROMOCAO,MPRO.UNIDADE,PRO.REFERENCIA AS EAN,GARANTIA,NCM, 3 AS TIPOPRODUTO,MPRO.SKU AS PRODUTOPAI  "
                + "     , VIDEO, VAR.SKU AS SKU,SKULOJISTA,MPRO.RETORNO,STATUSINTEGRACAO,MPRO.SALESCHANNEL,VAR.NUMVAR"
                + " FROM  "
                + "     AD_HMPVAR VAR "
                + " INNER JOIN AD_HMPPRO MPRO ON "
                + "     MPRO.NUMPRO = VAR.NUMPRO"
                + " INNER JOIN TGFPRO PRO ON  "
                + "     PRO.CODPROD = VAR.CODPROD  "
                + " LEFT JOIN TGFCAT CAT ON  "
                + "     CAT.CODCAT = MPRO.NUMCAT  "
                + " LEFT JOIN AD_HMPMAR MAR ON  "
                + "     MAR.NUMMAR = MPRO.NUMMAR  "
                + " WHERE  "
                + "     VAR.PROCESSADO = 'N'  "
                + "     AND VAR.ATIVO = 'S'");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaProduto produto = new SankhyaProduto();
            produto.setNumPro(rs.getInt("NUMPRO"));
            produto.setEstoque(rs.getInt("ESTOQUE"));
            produto.setAltura(rs.getDouble("ALTURA"));
            produto.setLargura(rs.getDouble("LARGURA"));
            produto.setProfundidade(rs.getDouble("PROFUNDIDADE"));
            produto.setPromocao(rs.getString("PROMOCAO"));
            produto.setAtivo(rs.getString("ATIVO"));
            produto.setFrete(rs.getString("FRETE"));
            produto.setProcessado(rs.getString("PROCESSADO"));
            produto.setCodProd(rs.getInt("CODPROD"));
            produto.setControle(rs.getString("CONTROLE"));
            produto.setDescricao(rs.getString("DESCRICAO"));
            produto.setDescricaoLonga(rs.getString("DESCRICAOLONGA"));
            produto.setModelo("MODELO");
            produto.getMarca().setNome(rs.getString("MARCA"));
            produto.setPeso(rs.getDouble("PESO"));
            produto.setPreco(rs.getDouble("PRECO"));
            produto.setPrecoPromocao((rs.getDouble("PRECOPROMOCAO")));
            produto.setUnidade(rs.getString("UNIDADE"));
            produto.setEan(rs.getString("EAN"));
            produto.setNcm(rs.getBigDecimal("NCM"));
            produto.setTipoProduto(rs.getString("TIPOPRODUTO"));
            produto.getProdutoPai().setSku(rs.getString("PRODUTOPAI"));
            produto.setVideo(rs.getString("VIDEO"));
            produto.setSku(rs.getString("SKU"));
            produto.setSkuLojista(rs.getString("SKULOJISTA"));
            produto.setRetorno(rs.getString("RETORNO"));
            produto.setStatusIntegracao(rs.getString("STATUSINTEGRACAO"));
            produto.setSalesChannel(rs.getInt("SALESCHANNEL"));
            produto.setNumVar(rs.getInt("NUMVAR"));
            EspecificacaoDao especificacaoDao = new EspecificacaoDao();
            produto.setEspecificacoes(especificacaoDao.buscarSankhyaVariacaoEspecificacoes(produto));
            produtos.add(produto);
        }
        return produtos;
    }

    public int marcarComoProcessado(SankhyaProduto sankhyaProduto) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "UPDATE "
                + "    AD_HMPPRO "
                + "SET PROCESSADO = 'S', CONTROLE = 'INTEGRACAO'"
                + "WHERE NUMPRO = ?"
        );
        stmt.setInt(1, sankhyaProduto.getNumPro());
        int executeUpdate = stmt.executeUpdate();
        return executeUpdate;

    }

    public int marcarVariedadeComoProcessada(SankhyaProduto sankhyaProduto) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "UPDATE "
                + "    AD_HMPVAR "
                + "SET PROCESSADO = 'S', CONTROLE = 'INTEGRACAO'"
                + "WHERE NUMPRO = ? AND NUMVAR = ?"
        );
        stmt.setInt(1, sankhyaProduto.getNumPro());
        stmt.setInt(2, sankhyaProduto.getNumVar());
        int executeUpdate = stmt.executeUpdate();
        return executeUpdate;
    }

    public void alterarStatusDosProdutosParaErro(String sku, String skuLojista, Integer codMpk, String erro) throws SQLException {
        CallableStatement stmt = null;
        try {
//            CallableStatement stmt;
//            stmt = connection.prepareCall(
//                    "UPDATE AD_HMPVANU SET STATUS = ?, CONTROLE = 'INTEGRACAO'  WHERE NUMPRO = (SELECT NUMPRO FROM AD_HMPVAR WHERE SKU = ?) AND CODMPK IN (SELECT NUMMKTP FROM AD_HMPMKTP WHERE CANALDEVENDA = ?) AND SKULOJISTA = ?"
//            );
//            stmt.setInt(1, 4);
//            stmt.setString(2, sku);
//            stmt.setInt(3, codMpk);
//            stmt.setString(4, skuLojista);
//            stmt.executeUpdate();
            if (connection == null) {
                connection = ConexaoOracle.ObterConexao();
            }
            stmt = connection.prepareCall(
                    "BEGIN UPDATE AD_HMPANU SET PROCESSADO = 'S', STATUS = ?, CONTROLE = 'INTEGRACAO' WHERE NUMPRO = (SELECT NUMPRO FROM AD_HMPPRO WHERE SKU = ?) AND CODMPK IN (SELECT NUMMKTP FROM AD_HMPMKTP WHERE CANALDEVENDA = ?) ; "
                    + "UPDATE AD_HMPPRO SET RETORNO = ?, CONTROLE = 'INTEGRACAO' WHERE NUMPRO = (SELECT NUMPRO FROM AD_HMPPRO WHERE SKU = ?); END;"
            );
            stmt.setInt(1, 4);
            stmt.setString(2, sku);
            stmt.setInt(3, codMpk);
            //stmt.setString(4, skuLojista);
            stmt.setString(4, erro);
            stmt.setString(5, sku);
            stmt.executeUpdate();
            Logs.gerarLog(Logs.TITULO_SUCESSO, "Produto " + sku + " marcado com erro, para o MarketPlace: "+codMpk, Logs.COLOR_SUCESSO);
        } catch (SQLException sql) {
            Logger.getLogger(CategoriaDao.class
                    .getName()).log(Level.SEVERE, null, sql);
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao Integrar: " + sql.getMessage() + sku + erro, Logs.COLOR_ERRO);
        }
    }

    public void alterarStatusDosProdutosParaSicronizado(List<ProdutoSku> produtoSkus) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        Boolean primeiro = true;
        StringBuilder where = new StringBuilder();
        for (ProdutoSku produtoSku : produtoSkus) {
            if (primeiro) {
                where.append("( SKU = '" + produtoSku.getSku() + "' AND CODMPK IN (SELECT NUMMKTP FROM AD_HMPMKTP WHERE CANALDEVENDA = " + produtoSku.getSalesChannel() + ") ) ");
                primeiro = false;
            } else {
                where.append(" OR (SKU = '" + produtoSku.getSku() + "' AND CODMPK IN (SELECT NUMMKTP FROM AD_HMPMKTP WHERE CANALDEVENDA = " + produtoSku.getSalesChannel() + ") ) ");
            }
        }
        if (produtoSkus.size() > 0) {
            CallableStatement stmt;
            stmt = connection.prepareCall(
                    "UPDATE AD_HMPVANU SET STATUS = 3, CONTROLE = 'INTEGRACAO' WHERE  "
                    + "NUMPRO IN ( "
                    + "(SELECT  "
                    + "    NUMPRO  "
                    + " FROM  "
                    + "     AD_HMPVAR  "
                    + " WHERE  "
                    + where
                    + "     ) "
                    + ")"
            );
            stmt.executeUpdate();
            CallableStatement stmt2;
            stmt2 = connection.prepareCall(
                    "UPDATE AD_HMPANU SET STATUS = 3, CONTROLE = 'INTEGRACAO' WHERE  "
                    + "NUMPRO IN ( "
                    + "(SELECT  "
                    + "    NUMPRO  "
                    + " FROM  "
                    + "     AD_HMPPRO  "
                    + " WHERE  "
                    + where
                    + "     ) "
                    + ")"
            );
            stmt2.executeUpdate();
            Logs.gerarLog(Logs.TITULO_SUCESSO, "Todos os Status Pendentes Foram Sicronizados", Logs.COLOR_SUCESSO);
        }
    }

    public void alterarStatusDosProdutoParaSicronizado(ProdutoSku produtoSkus) throws SQLException {
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall(
                "UPDATE AD_HMPANU SET STATUS = 3, CONTROLE = 'INTEGRACAO' WHERE  "
                + " NUMPRO = (SELECT NUMPRO FROM AD_HMPPRO WHERE SKU = ?) AND CODMPK = (SELECT MIN(NUMMKTP) FROM AD_HMPMKTP WHERE CANALDEVENDA = ?)"
        );
        stmt.setString(1, produtoSkus.getSku());
        stmt.setInt(2, produtoSkus.getSalesChannel());
        stmt.executeUpdate();
    }

    public Integer inserir(SankhyaProduto sankhyaProduto) {
        try {
            connection = ConexaoOracle.ObterConexao();
            CallableStatement stmt = connection.prepareCall(""
                    + "BEGIN  INSERT INTO  AD_HMPPRO(NUMPRO,"
                    + "                           NUMCAT, "
                    + "                           NUMMAR, "
                    + "                           DESCRICAO, "
                    + "                           MODELO, "
                    + "                           PRECO, "
                    + "                           PESO, "
                    + "                           ALTURA, "
                    + "                           LARGURA, "
                    + "                           PROFUNDIDADE, "
                    + "                           ATIVO, "
                    + "                           PROCESSADO, "
                    + "                           CONTROLE, "
                    + "                           RETORNO, "
                    + "                           UNIDADE, "
                    + "                           GARANTIA, "
                    + "                           TIPOPRODUTO, "
                    + "                           VIDEO, "
                    + "                           STATUSINTEGRACAO, "
                    + "                           SKU,"
                    + "                           SKULOJISTA,"
                    + "                           DESCRICAOLONGA )  "
                    + "                           VALUES ( "
                    + "                           (SELECT NVL(MAX(NUMPRO) + 1,1) FROM AD_HMPPRO),"
                    + "                           ?,?,?,?,?,?,?,?,?,? "
                    + "                           , ?,?,?,?,?,?,?,?,?,? "
                    + "                           , ?"
                    + "                           )"
                    + "  RETURNING NUMPRO INTO ? ; END;"
            );
            MarcaDao marcaDao = new MarcaDao();
            Integer numMar = marcaDao.buscarIdMarcaPeloNome(sankhyaProduto.getMarca().getNome()); // NUMMA  
            CategoriaDao categoriaDao = new CategoriaDao();
            SankhyaCategoria sankhyaCategoria = categoriaDao.buscarCategoriaPeloCodHub(sankhyaProduto.getCategoria().getCodHub());
            if (sankhyaCategoria == null) {
                stmt.setNull(1, Types.INTEGER);
            } else {
                if (sankhyaCategoria.getNumCat() == null) {
                    stmt.setNull(1, Types.INTEGER);
                } else {
                    stmt.setInt(1, sankhyaCategoria.getNumCat());
                }
            }
            if (numMar == null) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, numMar);
            }
            stmt.setString(3, sankhyaProduto.getDescricao());
            stmt.setString(4, sankhyaProduto.getModelo());
            stmt.setDouble(5, sankhyaProduto.getPreco());
            stmt.setDouble(6, sankhyaProduto.getPeso());
            stmt.setDouble(7, sankhyaProduto.getAltura());
            stmt.setDouble(8, sankhyaProduto.getLargura());
            stmt.setDouble(9, sankhyaProduto.getProfundidade());
            stmt.setString(10, sankhyaProduto.getAtivo());
            stmt.setString(11, sankhyaProduto.getProcessado());
            stmt.setString(12, sankhyaProduto.getControle());
            stmt.setString(13, sankhyaProduto.getRetorno());
            stmt.setString(14, sankhyaProduto.getUnidade());
            stmt.setInt(15, sankhyaProduto.getGarantia());
            stmt.setString(16, sankhyaProduto.getTipoProduto());
            stmt.setString(17, sankhyaProduto.getVideo());
            stmt.setString(18, sankhyaProduto.getStatusIntegracao());
            stmt.setString(19, sankhyaProduto.getSku());
            stmt.setString(20, sankhyaProduto.getSkuLojista());
            stmt.setString(21, sankhyaProduto.getDescricaoLonga());
            stmt.registerOutParameter(22, java.sql.Types.INTEGER);
            stmt.executeUpdate();
//        sankhyaProduto.getProdutoPai(); -- APENAS PARA VARIACAO
            sankhyaProduto.setNumPro(stmt.getInt(22));
            EspecificacaoDao especificacaoDao = new EspecificacaoDao();
            for (SankhyaEspecificacao especificacao : sankhyaProduto.getEspecificacoes()) {
                especificacaoDao.inserir(especificacao, sankhyaProduto.getSku());
            }
            return sankhyaProduto.getNumPro();
        } catch (SQLException e) {
            Logs.gerarLog(Logs.TITULO_ERRO, e.getMessage(), Logs.COLOR_ERRO);
            Throwable cause = e.getCause();
            Logs.gerarLog(Logs.TITULO_ERRO, cause.getMessage(), Logs.COLOR_ERRO);
        }
        return null;
    }

    public void alterarSkuLojista(Integer numPro, Integer salesChannel, String skuLojista) throws SQLException {
        if (connection == null) {
            connection = ConexaoOracle.ObterConexao();
        }
        CallableStatement stmt;
        stmt = connection.prepareCall(
                "UPDATE"
                + " AD_HMPANU SET "
                + " SKULOJISTA = ? "
                + " , CONTROLE = 'INTEGRACAO' "
                + " WHERE NUMPRO = ? "
                + " AND CODMPK = (SELECT MIN(NUMMKTP) FROM AD_HMPMKTP WHERE CANALDEVENDA = ?) "
        );
        stmt.setString(1, skuLojista);
        stmt.setInt(2, numPro);
        stmt.setInt(3, salesChannel);
        stmt.executeUpdate();
    }

}
