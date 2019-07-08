package com.vpd.courseproject.forum.interceptors;

import com.vpd.courseproject.forum.persistence.dao.IPrivateMessageDao;
import com.vpd.courseproject.forum.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserNewMessagesInterceptor implements HandlerInterceptor {

    @Autowired
    private IPrivateMessageDao privateMessageDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            request.setAttribute("number_of_unread_messages", privateMessageDao
                    .countByToLoginAndReadIsFalseAndDeletedToFalse(user.getLogin()));
        }
        return true;
    }

}
