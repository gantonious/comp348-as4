package email;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by George on 2018-01-04.
 */
public class JavaMailReader implements IEmailReader {
    private Session mailSession;
    private EmailCredentials emailCredentials;

    public JavaMailReader(EmailCredentials emailCredentials) {
        Properties sessionProperties = getSessionPropertiesFrom(emailCredentials);
        Authenticator sessionAuthenticator = getAuthenticatorFrom(emailCredentials);

        this.emailCredentials = emailCredentials;
        this.mailSession = Session.getInstance(sessionProperties, sessionAuthenticator);
    }

    private Properties getSessionPropertiesFrom(EmailCredentials emailCredentials) {
        Properties sessionProperties = new Properties();
        sessionProperties.put("mail.pop3.host", emailCredentials.getMailServer());
        sessionProperties.put("mail.pop3.port", "995");
        sessionProperties.put("mail.pop3.starttls.enable", "true");
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
    public List<Email> getEmailsFrom(String mailbox) {
        try {
            return tryToGetEmailsFrom(mailbox);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Email> tryToGetEmailsFrom(String mailbox) throws Exception {
        Store store = mailSession.getStore("pop3s");

        store.connect(emailCredentials.getMailServer(),
                emailCredentials.getUsername(),
                emailCredentials.getPassword());

        Folder emailFolder = store.getFolder(mailbox);
        Message[] messages = emailFolder.getMessages();
        emailFolder.close();

        return convertToEmails(messages);
    }

    private List<Email> convertToEmails(Message[] messages) throws Exception {
        List<Email> emails = new ArrayList<>();

        for (Message message : messages) {
            Email email = new Email()
                    .from(convertAddressesToString(message.getFrom())[0])
                    .to(convertAddressesToString(message.getRecipients(Message.RecipientType.TO)))
                    .cc(convertAddressesToString(message.getRecipients(Message.RecipientType.CC)))
                    .cc(convertAddressesToString(message.getRecipients(Message.RecipientType.BCC)))
                    .subject(message.getSubject())
                    .body(message.getContent().toString());

            emails.add(email);
        }

        return emails;
    }

    private String[] convertAddressesToString(Address[] addresses) {
        return (String[]) Arrays.stream(addresses)
                .map(Address::toString)
                .toArray();
    }
}
