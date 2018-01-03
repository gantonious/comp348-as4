package part1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by George on 2018-01-01.
 */
public class Email {
    private String fromAddress;
    private String subject;
    private String body;
    private List<String> toAddresses;
    private List<String> ccAddresses;
    private List<String> bccAddresses;

    public Email() {
        toAddresses = new ArrayList<>();
        ccAddresses = new ArrayList<>();
        bccAddresses = new ArrayList<>();
    }

    public Email from(String address) {
        fromAddress = address;
        return this;
    }

    public Email to(String... addresses) {
        toAddresses.addAll(Arrays.asList(addresses));
        return this;
    }

    public Email cc(String... addresses) {
        ccAddresses.addAll(Arrays.asList(addresses));
        return this;
    }

    public Email bcc(String... addresses) {
        bccAddresses.addAll(Arrays.asList(addresses));
        return this;
    }

    public Email subject(String subject) {
        this.subject = subject;
        return this;
    }

    public Email body(String body) {
        this.body = body;
        return this;
    }

    public Email attach(File file) {
        return this;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public List<String> getToAddresses() {
        return toAddresses;
    }

    public List<String> getCcAddresses() {
        return ccAddresses;
    }

    public List<String> getBccAddresses() {
        return bccAddresses;
    }

    public void sendUsing(IEmailClient emailClient) {
        emailClient.send(this);
    }
}
