package br.com.ventisol.sankhya.model.dao;

import br.com.ventisol.sankhya.model.service.ConexaoOracle;
import br.com.ventisol.sankhya.model.vo.SankhyaCategoria;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaDao {

    Connection connection = null;
    ResultSet rs = null;

    public CategoriaDao(Connection connection) {
        this.connection = connection;
    }

    public CategoriaDao() {
    }

    public List<SankhyaCategoria> buscarSankhyaCategoriasNaoProcessados() throws SQLException {
        List<SankhyaCategoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM ("
                + "SELECT "
                + "    CAT.NUMCAT,CAT.NUMPREF,CAT.NOME,CAT.ATIVO,CAT.PROCESSADO,CAT.CONTROLE,CAT.CODHUB,CAT.RETORNO,CAT.CATEGORIAPAI, CAT2.CODHUB AS CATEGORIAPAIHUB, CAT.CODHUBPAI"
                + " FROM "
                + "    AD_HMPCAT CAT "
                + "LEFT JOIN AD_HMPCAT CAT2 ON "
                + "   CAT2.NUMCAT = CAT.CATEGORIAPAI "
                + "WHERE  "
                + "    CAT.ATIVO = 'S' AND CAT.PROCESSADO = 'N'"
                + ") "
                + "    WHERE (CATEGORIAPAI IS NULL OR (CATEGORIAPAI IS NOT NULL AND CATEGORIAPAIHUB IS NOT NULL))";
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            SankhyaCategoria categoria = new SankhyaCategoria();
            categoria.setAtivo(rs.getString("ATIVO"));
            categoria.setCodHub(rs.getString("CODHUB"));
            categoria.setControle(rs.getString("CONTROLE"));
            categoria.setNome(rs.getString("NOME"));
            categoria.setNumCat(rs.getInt("NUMCAT"));
            categoria.setProcessado(rs.getString("PROCESSADO"));
            categoria.setRetorno(rs.getString("RETORNO"));
            categoria.setCategoriaPai(rs.getInt("CATEGORIAPAI"));
            categoria.setCategoriaPaiHub(rs.getInt("CATEGORIAPAIHUB"));
            categoria.setCodHubPai(rs.getString("CODHUBPAI"));
            categorias.add(categoria);
        }
        return categorias;
    }

    public int marcarComoProcessado(SankhyaCategoria sankhyaCategoria) throws SQLException {
        connection = ConexaoOracle.ObterConexao();
        CallableStatement stmt = connection.prepareCall(""
                + "UPDATE "
                + "    AD_HMPCAT "
                + "SET PROCESSADO = 'S', CONTROLE = 'INTEGRACAO'"
                + "WHERE AD_HMPCAT.NUMCAT = ?"
        );
        stmt.setInt(1, sankhyaCategoria.getNumCat());
       
        int executeUpdate = stmt.executeUpdate();
        return executeUpdate;
    }

    public Integer inserir(SankhyaCategoria sankhyaCategoria) throws SQLException {
        CallableStatement stmt = ConexaoOracle.ObterConexao().prepareCall(""
                + "INSERT INTO "
                + "    AD_HMPCAT "
                + " (NUMCAT,NUMPREF, CODHUB, CODHUBPAI,NOME,ATIVO,CONTROLE,PROCESSADO) "
                + " VALUES( "
                + " (SELECT NVL(MAX(NUMCAT) + 1,1) FROM AD_HMPCAT)"
                + ", 1"
                + ", ?"
                + ", ?"
                + ", ?"
                + ",'S'"
                + ",'INTEGRACAO'"
                + ",'S'"
                + ")"
        );
        stmt.setString(1, sankhyaCategoria.getCodHub());
        stmt.setString(2, sankhyaCategoria.getCodHubPai());
        stmt.setString(3, sankhyaCategoria.getNome());
    
        int executeUpdate = stmt.executeUpdate();  
        return executeUpdate;

    }

    public SankhyaCategoria buscarCategoriaPeloCodHub(String codHub) {
        try {
            SankhyaCategoria categoria = new SankhyaCategoria();
            String sql = "SELECT * FROM ("
                    + "SELECT "
                    + "    CAT.NUMCAT,CAT.NUMPREF,CAT.NOME,CAT.ATIVO,CAT.PROCESSADO,CAT.CONTROLE,CAT.CODHUB,CAT.RETORNO,CAT.CATEGORIAPAI, CAT2.CODHUB AS CATEGORIAPAIHUB, CAT.CODHUBPAI "
                    + "FROM "
                    + "    AD_HMPCAT CAT "
                    + "LEFT JOIN AD_HMPCAT CAT2 ON "
                    + "   CAT2.NUMCAT = CAT.CATEGORIAPAI "
                    + ") WHERE CODHUB = ? ";

            connection = ConexaoOracle.ObterConexao();
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(1, codHub);
            rs = stmt.executeQuery();
            if (rs.next()) {
                categoria.setAtivo(rs.getString("ATIVO"));
                categoria.setCodHub(rs.getString("CODHUB"));
                categoria.setControle(rs.getString("CONTROLE"));
                categoria.setNome(rs.getString("NOME"));
                categoria.setNumCat(rs.getInt("NUMCAT"));
                categoria.setProcessado(rs.getString("PROCESSADO"));
                categoria.setRetorno(rs.getString("RETORNO"));
                categoria.setCategoriaPai(rs.getInt("CATEGORIAPAI"));
                categoria.setCategoriaPaiHub(rs.getInt("CATEGORIAPAIHUB"));
                categoria.setCodHubPai(rs.getString("CODHUBPAI"));
            }
            return categoria;
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
