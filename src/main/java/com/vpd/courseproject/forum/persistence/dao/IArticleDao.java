package com.vpd.courseproject.forum.persistence.dao;

import com.vpd.courseproject.forum.persistence.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IArticleDao extends CrudRepository<Article, Long> {

    long countArticlesByUserLogin(String login);

    @Transactional
    List<Article> findArticlesByUserLogin(String login);
}

