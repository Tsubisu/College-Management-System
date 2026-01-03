package College_Management_System;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

        public static void sendEmail(String toEmail, String subject, String body) {

            // ====== 1. GMAIL SMTP SETTINGS ======

            final String fromEmail = "snewgr@gmail.com"; // your email
            final String appPassword = "elnqnbrhsixfemxj"; // Gmail App Password ONLY

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");   // TLS
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // ====== 2. AUTHENTICATION ======

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, appPassword);
                }
            });

            try {

                // ====== 3. MESSAGE CREATION ======
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                message.setSubject(subject);
                message.setText(body);

                // ====== 4. SEND EMAIL ======
                Transport.send(message);
                System.out.println("Email Sent Successfully!");

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
            finally {
                System.out.print("Sending message");
            }
        }
}

