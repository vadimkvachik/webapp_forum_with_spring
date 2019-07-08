package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exceptions.SendEmailException;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IMailService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

@Service
public class MailService implements IMailService {
    private Logger logger = Logger.getLogger(MailService.class);
    private Properties properties = new Properties();

    public void sendMailForPasswordRecovery(User user, String lang) throws SendEmailException {
        try {
            properties.load(Objects.requireNonNull(MailService.class.getClassLoader().getResourceAsStream("mail.properties")));
            String name;
            String subject;
            String text;
            if (lang.equals("en")) {
                name = properties.getProperty("ENG_MAIL_NAME");
                subject = properties.getProperty("ENG_SUBJECT");
                text = String.format(properties.getProperty("ENG_TEXT"), user.getName(), user.getLogin(), user.getPassword());
            } else {
                name = properties.getProperty("RUS_MAIL_NAME");
                subject = properties.getProperty("RUS_SUBJECT");
                text = String.format(properties.getProperty("RUS_TEXT"), user.getName(), user.getLogin(), user.getPassword());
            }

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(properties.getProperty("mail.from"),
                                    properties.getProperty("mail.smtp.password"));
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.from"), name));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            logger.info("User '" + user.getLogin() + "' recovered password by email");
        } catch (MessagingException | IOException e) {
            throw new SendEmailException(e.getMessage());
        }
    }

}
