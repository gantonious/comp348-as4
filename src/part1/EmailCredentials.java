package part1;

/**
 * Created by George on 2018-01-03.
 */
public class EmailCredentials {
    private String username;
    private String password;
    private String mailServer;

    public EmailCredentials username(String username) {
        this.username = username;
        return this;
    }

    public EmailCredentials password(String password) {
        this.password = password;
        return this;
    }

    public EmailCredentials mailServer(String mailServer) {
        this.mailServer = mailServer;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMailServer() {
        return mailServer;
    }
}
