package br.com.ventisol.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arquivo {

    public static void salvarNoArquivo(String conteudo) {
        gravarArquivo(buscarArquivo(), conteudo);
    }

    public static File buscarArquivo() {
        File diretorio = new File(System.getProperty("user.home") + "\\HUB2B");
        diretorio.mkdir();
        LocalDate hoje = LocalDate.now();
        File file = new File(System.getProperty("user.home") + "\\HUB2B\\" + hoje.toString() + ".html");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return file;
    }

    private static void gravarArquivo(File arquivo, String conteudo) {
        try {
            FileOutputStream os = new FileOutputStream(arquivo, true);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.append(conteudo);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
