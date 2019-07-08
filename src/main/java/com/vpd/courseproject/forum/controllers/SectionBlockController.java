package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.ISectionBlockService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class SectionBlockController {
    private ISectionBlockService sectionBlockService;

    public SectionBlockController(ISectionBlockService sectionBlockService) {
        this.sectionBlockService = sectionBlockService;
    }

    @PostMapping("/add_section_block")
    public String add(HttpSession session, @RequestParam String sectionBlockName) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            String path = sectionBlockService.addSectionBlockAndReturnPath(user.getLogin(), sectionBlockName);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/rename_block")
    public String rename(HttpSession session, @RequestParam String newName, @RequestParam long id) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            String path = sectionBlockService.renameSectionBlockAndReturnPath(user.getLogin(), id, newName);
            return "redirect:" + path;
        } else {
            return "redirect:/index";
        }
    }

    @PostMapping("/delete_block")
    public String delete(HttpSession session, @RequestParam long id) {
        User user = (User) session.getAttribute("user");
        if (validate(user)) {
            sectionBlockService.deleteSectionBlock(user.getLogin(), id);
        }
        return "redirect:/index";
    }

    private boolean validate(User user) {
        return user != null && user.getRole().equals(User.Role.ADMIN);
    }
}


