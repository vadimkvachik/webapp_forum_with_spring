package com.vpd.courseproject.forum.exceptions;

import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = Logger.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({JDBCConnectionException.class})
    public String handleJdbcConnectionException(Model model) {
        String message = "Can't connect to DB";
        model.addAttribute("errorInfo", message);
        logger.error(message);
        return "error_page";
    }

    @ExceptionHandler({SendEmailException.class})
    public String handleSendEmailException(Model model) {
        String message = "Can't send email";
        model.addAttribute("errorInfo", message);
        logger.error(message);
        return "error_page";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid parameters")
    @ExceptionHandler({NullPointerException.class})
    public void handleNPE() {
    }

}
