package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.persistence.dao.IMessageDao;
import com.vpd.courseproject.forum.persistence.dao.ISectionDao;
import com.vpd.courseproject.forum.persistence.dao.ITopicDao;
import com.vpd.courseproject.forum.persistence.dto.TopicDto;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Section;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.ITopicService;
import com.vpd.courseproject.forum.utils.api.IFormatter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TopicService implements ITopicService {
    private static final Logger logger = Logger.getLogger(TopicService.class);
    private IFormatter formatter;
    private IMessageDao messageDao;
    private ITopicDao topicDao;
    private ISectionDao sectionDao;

    public TopicService(IFormatter formatter, IMessageDao messageDao, ITopicDao topicDao, ISectionDao sectionDao) {
        this.formatter = formatter;
        this.messageDao = messageDao;
        this.topicDao = topicDao;
        this.sectionDao = sectionDao;
    }

    public String addTopicAndReturnPath(User user, long sectionId, String name, String firstMessage) {
        Section section = sectionDao.findById(sectionId).orElse(null);
        Topic topic = new Topic(name, section);
        topicDao.save(topic);
        messageDao.save(new Message(user, topic, firstMessage));
        logger.info("User '" + user.getLogin() + "' added the topic '" + topic.getName()
                + "' in the section '" + topic.getSection().getName() + "'");
        return "/topic?id=" + topic.getId();
    }

    public Topic getTopicById(long id) {
        return topicDao.findById(id).orElse(null);
    }

    public List<TopicDto> getTopics(long sectionId) {
            List<TopicDto> topicDtoList = new ArrayList<>();
            for (Topic topic : topicDao.findAllBySectionId(sectionId)) {
                TopicDto topicDto = new TopicDto();
                topicDto.setTopic(topic);
                topicDto.setNumberOfMessages(messageDao.countMessagesByTopicId(topic.getId()));
                List<Message> lastMessageList = messageDao.findLastMessageByTopicId(topic.getId());
                if (lastMessageList.size() > 0) {
                    topicDto.setLastMessage(lastMessageList.get(0));
                    topicDto.setLastMessagePreview(formatter.formatTextForPreview(lastMessageList.get(0).getText(), 20));
                }
                topicDtoList.add(topicDto);
            }
            Collections.sort(topicDtoList);
            return topicDtoList;
    }

    public String renameTopicAndReturnPath(String login, long id, String newName) {
        Topic topic = topicDao.findById(id).orElse(null);
        assert topic != null;
        String oldName = topic.getName();
        topic.setName(newName);
        topicDao.save(topic);
        logger.info("User '" + login + "' renamed the topic '" + oldName + "' to '" + topic.getName() + "'");
        return "/section?id=" + topic.getSection().getId() + "#topic" + topic.getId();
    }

    public String deleteTopicAndReturnPath(String login, long id) {
        Topic topic = topicDao.findById(id).orElse(null);
        assert topic != null;
        topicDao.delete(topic);
        logger.info("User '" + login + "' deleted the topic '" + topic.getName() + "' in the section '" + topic.getSection().getName() + "'");
        return "/section?id=" + topic.getSection().getId();
    }
}
