package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.persistence.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITopicDao extends CrudRepository<Topic, Long> {

    List<Topic> findAllBySectionId(long sectionId);

    long countBySectionId(long sectionId);
}
