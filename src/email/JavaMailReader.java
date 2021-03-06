package email;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.util.ArrayList;
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
        sessionProperties.put("mail.store.protocol", "imaps");
        sessionProperties.put("mail.imaps.host", emailCredentials.getMailServer());
        sessionProperties.put("mail.imaps.port", "993");
        return sessionProperties;
    }

    private Authenticator getAuthenticatorFrom(final EmailCredentials emailCredentials) {
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
        Store store = mailSession.getStore("imaps");

        store.connect(emailCredentials.getMailServer(),
                emailCredentials.getUsername(),
                emailCredentials.getPassword());

        Folder emailFolder = store.getFolder(mailbox);
        emailFolder.open(Folder.READ_ONLY);
        Message[] messages = emailFolder.search(buildUnreadEmailsSearchTerm());

        List<Email> emails = convertToEmails(messages);
        emailFolder.close();

        return emails;
    }

    private SearchTerm buildUnreadEmailsSearchTerm() {
        return new FlagTerm(new Flags(Flags.Flag.SEEN), false);
    }

    private List<Email> convertToEmails(Message[] messages) throws Exception {
        List<Email> emails = new ArrayList<>();

        for (Message message : messages) {
            Email email = new Email()
                    .from(convertAddressesToString(message.getFrom())[0])
                    .to(convertAddressesToString(message.getRecipients(Message.RecipientType.TO)))
                    .cc(convertAddressesToString(message.getRecipients(Message.RecipientType.CC)))
                    .subject(message.getSubject())
                    .body(MessageUtils.getBodyTextFrom(message));

            emails.add(email);
        }

        return emails;
    }

    private String[] convertAddressesToString(Address[] addresses) {
        if (addresses == null) {
            return new String[0];
        }

        String[] rawAddresses = new String[addresses.length];

        for (int i = 0; i < addresses.length; i++) {
            rawAddresses[i] = addresses[i].toString();
        }

        return rawAddresses;
    }
}
