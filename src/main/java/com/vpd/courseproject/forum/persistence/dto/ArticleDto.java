package com.vpd.courseproject.forum.persistence.dto;

import com.vpd.courseproject.forum.persistence.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleDto implements Comparable<ArticleDto> {
    private Article article;
    private String textPreview;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getTextPreview() {
        return textPreview;
    }

    public void setTextPreview(String textPreview) {
        this.textPreview = textPreview;
    }

    @Override
    public int compareTo(ArticleDto o) {
        return (int) (o.article.getId() - article.getId());
    }
}
