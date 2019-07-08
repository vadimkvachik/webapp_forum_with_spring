package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {
    private IArticleService articleService;

    public ArticleController(IArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/add_article")
    public String add(HttpSession session, @RequestParam String topic, @RequestParam String text) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            articleService.addArticle(user, topic, text);
            return "redirect:/articles";
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/delete_article")
    public String delete(HttpSession session, @RequestParam long id) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            articleService.deleteArticle(user.getLogin(), id);
            return "redirect:/articles";
        } else {
            return "redirect:/index";
        }
    }

}
