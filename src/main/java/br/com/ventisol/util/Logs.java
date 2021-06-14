package br.com.ventisol.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logs {

    public static final String COLOR_AVISO = "rgb(253,218,0)";
    public static final String COLOR_SUCESSO = "green";
    public static final String COLOR_ERRO = "red";

    public static final String TITULO_AVISO = "Aviso!";
    public static final String TITULO_SUCESSO = "Sucesso!";
    public static final String TITULO_ERRO = "Erro!";

    public static void inicializarIntegracao() {
        verificarSeTodosOsTitulosForamFechados();
        StringBuilder log = new StringBuilder();
        log.append("\n<details>")
                .append("<summary>")
                .append("Integração do Horário: ")
                .append(buscarHora())
                .append("</summary>");
        adicionarLog(log.toString());
    }

    public static void verificarSeTodosOsTitulosForamFechados()  {
        try {
            File arquivo = Arquivo.buscarArquivo();
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String line;
            int quantidadeDeTituloIniciados = 0;
            int quantidadeDeTituloFinalizados = 0;
            try {
                while ((line = reader.readLine()) != null) {
                    if(line.contains("<details>")){
                        quantidadeDeTituloIniciados += 1;
                    }else if(line.contains("</details>")){
                        quantidadeDeTituloFinalizados += 1;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Logs.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(quantidadeDeTituloIniciados > quantidadeDeTituloFinalizados){
                adicionarLog("</details>");
                quantidadeDeTituloFinalizados += 1;
            }
            System.out.println(quantidadeDeTituloIniciados);
            System.out.println(quantidadeDeTituloFinalizados);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void finalizarIntegracao() {
        StringBuilder log = new StringBuilder();
        log.append("</details>");
        adicionarLog(log.toString());
    }

    public static void inicializarLog(String titulo, String mensagem) {
        StringBuilder log = new StringBuilder();
        log.append("\n<br/>")
                .append(buscarHora())
                .append(" : ")
                .append("========================== ")
                .append(titulo)
                .append(" ==========================")
                .append(mensagem);
        adicionarLog(log.toString());
    }

    public static void gerarLog(String titulo, String conteudo, String color) {
        if(titulo.equals(TITULO_ERRO)){
//            Util.enviarEmail("projeto@ventisol.com.br", "Erro na Inetgração do Hub2B", conteudo);
        }
        StringBuilder log = new StringBuilder();
        log.append("\n<br/>")
                .append(buscarHora())
                .append(" : ")
                .append("<a style=\"color:")
                .append(color)
                .append("\"># ")
                .append(titulo)
                .append(" # ")
                .append(conteudo)
                .append("</a>");
        adicionarLog(log.toString());
    }

    public static void finalizandoLog(String titulo) {
        StringBuilder log = new StringBuilder();
        log.append("\n<br/>")
                .append(buscarHora())
                .append(" : ")
                .append("========================== ")
                .append(titulo)
                .append(" ==========================");
        adicionarLog(log.toString());
    }

    private static String buscarHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        String dataFormatada = sdf.format(hora);
        return dataFormatada;
    }

    public static void adicionarLog(String text) {
        Arquivo.salvarNoArquivo(text);
    }
}
