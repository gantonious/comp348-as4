package part1;

import email.Email;
import email.EmailCredentials;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 2018-01-01.
 */
public class EmailParser {

    public EmailDescriptor parseEmailFrom(String fileName) {
        try {
            return parseEmailFrom(new FileInputStream(fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EmailDescriptor parseEmailFrom(InputStream inputStream) {
        try (
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            return parseEmailFrom(bufferedReader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EmailDescriptor parseEmailFrom(BufferedReader bf) throws Exception {
        Map<String, String> emailFields = new HashMap<>();

        for (String nextLine = bf.readLine(); nextLine != null; nextLine = bf.readLine()) {
            String[] fieldTokens = nextLine.split(":");
            String field = fieldTokens[0].trim().toLowerCase();

            try {
                String value = fieldTokens[1].trim();
                emailFields.put(field, value);
            } catch (ArrayIndexOutOfBoundsException e) {
                emailFields.put(field, "");
            }

            if (field.equals("body")) {
                break;
            }
        }

        for (String nextLine = bf.readLine(); nextLine != null; nextLine = bf.readLine()) {
            emailFields.put("body", emailFields.get("body") + "\n" + nextLine);
        }

        EmailCredentials emailCredentials = new EmailCredentials()
                .username(emailFields.get("user"))
                .password(emailFields.get("password"))
                .mailServer(emailFields.get("server"));

        Email email = new Email()
                .from(emailFields.get("user"))
                .to(getCommaSeparatedValues(emailFields, "to"))
                .cc(getCommaSeparatedValues(emailFields, "cc"))
                .bcc(getCommaSeparatedValues(emailFields, "bcc"))
                .subject(emailFields.get("subject"))
                .body(emailFields.get("body"));

        return new EmailDescriptor(emailCredentials, email);
    }

    private String[] getCommaSeparatedValues(Map<String, String> emailFields, String field) {
        if (emailFields.get(field.toLowerCase()).contains(",")) {
            String[] splitValues = emailFields.get(field.toLowerCase()).split(",");
            String[] trimmedSplitValues = new String[splitValues.length];

            for (int i = 0; i < trimmedSplitValues.length; i++) {
                trimmedSplitValues[i] = splitValues[i].trim();
            }

            return trimmedSplitValues;
        }
        return new String[0];
    }
}
