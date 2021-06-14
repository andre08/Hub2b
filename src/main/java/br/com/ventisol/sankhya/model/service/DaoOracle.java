package br.com.ventisol.sankhya.model.service;

import java.sql.Connection;

public class DaoOracle {

public Connection conexao(){
     return  ConexaoOracle.ObterConexao();
}
   
}
