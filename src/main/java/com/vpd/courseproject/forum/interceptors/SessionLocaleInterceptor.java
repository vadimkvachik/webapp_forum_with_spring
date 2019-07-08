package com.vpd.courseproject.forum.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionLocaleInterceptor implements HandlerInterceptor {

    @Value("${forum_lang}")
    private String defaultLang;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getSession().getAttribute("lang") == null) {
            if (!(defaultLang.equals("en") || defaultLang.equals("ru"))) defaultLang = "en";
            request.getSession().setAttribute("lang", defaultLang);
        }
        return true;
    }

}
