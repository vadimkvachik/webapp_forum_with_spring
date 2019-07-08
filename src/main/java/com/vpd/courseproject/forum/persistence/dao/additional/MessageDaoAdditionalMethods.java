package com.vpd.courseproject.forum.persistence.dao.additional;

import com.vpd.courseproject.forum.persistence.entity.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageDaoAdditionalMethods {

    @Transactional
    List<Message>  findLastMessageByTopicId(long topicId);

    @Transactional
    List<Message> findLastMessageBySectionId(long sectionId);
}
