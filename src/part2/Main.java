package part2;

import part1.EmailDescriptor;
import part1.EmailParser;
import email.IEmailSender;
import email.JavaMailSender;

/**
 * Created by George on 2018-01-03.
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

        IEmailSender mailSender = new JavaMailSender(emailDescriptor.getEmailCredentials());

        emailDescriptor
                .getEmail()
                .attach("./src/part2/banff.jpg")
                .sendUsing(mailSender);
    }

    private static String getEmailDescriptorFileNameFrom(String[] args) {
        try {
            return args[0];
        } catch (Exception e) {
            throw new RuntimeException("The filename for the email descriptor should be specified");
        }
    }
}
