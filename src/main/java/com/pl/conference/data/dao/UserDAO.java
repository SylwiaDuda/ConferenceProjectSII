package com.pl.conference.data.dao;

import com.pl.conference.data.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends BasicDAO<User, Long> {

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByUserName(String userName);

    User findUserByUserNameAndEmail(String email, String password);

    @Query("SELECT e.email, e.password FROM User e")
    List<Object[]> findUserSignInData();
}
