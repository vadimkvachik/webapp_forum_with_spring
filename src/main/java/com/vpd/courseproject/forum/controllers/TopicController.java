package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.ITopicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class TopicController {
    private ITopicService topicService;

    public TopicController(ITopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/add_topic")
    public String add(HttpSession session, @RequestParam String name, @RequestParam String text, @RequestParam long sectionId) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String path = topicService.addTopicAndReturnPath(user, sectionId, name, text);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/rename_topic")
    public String rename(HttpSession session, @RequestParam long id, @RequestParam String newName) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            String path = topicService.renameTopicAndReturnPath(user.getLogin(), id, newName);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/delete_topic")
    public String delete(HttpSession session, @RequestParam long id) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            String path = topicService.deleteTopicAndReturnPath(user.getLogin(), id);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    private boolean validate(User user) {
        return user != null && !user.getRole().equals(User.Role.USER);
    }
}

