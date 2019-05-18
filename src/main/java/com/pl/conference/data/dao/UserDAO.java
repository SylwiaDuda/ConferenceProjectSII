package com.pl.conference.data.dao;

import com.pl.conference.data.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDAO extends BasicDAO<User, Long> {

    User findUserByEmail(String email);

    @Query("SELECT e.email, e.password FROM User e")
    List<Object[]> findUserSignInData();

//    @Query("SELECT p.password FROM User p where p.email =? 1")
//    String findUserPassword(String en);
}
