package com.giobart.teamup.service;

import com.giobart.teamup.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

}
