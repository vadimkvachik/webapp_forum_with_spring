package com.vpd.courseproject.forum.controllers;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IMailService;
import com.vpd.courseproject.forum.service.api.IUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
    private IUserService userService;
    private IMailService mailService;

    public UserController(IUserService userService, IMailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("/locale")
    public String changeLocale(@RequestParam String lang, HttpSession session) {
        if (lang != null && (lang.equals("ru") || lang.equals("en"))) {
            session.setAttribute("lang", lang);
        }
        return "redirect:/index";
    }

    @PostMapping("/change_password")
    public String changePassword(@RequestParam Map<String, String> params, Model model) {
        if (userService.changePassword(params.get("login"), params.get("old_password"), params.get("new_password"))) {
            model.addAttribute("information", "successful_password_change");
        } else {
            model.addAttribute("information", "fail_password_change");
        }
        return "information_page";
    }

    @PostMapping("/role")
    public String changeRole(@RequestParam String login, @RequestParam String role, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().equals(User.Role.ADMIN) && !login.equals("admin")) {
            userService.changeRole(login, role);
            logger.info("User '" + user.getLogin() + "' changed the role of user '" + login + "' to '" + role + "'");
        }
        return "redirect:/users";
    }

    @PostMapping("/edit_profile")
    public String editProfile(@RequestParam Map<String, String> params, HttpSession session) {
        String login = params.get("login");
        userService.editProfile(login, params.get("new_name"), params.get("new_phone"), params.get("new_description"));
        session.setAttribute("user", userService.getUserByLogin(login));
        return "redirect:/profile?login=" + login;
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam String login, HttpSession session, Model model) {
        userService.deleteUser(login);
        session.setAttribute("user", null);
        model.addAttribute("login", login);
        model.addAttribute("information", "your_profile_deleted");
        return "information_page";
    }

    @PostMapping("/restore_profile")
    public String restoreProfile(@RequestParam String login, HttpSession session, Model model) {
        User user = userService.restoreUser(login);
        session.setAttribute("user", user);
        model.addAttribute("information", "successful_restore_yourself");
        return "information_page";
    }

    @PostMapping("/lock")
    public String lockUser(@RequestParam String login, @RequestParam(required = false) String reason, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && !user.getRole().equals(User.Role.USER) && !login.equals("admin")) {
            userService.lockOrUnlockUser(user.getLogin(), login, reason);
        }
        return "redirect:/users";
    }

    @GetMapping("/initialization")
    public String init(Model model) {
        if (userService.createAdministrator()) {
            model.addAttribute("information", "admin_register_successful");
            return "information_page";
        }
        return "redirect:/index";
    }

    @PostMapping("/pass_recovery")
    public String passwordRecovery(@RequestParam String email, HttpSession session, Model model) {
        User user = userService.getUserByEmail(email);
        String lang = (String) session.getAttribute("lang");
        if (user != null) {
            mailService.sendMailForPasswordRecovery(user, lang);
            model.addAttribute("information", "successful_send_new_password");
        } else {
            model.addAttribute("information", "fail_send_new_password");
        }
        return "information_page";
    }

    @PostMapping("/reg")
    public String registration(@RequestParam Map<String, String> params, HttpSession session) {
        User user = userService.createUser(params.get("login"), params.get("pass"), params.get("name"),
                params.get("email"), params.get("phone"), params.get("description"));
        session.setAttribute("user", user);
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam String login, @RequestParam String password, HttpSession session) {
        User user = userService.getUserByLogin(login);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (user.isDeleted()) {
                    model.addAttribute("information", "your_profile_deleted");
                    model.addAttribute("login", user.getLogin());
                    return "information_page";
                } else {
                    session.setAttribute("user", user);
                    logger.info("User '" + user.getLogin() + "' logged in");
                    return "redirect:/index";
                }
            } else {
                return "login_page";
            }
        } else {
            return "login_page";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        session.setAttribute("user", null);
        logger.info("User '" + user.getLogin() + "' logged out");
        return "redirect:/index";
    }

}
