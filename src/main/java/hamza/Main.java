package hamza;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

    private static String USER_NAME = "egeneration.sante@gmail.com"; // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "jamiroquai(-3)"; // GMail password
//    private static String RECIPIENT = "nassas.hamza@gmail.com";
    private static String RECIPIENT = "jean.baptiste.lasselle@gmail.com";
    

    public static void main(String[] args) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Test de l'envoi de mail depuis une machine hôte";
        String body = "Envoi effectué depuis la machine: " + getHostInfos();

        sendFromGMail(from, pass, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    
    private static String getHostInfos() {
    	String retour = "Résolution réseau impossible: la machine Virtuelle Java (JRE) n'a pu déterminer ni le nom d'hôte, ni l'adresse IP de la machine hôte.";
        InetAddress ip = null;
        String hostname = null;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
            retour = " [ADRESSE_IP=" + ip + "]" + " [HOSTNAME=" + hostname + "]";
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }
        
        return retour ;
    }
}