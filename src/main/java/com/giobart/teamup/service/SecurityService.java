package com.giobart.teamup.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
