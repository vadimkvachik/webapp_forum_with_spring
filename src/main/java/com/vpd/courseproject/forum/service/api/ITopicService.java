package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.dto.TopicDto;
import com.vpd.courseproject.forum.persistence.entity.Topic;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface ITopicService {

    String addTopicAndReturnPath(User user, long sectionId, String name, String firstMessage);

    Topic getTopicById(long id);

    List<TopicDto> getTopics(long sectionId);

    String renameTopicAndReturnPath(String login, long id, String newName);

    String deleteTopicAndReturnPath(String login, long id);
}
