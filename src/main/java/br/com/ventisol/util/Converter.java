package br.com.ventisol.util;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Converter {

    public static java.sql.Date dataUtilParaSql(String data, String formato) {
        SimpleDateFormat format = new SimpleDateFormat(formato);
        format.setLenient(false);
        java.sql.Date date;
        try {
            if (data == null) {
                return null;
            }
            date = new java.sql.Date((format.parse(data)).getTime());
        } catch (ParseException ex) {
            return null;
        }
        return date;
    }

    public static Date dataSqlParaUtil(java.sql.Timestamp dataSql) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (dataSql == null) {
            return null;
        }
        try {
            date = (Date) df.parse(dataSql.toString());
        } catch (ParseException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    public static Integer nuloParaZero(Integer numero) {
        if (numero == null) {
            numero = 0;
        }
        return numero;
    }

    public static Double nuloParaZero(Double numero) {
        if (numero == null) {
            numero = 0.0;
        }
        return numero;
    }

    public static BigDecimal nuloParaZero(BigDecimal numero) {
        if (numero == null) {
            numero = new BigDecimal("0");
        }
        return numero;
    }

    public static java.sql.Date stringParaDataSql(String data, String formato) {
        try {
            SimpleDateFormat formatacao = new SimpleDateFormat(formato);
            java.util.Date dataFormatada;
            java.sql.Date dataSql = null;
            try {
                dataFormatada = formatacao.parse(data);
                dataSql = new java.sql.Date(dataFormatada.getTime());
            } catch (ParseException ex) {
                Logs.gerarLog(Logs.TITULO_ERRO, "Erro na conversão da data: " + data, Logs.COLOR_ERRO);
            }
            return dataSql;
        } catch (Exception error) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro na conversão da data: " + error, Logs.COLOR_ERRO);
            return null;
        }
    }

    public static Integer converterZeroParaNulo(Integer categoriaPaiHub) {
        if (categoriaPaiHub == 0) {
            return null;
        } else {
            return categoriaPaiHub;
        }
    }

    public static String converterZeroParaNuloString(Integer categoriaPaiHub) {
        if (categoriaPaiHub == 0) {
            return null;
        } else {
            return categoriaPaiHub.toString();
        }
    }

    public static String converterDataParaInstant(Date data) {
        if (data != null) {
            return data.toInstant().toString();
        }
        return "";
    }

}
