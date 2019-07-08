package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.persistence.dao.IPrivateMessageDao;
import com.vpd.courseproject.forum.persistence.dao.IUserDao;
import com.vpd.courseproject.forum.persistence.entity.PrivateMessage;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IPrivateMessageService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PrivateMessageService implements IPrivateMessageService {
    private static final Logger logger = Logger.getLogger(PrivateMessageService.class);
    private IPrivateMessageDao privateMessageDao;
    private IUserDao userDao;

    public PrivateMessageService(IPrivateMessageDao privateMessageDao, IUserDao userDao) {
        this.privateMessageDao = privateMessageDao;
        this.userDao = userDao;
    }

    public void addPrivateMessage(String topic, String text, String senderLogin, String recipientLogin) {
        PrivateMessage message = new PrivateMessage();
        message.setTopic(topic);
        message.setText(text);
        message.setFrom(userDao.findById(senderLogin).orElse(null));
        message.setTo(userDao.findById(recipientLogin).orElse(null));
        privateMessageDao.save(message);
        logger.info("User '" + senderLogin + "' sent private message to user '" + recipientLogin + "'");
    }

    public PrivateMessage getPrivateMessage(User user, long id) {
        PrivateMessage message = privateMessageDao.findById(id).orElse(null);
        assert message != null;
        if (message.getTo().equals(user) || message.getFrom().equals(user)) {
            if (user.equals(message.getTo())) {
                message.setRead(true);
                privateMessageDao.save(message);
            }
            return message;
        } else {
            throw new NumberFormatException();
        }
    }

    public List<PrivateMessage> getPrivateMessagesByRecipient(String login) {
            List<PrivateMessage> messages = privateMessageDao.findAllByToLoginAndDeletedToIsFalse(login);
            Collections.sort(messages);
            return messages;
    }

    public List<PrivateMessage> getPrivateMessagesBySender(String login) {
            List<PrivateMessage> messages = privateMessageDao.findAllByFromLoginAndDeletedFromIsFalse(login);
            Collections.sort(messages);
            return messages;
    }

    public String deletePrivateMessageAndReturnPath(User user, long id, String activity) {
            PrivateMessage message = privateMessageDao.findById(id).orElse(null);
                assert message != null;
            if (activity.equals("sender")) {
                if (!message.isRead() || message.isDeletedTo()) {
                    privateMessageDao.delete(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' from DB");
                } else {
                    message.setDeletedFrom(true);
                    privateMessageDao.save(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' for yourself");
                }
                return "/private_out";
            } else if (activity.equals("recipient")) {
                if (message.isDeletedFrom()) {
                    privateMessageDao.delete(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' from DB");
                } else {
                    message.setDeletedTo(true);
                    privateMessageDao.save(message);
                    logger.info("User '" + user.getLogin() + "' deleted private message '" + message.getTopic() + "' for yourself");
                }
                return "/private_in";
            }
            return "/";
    }
}
