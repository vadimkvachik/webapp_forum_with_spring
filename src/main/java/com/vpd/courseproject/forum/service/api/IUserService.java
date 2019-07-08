package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.entity.User;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    User createUser(String login, String pass, String name, String email, String phone, String description);

    boolean createAdministrator();

    User getUserByLogin(String login);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    void changeRole(String login, String role);

    boolean changePassword(String login, String oldPassword, String newPassword);

    void editProfile(String login, String newName, String newPhone, String newDescription);

    void lockOrUnlockUser(String loginBlocker, String login, String reasonForBlocking);

    void deleteUser(String login);

    User restoreUser(String login);
}
