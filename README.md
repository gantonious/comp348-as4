# COMP 348 Assignment 4

I declare that this assignment is my own work and that all material previously written or published in any source by any other person has been duly acknowledged in the assignment. I have not submitted this work, or a significant part thereof, previously as part of any academic program. In submitting this assignment I give permission to copy it for assessment purposes only.

Submitted by: George Antonious (3364768)

## Building Instructions

Assuming JDK 1.8 is installed on the host machine, both parts can be built by doing:

```bash
make
```

## Additional Documentation

Reflections on the project and final design decisions made can be found in `docs/reflection.pdf`. Research made on the JavaMail API and its findings can be found in `docs/research.pdf`.

## Part 1: Email Sender

### Usage

To send an email do:

```bash
make runSendEmail EMAIL_FILE=[path_to_email_file]
```

### Test Plan

To test this part an email file was crafted so that each field was utilized such as the TO, CC, and BCC fields. Then all the specifeid client email boxes were checked and were checked that the visibility of the specified recipients were correct. Tests were also made for the case that the email file leaves some fields blank such as CC. When testing `smtp.gmail.com` was used as the mail server.

## Part 2: Email Sender with Attachment

### Usage

To send an email with an attachment do (this sends `banff.jpg` in the root project directory as the attachment):

```bash
make runSendEmailWithAttachment EMAIL_FILE=[path_to_email_file]
```

## Test Plan

This part extends the existing functionality of part 1 and only attaches a predefined attachment to the email. To test this part sufficiently all that I did was send an email and ensure the recipient received the attached attachment as expected. When testing `smtp.gmail.com` was used as the mail server.

## Part 3: Email Reader

### Usage

To view all of your unread emails do:

```bash
make runEmailReader MAIL_SERVER=[address_of_mail_sever] EMAIL_ADDRESS=[account_address] PASSWORD=[account_password]
```

To view a specific email rerun the program with the additional `EMAIL_SELECTION` parameter as shown below:

```bash
make runEmailReader MAIL_SERVER=[address_of_mail_sever] EMAIL_ADDRESS=[account_address] PASSWORD=[account_password] EMAIL_SELECTION=[selected_email]
```

### Test Plan

Four different cases were tested for this part. These tests used `imap.gmail.com` as the mail server:
- Checking for emails when a user has no unread emails
- Checking for emails when the user does have unread emails
- Selecting a valid email from the list
- Selecting an email that doesn't exist within the list