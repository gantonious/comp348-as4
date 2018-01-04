package part3;

import email.*;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by George on 2018-01-04.
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
            throw new RuntimeException("Bad selection");
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
