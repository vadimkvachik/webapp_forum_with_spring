package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IPrivateMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class PrivateMessagesController {
    private IPrivateMessageService privateMessageService;

    public PrivateMessagesController(IPrivateMessageService privateMessageService) {
        this.privateMessageService = privateMessageService;
    }

    @PostMapping("/send_private_message")
    public String send(@RequestParam Map<String, String> params, Model model) {
        String login = params.get("recipientLogin");
        privateMessageService.addPrivateMessage(params.get("topic"), params.get("text"), params.get("senderLogin"), login);
        model.addAttribute("login", login);
        model.addAttribute("information", "message_sent");
        return "information_page";
    }

    @PostMapping("/delete_private_message")
    public String delete(@RequestParam long id, @RequestParam String activity, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String path = privateMessageService.deletePrivateMessageAndReturnPath(user, id, activity);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }
}
