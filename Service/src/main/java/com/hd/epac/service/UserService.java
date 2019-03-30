package com.hd.epac.service;

import com.hd.epac.entity.User;

public interface UserService {
    User CheckLogin(String username, String password);
}
