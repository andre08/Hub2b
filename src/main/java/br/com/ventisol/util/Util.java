package br.com.ventisol.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Util extends Object {

    private static final Session session = Session.getDefaultInstance(resgatarPropriedades(),
            new javax.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("retornohub2b@gmail.com", "projeto@123");
        }
    }
    );

    public static Properties resgatarPropriedades() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return props;
    }

    public static Object gsonParaClasse(Class<?> classe, String gson) {
        Object obj = null;
        try {
            Gson gsons = new Gson();
            obj = gsons.fromJson(gson, classe);
        } catch (JsonSyntaxException error) {
            Logs.gerarLog(Logs.TITULO_ERRO, "Erro ao gerar GSON! Classe: " + classe.toString() + " Gson:" + gson, Logs.COLOR_ERRO);
        }
        return obj;
    }

    public static String classeParaGson(Object gson) {
        Gson gsons = new Gson();
        String obj = gsons.toJson(gson, gson.getClass());
        return obj;
    }

    public static void enviarEmail(String email, String titulo, String mensagem) {
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect("retornohub2b@gmail.com", "ventisol@123");
            Message message = new MimeMessage(session);
            Address[] toUser = InternetAddress.parse(email);
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setText(mensagem);
            message.setSubject("Erro na Integração do Hub2B");
            transport.sendMessage(message, message.getAllRecipients());
//            Transport transport = session.getTransport("smtp");
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("retornohub2b@gmail.com"));
//            Address[] toUser = InternetAddress.parse(email);
//
//            message.setRecipients(Message.RecipientType.TO, toUser);
//            message.setSubject("Erro na Integração do Hub2B");//Assunto / Título -- Fixado
//            /**
//             * Método para enviar a mensagem criada
//             */
//            transport.send(message);
//            transport.close();

        } catch (MessagingException e) {
            System.out.println("Erro ao enviar Email: Muitas tenativas");
        }

    }
}
