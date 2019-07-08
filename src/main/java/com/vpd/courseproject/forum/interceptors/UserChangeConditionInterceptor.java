package com.vpd.courseproject.forum.interceptors;

import com.vpd.courseproject.forum.persistence.entity.User;
import com.vpd.courseproject.forum.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserChangeConditionInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            User sessionUserFromDB = userService.getUserByLogin(user.getLogin());
            if (sessionUserFromDB.getReasonForBlocking() != null) {
                request.getSession().setAttribute("user", null);
                request.setAttribute("information", "your_profile_banned");
                request.setAttribute("reason", sessionUserFromDB.getReasonForBlocking());
                request.getRequestDispatcher("/WEB-INF/jsp/information_page.jsp").forward(request, response);
                return false;
            } else if (!user.getRole().equals(sessionUserFromDB.getRole())) {
                request.getSession().setAttribute("user", sessionUserFromDB);
                request.setAttribute("information", "your_role_changed");
                request.setAttribute("role", sessionUserFromDB.getRole());
                request.getRequestDispatcher("/WEB-INF/jsp/information_page.jsp").forward(request, response);
                return false;
            }
        }
        return true;
    }
}

