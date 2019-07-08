package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.persistence.dao.additional.MessageDaoAdditionalMethods;
import com.vpd.courseproject.forum.persistence.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMessageDao extends CrudRepository<Message, Long>, MessageDaoAdditionalMethods {

    long countMessagesByTopicId(long id);

    long countMessagesByTopicSectionId(long sectionId);

    long countMessagesByUserLogin(String login);

    @Transactional
    List<Message> findMessagesByUserLogin(String login);

    @Transactional
    List<Message> findMessagesByTopicId(long id);

}
