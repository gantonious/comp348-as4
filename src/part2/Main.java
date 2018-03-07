package part2;

import part1.EmailDescriptor;
import part1.EmailParser;
import email.IEmailSender;
import email.JavaMailSender;

/**
 * title: Main.java
 * description: The main entry for the SendEmail with attachment program.
 * date: March 6, 2018
 * @author George Antonious
 * @version 1.0
 * @copyright 2018 George Antonious
 *
 * I declare that this assignment is my own work and that all material
 * previously written or published in any source by any other person
 * has been duly acknowledged in the assignment. I have not submitted
 * this work, or a significant part thereof, previously as part of any
 * academic program. In submitting this assignment I give permission to
 * copy it for assessment purposes only.
 *
 * The usage, design, and test plan for this part can be found in the
 * README.md file in the root of this project. It is recommended to view
 * it in a markdown reader.
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
