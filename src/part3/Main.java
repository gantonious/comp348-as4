package part3;

import email.*;

import java.io.PrintStream;
import java.util.List;

/**
 * title: Main.java
 * description: The main entry for the GetMail program.
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
        EmailCredentials emailCredentials = new EmailCredentials()
                .mailServer(getMailServerFrom(args))
                .username(getUsernameFrom(args))
                .password(getPasswordFrom(args));

        IEmailReader emailReader = new JavaMailReader(emailCredentials);

        if (isInSelectionMode(args)) {
            runShowEmailDetails(getEmailSelectionFrom(args), emailReader);
        } else {
            runShowEmails(emailReader);
        }
    }

    private static void runShowEmailDetails(int selection, IEmailReader emailReader) {
        List<Email> emails = emailReader.getEmailsFrom("Inbox");

        if (selection < 1 || selection > emails.size()) {
            throw new RuntimeException("You selected an invalid email.");
        }

        Email email = emails.get(selection - 1);

        PrintStream printWriter = System.out;

        printWriter.println("From: " + email.getFromAddress());
        printWriter.println("To: " + StringUtils.join(email.getToAddresses(), ", "));
        if (!email.getCcAddresses().isEmpty()) {
            printWriter.println("Cc: " + StringUtils.join(email.getCcAddresses(), ", "));
        }
        printWriter.println("Subject: " + email.getSubject());
        printWriter.println("------------");
        printWriter.println(email.getBody());
        printWriter.println("------------");
    }

    private static void runShowEmails(IEmailReader emailReader) {
        List<Email> emails = emailReader.getEmailsFrom("Inbox");
        PrintStream printWriter = System.out;

        for (int i = 0; i < emails.size(); i++) {
            Email email = emails.get(i);
            String line = String.format("%d. %s (%s)", i + 1, email.getSubject(), email.getFromAddress());
            printWriter.println(line);
        }

        if (emails.isEmpty()) {
            printWriter.println("You have no new emails!");
        }
    }

    private static boolean isInSelectionMode(String[] args) {
        return args.length == 4;
    }

    private static String getMailServerFrom(String[] args) {
        try {
            return args[0];
        } catch (Exception e) {
            throw new RuntimeException("The mail server should be specified as the first argument.");
        }
    }

    private static String getUsernameFrom(String[] args) {
        try {
            return args[1];
        } catch (Exception e) {
            throw new RuntimeException("The username should be specified as the second argument.");
        }
    }

    private static String getPasswordFrom(String[] args) {
        try {
            return args[2];
        } catch (Exception e) {
            throw new RuntimeException("The password should be specified as the thirds argument.");
        }
    }

    private static int getEmailSelectionFrom(String[] args) {
        try {
            return Integer.parseInt(args[3]);
        } catch (Exception e) {
            throw new RuntimeException("The selection should be specified as an integer.");
        }
    }
}
