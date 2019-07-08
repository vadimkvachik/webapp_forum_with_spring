package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.exceptions.SendEmailException;
import com.vpd.courseproject.forum.persistence.entity.User;

public interface IMailService {

    void sendMailForPasswordRecovery(User user, String lang) throws SendEmailException;

}
