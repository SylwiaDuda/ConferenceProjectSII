package com.pl.conference.service;

import com.pl.conference.data.dao.LectureDAO;
import com.pl.conference.data.dao.UserDAO;
import com.pl.conference.data.entity.Lecture;
import com.pl.conference.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private static final String INCORRECT_DATA = "Niepoprawne dane";
    private static final String UNAVAILABLE_LOGIN = "Podany login jest już zajęty";
    private static final String CORRECT_DATA = "Wprowadzono poprawne dane";

    private final UserDAO userDAO;
    private final LectureService lectureService;
    private final LectureDAO lectureDAO;

    @Autowired
    public UserService(UserDAO userDAO, LectureService lectureService, LectureDAO lectureDAO) {
        this.userDAO = userDAO;
        this.lectureService = lectureService;
        this.lectureDAO = lectureDAO;
    }

    public User findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
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

    public String checkUserData(String login, String email) {
        Optional<User> userOptional = userDAO.findUserByUserName(login);
        if (!userOptional.isPresent()) {
            return INCORRECT_DATA;
        } else {
            User user = userOptional.get();
            String userEmail = user.getEmail();
            if (!userEmail.equals(email)) {
                return UNAVAILABLE_LOGIN;
            }
            return CORRECT_DATA;
        }
    }

    public User cancelReservation(User currentUser, Lecture lecture) {
        User user = findUserByEmail(currentUser.getEmail());
        Set<User> users = lecture.getParticipantsOfLecture();
        users.removeIf(u -> u.getEmail().equals(user.getEmail()));
        lecture.setParticipantsOfLecture(users);
        Set<Lecture> lectures = user.getLectures();
        lectures.removeIf(l -> l.getId().equals(lecture.getId()));
        user.setLectures(lectures);
        lectureDAO.saveAndFlush(lecture);
        userDAO.saveAndFlush(user);
        return user;
    }

    public User reserve(String login, String email, Lecture lecture) {
        User user = userDAO.findUserByUserNameAndEmail(login, email);
        Set<User> users = lecture.getParticipantsOfLecture();
        users.add(user);
        lecture.setParticipantsOfLecture(users);
        Set<Lecture> lectures = user.getLectures();
        lectures.add(lecture);
        user.setLectures(lectures);
        userDAO.saveAndFlush(user);
        lectureDAO.saveAndFlush(lecture);
        MailService.sendEmail(user,lecture);
        return user;
    }
}
