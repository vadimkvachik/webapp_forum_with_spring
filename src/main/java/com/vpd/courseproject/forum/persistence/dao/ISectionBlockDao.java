package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.persistence.entity.SectionBlock;
import org.springframework.data.repository.CrudRepository;

public interface ISectionBlockDao extends CrudRepository<SectionBlock, Long> {
}