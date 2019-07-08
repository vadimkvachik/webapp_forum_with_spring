package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.ISectionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class SectionController {
    private ISectionService sectionService;

    public SectionController(ISectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/add_section")
    public String add(HttpSession session, @RequestParam String name, @RequestParam long sectionBlockId) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            String path =  sectionService.addSectionAndReturnPath(user.getLogin(), name, sectionBlockId);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/rename_section")
    public String rename(HttpSession session, @RequestParam long id, @RequestParam String newName) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            String path = sectionService.renameSectionAndReturnPath(user.getLogin(), id, newName);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/delete_section")
    public String delete(HttpSession session, @RequestParam long id) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            sectionService.deleteSection(user.getLogin(), id);
        }
        return "redirect:/index";
    }

    private boolean validate(User user) {
        return user != null && user.getRole().equals(User.Role.ADMIN);
    }
}
