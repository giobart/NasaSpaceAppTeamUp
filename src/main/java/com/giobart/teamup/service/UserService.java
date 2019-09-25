package com.giobart.teamup.service;

import com.giobart.teamup.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void update(User user);

    User findByUsername(String username);

    List<User> getAllUsers();

}
