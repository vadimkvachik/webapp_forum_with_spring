package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MessageController {
    private IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/add_message")
    public String add(HttpSession session, @RequestParam long topicId, @RequestParam String messageText) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String path = messageService.addMessageAndReturnPath(user, topicId, messageText);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/change_message")
    public String change(HttpSession session, @RequestParam long id, @RequestParam String page, @RequestParam String newText) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String path =  messageService.changeMessageAndReturnPath(user.getLogin(), id, newText, page);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/delete_message")
    public String change(HttpSession session, @RequestParam long id, @RequestParam int page, @RequestParam int numberOfMessages) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String path = messageService.deleteMessageAndReturnPath(user.getLogin(), id, page, numberOfMessages);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }
}
