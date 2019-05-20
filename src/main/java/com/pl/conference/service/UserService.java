package com.pl.conference.service;

import com.pl.conference.data.dao.UserDAO;
import com.pl.conference.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Map<String, String> findUserSignInData() {
        List<Object[]> resultList = userDAO.findUserSignInData();
        Map<String, String> usersData = new HashMap<>();

        for (Object[] userData : resultList) {
            usersData.put((String) userData[0], (String) userData[1]);
        }
        return usersData;
    }

    public User signIn(String email, String password) {
        return userDAO.findUserByEmailAndPassword(email, password);
    }

    public User changeEmail(String email, String newEmail, String password) {
        User loggedInUser = userDAO.findUserByEmailAndPassword(email, password);
        if (Objects.isNull(loggedInUser)) return null;
        User user = userDAO.findUserByEmail(newEmail);
        if (Objects.nonNull(user)) return null;
        loggedInUser.setEmail(newEmail);
        return userDAO.saveAndFlush(loggedInUser);

    }
}
