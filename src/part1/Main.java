package part1;

import email.IEmailSender;
import email.JavaMailSender;

/**
 * Created by George on 2018-01-01.
 */
public class Main {

    public static void main(String[] args) {
        try {
            runProgram(args);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void runProgram(String[] args) {
        EmailParser emailParser = new EmailParser();

        String emailDescriptorFileName = getEmailDescriptorFileNameFrom(args);
        EmailDescriptor emailDescriptor = emailParser.parseEmailFrom(emailDescriptorFileName);

        IEmailSender emailSender = new JavaMailSender(emailDescriptor.getEmailCredentials());
        emailDescriptor.getEmail().sendUsing(emailSender);
    }

    private static String getEmailDescriptorFileNameFrom(String[] args) {
        try {
            return args[0];
        } catch (Exception e) {
            throw new RuntimeException("The filename for the email descriptor should be specified");
        }
    }
}
