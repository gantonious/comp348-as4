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

## Part 2: Email Sender with Attachment

### Usage

To send an email with an attachment do (this sends `banff.jpg` in the root project directory as the attachment):

```bash
make runSendEmailWithAttachment EMAIL_FILE=[path_to_email_file]
```

## Part 3: Email Reader

### Usage

To view all of your unread emails do:

```bash
make runSendEmailWithAttachment MAIL_SERVER=[address_of_mail_sever] EMAIL_ADDRESS=[account_address] PASSWORD=[account_password]
```

To view a specific email rerun the program with the additional `EMAIL_SELECTION` parameter as shown below:

```bash
make runSendEmailWithAttachment MAIL_SERVER=[address_of_mail_sever] EMAIL_ADDRESS=[account_address] PASSWORD=[account_password] EMAIL_SELECTION=[selected_email]
```