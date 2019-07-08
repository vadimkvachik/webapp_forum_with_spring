package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.entity.PrivateMessage;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IPrivateMessageService {

    void addPrivateMessage(String topic, String text, String senderLogin, String recipientLogin);

    PrivateMessage getPrivateMessage(User user, long id);

    List<PrivateMessage> getPrivateMessagesByRecipient(String login);

    List<PrivateMessage> getPrivateMessagesBySender(String login);

    String deletePrivateMessageAndReturnPath(User user, long id, String activity);
}
