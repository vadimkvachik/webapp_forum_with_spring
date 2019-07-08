package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class PagesViewController extends HttpServlet {
    private ISectionBlockService sectionBlockService;
    private ISectionService sectionService;
    private IUserService userService;
    private IArticleService articleService;
    private ITopicService topicService;
    private IMessageService messageService;
    private IPaginationService paginationService;
    private IPrivateMessageService privateMessageService;

    public PagesViewController(ISectionBlockService sectionBlockService, ISectionService sectionService, IUserService userService, IArticleService articleService, ITopicService topicService, IMessageService messageService, IPaginationService paginationService, IPrivateMessageService privateMessageService) {
        this.sectionBlockService = sectionBlockService;
        this.sectionService = sectionService;
        this.userService = userService;
        this.articleService = articleService;
        this.topicService = topicService;
        this.messageService = messageService;
        this.paginationService = paginationService;
        this.privateMessageService = privateMessageService;
    }

    @GetMapping({"/", "/index"})
    public String mainPage(Model model) {
        model.addAttribute("sectionBlockList", sectionBlockService.getAllSectionBlocks());
        model.addAttribute("sectionEntries", sectionService.getSectionEntries());
        return "main_page";
    }

    @GetMapping({"/users"})
    public String usersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users_page";
    }

    @GetMapping({"/articles"})
    public String articlesPage(Model model, @RequestParam(required = false) String login) {
        model.addAttribute("articles", articleService.getArticles(login));
        return "articles_page";
    }

    @GetMapping({"/article"})
    public String articlePage(Model model, @RequestParam long id) {
        model.addAttribute("article", articleService.getArticle(id));
        return "article_page";
    }

    @GetMapping({"/reg"})
    public String registrationPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "registration_page";
    }

    @GetMapping({"/section"})
    public String sectionPage(Model model, @RequestParam long id) {
        model.addAttribute("section", sectionService.getSection(id));
        model.addAttribute("topics", topicService.getTopics(id));
        return "section_page";
    }

    @GetMapping({"/topic"})
    public String topicPage(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam long id) {
        List<Message> messages = messageService.getMessagesByTopicId(id);
        model.addAttribute("topic", topicService.getTopicById(id));
        model.addAttribute("messages", paginationService.get10messagesForCurrentPage(page, messages));
        model.addAttribute("pages", paginationService.getPagesArray(messages.size()));
        model.addAttribute("currentPage", page);
        return "topic_page";
    }

    @GetMapping({"/profile"})
    public String profilePage(Model model, @RequestParam String login) {
        model.addAttribute("user", userService.getUserByLogin(login));
        model.addAttribute("messages", messageService.getNumberOfMessagesByLogin(login));
        model.addAttribute("articles", articleService.getNumberOfArticlesByLogin(login));
        return "profile_page";
    }

    @PostMapping({"/edit"})
    public String profileEditPage(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("login", params.get("login"));
        model.addAttribute("name", params.get("name"));
        model.addAttribute("phone", params.get("phone"));
        model.addAttribute("description", params.get("description").replace("<br>", ""));
        return "profile_edit_page";
    }

    @GetMapping({"/messages"})
    public String messagesPage(Model model, @RequestParam String login) {
        model.addAttribute("messages", messageService.getMessagesByLogin(login));
        model.addAttribute("login", login);
        return "messages_page";
    }

    @GetMapping({"/private_in"})
    public String privateMessagesInboxPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("messages", privateMessageService.getPrivateMessagesByRecipient(user.getLogin()));
            return "private_messages_inbox_page";
        } else {
          return "redirect:/index";
        }
    }

    @GetMapping({"/private_out"})
    public String privateMessagesOutboxPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("messages", privateMessageService.getPrivateMessagesBySender(user.getLogin()));
            return "private_messages_outbox_page";
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping({"/private_view"})
    public String privateMessage(Model model, HttpSession session, @RequestParam long id) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("message", privateMessageService.getPrivateMessage(user, id));
            return "private_message_page";
        } else {
            return "redirect:/index";
        }
    }

}
