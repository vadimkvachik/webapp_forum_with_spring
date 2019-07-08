package com.vpd.courseproject.forum.persistence.dao.additional.impl;

import com.vpd.courseproject.forum.persistence.dao.additional.MessageDaoAdditionalMethods;
import com.vpd.courseproject.forum.persistence.entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

public class MessageDaoAdditionalMethodsImpl implements MessageDaoAdditionalMethods {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Message> findLastMessageByTopicId(long topicId) {
        Date dateOfLastMessage = em.createQuery("SELECT MAX(dateOfPublication)FROM Message a " +
                "WHERE a.topic.id=:topicId", Date.class).setParameter("topicId", topicId).getSingleResult();
        return em.createQuery("SELECT a FROM Message a WHERE a.dateOfPublication=:date", Message.class)
                .setParameter("date", dateOfLastMessage).getResultList();
    }

    @Override
    public List<Message> findLastMessageBySectionId(long sectionId) {
        Date dateOfLastMessage = em.createQuery("SELECT MAX(dateOfPublication)FROM Message a " +
                "WHERE a.topic.section.id=:sectionId", Date.class).setParameter("sectionId", sectionId).getSingleResult();
        return   em.createQuery("SELECT a FROM Message a WHERE a.dateOfPublication=:date", Message.class)
                .setParameter("date", dateOfLastMessage).getResultList();
    }
}
