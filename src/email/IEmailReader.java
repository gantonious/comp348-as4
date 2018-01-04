package email;

import java.util.List;

/**
 * Created by George on 2018-01-04.
 */
public interface IEmailReader {
    List<Email> getEmailsFrom(String mailbox);
}
