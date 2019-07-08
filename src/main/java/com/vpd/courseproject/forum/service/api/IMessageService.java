package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IMessageService {

    String addMessageAndReturnPath(User user, long topicId, String text);

    List<Message> getMessagesByLogin(String login);

    List<Message> getMessagesByTopicId(long topicId);

    String changeMessageAndReturnPath(String login, long messageId, String text, String page);

    String deleteMessageAndReturnPath(String login, long messageId, int page, int numberOfMessages);

    long getNumberOfMessagesByLogin(String login);
}
