package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.persistence.dao.IMessageDao;
import com.vpd.courseproject.forum.persistence.dao.ITopicDao;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IMessageService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageService implements IMessageService {
    private static final Logger logger = Logger.getLogger(MessageService.class);
    private ITopicDao topicDao;
    private IMessageDao messageDao;

    public MessageService(ITopicDao topicDao, IMessageDao messageDao) {
        this.topicDao = topicDao;
        this.messageDao = messageDao;
    }

    public String addMessageAndReturnPath(User user, long topicId, String text) {
        Topic topic = topicDao.findById(topicId).orElse(null);
        assert topic != null;
        long page = messageDao.countMessagesByTopicId(topic.getId()) / 10 + 1;
        Message message = new Message(user, topic, text);
        messageDao.save(message);
        logger.info("User '" + user.getLogin() + "' wrote a message in the topic '" + topic.getName() + "'");
        return "/topic?id=" + topicId + "&page=" + page + "#id" + message.getId();
    }

    public String changeMessageAndReturnPath(String login, long messageId, String text, String page) {
        Message message = messageDao.findById(messageId).orElse(null);
        assert message != null;
        message.setText(text);
        messageDao.save(message);
        logger.info("User '" + login + "' changed a message in the topic " + message.getTopic().getName());
        return "/topic?id=" + message.getTopic().getId() + "&page=" + page + "#id" + message.getId();
    }

    public String deleteMessageAndReturnPath(String login, long messageId, int page, int numberOfMessages) {
        if (numberOfMessages == 1 && page > 1) {
            page--;
        }
        Message message = messageDao.findById(messageId).orElse(null);
        assert message != null;
        messageDao.delete(message);
        logger.info("User '" + login + "' deleted a message in the topic " + message.getTopic().getName());
        return "/topic?id=" + message.getTopic().getId() + "&page=" + page;

    }

    public List<Message> getMessagesByLogin(String login) {
        return messageDao.findMessagesByUserLogin(login);
    }

    public List<Message> getMessagesByTopicId(long topicId) {
        List<Message> messages = messageDao.findMessagesByTopicId(topicId);
        Collections.sort(messages);
        return messages;
    }

    public long getNumberOfMessagesByLogin(String login) {
        return messageDao.countMessagesByUserLogin(login);
    }

}
