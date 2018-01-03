package part1;

/**
 * Created by George on 2018-01-03.
 */
public class EmailDescriptor {
    private EmailCredentials emailCredentials;
    private Email email;

    public EmailDescriptor(EmailCredentials emailCredentials, Email email) {
        this.emailCredentials = emailCredentials;
        this.email = email;
    }

    public EmailCredentials getEmailCredentials() {
        return emailCredentials;
    }

    public Email getEmail() {
        return email;
    }
}
