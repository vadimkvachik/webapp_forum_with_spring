package com.vpd.courseproject.forum.persistence.dao;


import com.vpd.courseproject.forum.persistence.entity.Section;
import org.springframework.data.repository.CrudRepository;

public interface ISectionDao extends CrudRepository<Section, Long> {
}
