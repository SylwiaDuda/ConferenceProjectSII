package com.pl.conference.service;

import com.pl.conference.data.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
