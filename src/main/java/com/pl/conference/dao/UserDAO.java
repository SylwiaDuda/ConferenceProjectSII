package com.pl.conference.dao;

import com.pl.conference.entity.User;

public interface UserDAO extends BasicDAO<User, Long> {

    User findUserByEmail(String email);

}
