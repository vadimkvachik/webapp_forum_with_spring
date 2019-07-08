package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<User, String> {

    User findUserByEmail(String email);
}
