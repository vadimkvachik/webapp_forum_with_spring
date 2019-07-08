package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.persistence.dao.IArticleDao;
import com.vpd.courseproject.forum.persistence.dto.ArticleDto;
import com.vpd.courseproject.forum.persistence.entity.Article;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IArticleService;
import com.vpd.courseproject.forum.utils.api.IFormatter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService implements IArticleService {
    private static final Logger logger = Logger.getLogger(ArticleService.class);
    private IArticleDao articleDao;
    private IFormatter formatter;

    public ArticleService(IArticleDao articleDao, IFormatter formatter) {
        this.articleDao = articleDao;
        this.formatter = formatter;
    }

    public void addArticle(User user, String topic, String text) {
        Article article = new Article(topic, text, user);
        articleDao.save(article);
        logger.info("User '" + user.getLogin() + "' added the article '" + topic + "'");
    }

    public Article getArticle(long id) {
        return articleDao.findById(id).orElse(null);
    }

    public List<ArticleDto> getArticles(String login) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        List<Article> articles;
        if (login == null) articles = (List<Article>) articleDao.findAll();
        else articles = articleDao.findArticlesByUserLogin(login);
        for (Article article : articles) {
            ArticleDto articleDto = new ArticleDto();
            articleDto.setArticle(article);
            articleDto.setTextPreview(formatter.formatTextForPreview(article.getText(), 800));
            articleDtoList.add(articleDto);
        }
        Collections.sort(articleDtoList);
        return articleDtoList;

    }

    public void deleteArticle(String login, long id) {
        Optional<Article> article = articleDao.findById(id);
        if (article.isPresent()) {
            articleDao.delete(article.get());
            logger.info("User '" + login + "' deleted the article '" + article.get().getTopic() + "'");
        }
    }

    public long getNumberOfArticlesByLogin(String login) {
        return articleDao.countArticlesByUserLogin(login);
    }

}
