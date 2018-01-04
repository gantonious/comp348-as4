package email;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

/**
 * Created by George on 2018-01-03.
 */
public class JavaMailSender implements IEmailSender {
    private Session mailSession;

    public JavaMailSender(EmailCredentials emailCredentials) {
        Properties sessionProperties = getSessionPropertiesFrom(emailCredentials);
        Authenticator sessionAuthenticator = getAuthenticatorFrom(emailCredentials);
        mailSession = Session.getInstance(sessionProperties, sessionAuthenticator);
    }

    private Properties getSessionPropertiesFrom(EmailCredentials emailCredentials) {
        Properties sessionProperties = new Properties();
        sessionProperties.put("mail.smtp.host", emailCredentials.getMailServer());
        sessionProperties.put("mail.smtp.auth", "true");
        sessionProperties.put("mail.smtp.port", "587");
        sessionProperties.put("mail.smtp.starttls.enable", "true");
        return sessionProperties;
    }

    private Authenticator getAuthenticatorFrom(EmailCredentials emailCredentials) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailCredentials.getUsername(), emailCredentials.getPassword());
            }
        };
    }

    @Override
    public void send(Email email) {
        try {
            tryToSend(email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void tryToSend(Email email) throws Exception {
        Message mailMessage = new MimeMessage(mailSession);
        mailMessage.setFrom(new InternetAddress(email.getFromAddress()));
        mailMessage.setRecipients(Message.RecipientType.TO, convertToAddresses(email.getToAddresses()));
        mailMessage.setRecipients(Message.RecipientType.CC, convertToAddresses(email.getCcAddresses()));
        mailMessage.setRecipients(Message.RecipientType.BCC, convertToAddresses(email.getBccAddresses()));
        mailMessage.setSubject(email.getSubject());

        Multipart multipart = new MimeMultipart();

        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(email.getBody());
        multipart.addBodyPart(bodyPart);

        for (String filename : email.getAttachments()) {
            BodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setFileName(filename);
            attachmentBodyPart.setDataHandler(new DataHandler(new FileDataSource(filename)));
            multipart.addBodyPart(attachmentBodyPart);
        }

        mailMessage.setContent(multipart);
        Transport.send(mailMessage);
    }

    private Address[] convertToAddresses(List<String> rawAddresses) throws Exception {
        Address[] addresses = new Address[rawAddresses.size()];

        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = new InternetAddress(rawAddresses.get(i));
        }

        return addresses;
    }
}
