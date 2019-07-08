package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.persistence.entity.PrivateMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPrivateMessageDao extends CrudRepository<PrivateMessage, Long> {

    @Transactional
    List<PrivateMessage> findAllByToLoginAndDeletedToIsFalse(String login);

    @Transactional
    List<PrivateMessage> findAllByFromLoginAndDeletedFromIsFalse(String login);

    long countByToLoginAndReadIsFalseAndDeletedToFalse(String login);
}
