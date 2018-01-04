package email;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.MimeMultipart;

/**
 * Created by George on 2018-01-04.
 */
public class MessageUtils {
    public static String getBodyTextFrom(Message message) {
        try {
            if (message.isMimeType("text/plain")) {
                return message.getContent().toString();
            } else if (message.isMimeType("multipart/*")) {
                return getBodyTextFrom((MimeMultipart) message.getContent());
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getBodyTextFrom(MimeMultipart mimeMultipart) throws Exception {
        String body = "";

        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/*")) {
                body += bodyPart.getContent().toString() + "\n";
            }
        }

        return body.trim();
    }
}
