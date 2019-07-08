package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.dto.ArticleDto;
import com.vpd.courseproject.forum.persistence.entity.Article;
import com.vpd.courseproject.forum.persistence.entity.User;

import java.util.List;

public interface IArticleService {

    void addArticle(User user, String topic, String text);

    Article getArticle(long id);

    List<ArticleDto> getArticles(String login);

    void deleteArticle(String login, long id);

    long getNumberOfArticlesByLogin(String login);
}
