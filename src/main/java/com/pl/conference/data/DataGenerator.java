package com.pl.conference.data;

import com.pl.conference.data.dao.LectureDAO;
import com.pl.conference.data.dao.UserDAO;
import com.pl.conference.data.entity.Lecture;
import com.pl.conference.data.entity.User;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserDAO userDAO, LectureDAO lectureDAO) {
        return args -> {
            if (hasData(lectureDAO) || hasData(lectureDAO)) {
                //System.out.println("Using existing database");
                return;
            }
            for (int i = 1; i <= 10; i++) {
                User user = new User("User" + i, "user" + i + "@vp.pl", "user" + i);
                userDAO.save(user);
            }
            Date date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = dateFormat.parse("2019-06-01 10:00:00");
            for (int i = 1; i <= 12; i++) {
                if (i >= 4 && i <= 6) date = dateFormat.parse("2019-06-01 12:00:00");
                if (i >= 7 && i <= 9) date = dateFormat.parse("2019-06-02 10:00:00");
                if (i >= 10 && i <= 12) date = dateFormat.parse("2019-06-02 12:00:00");
                Lecture lecture = new Lecture("Lecture" + i, date);
                lectureDAO.save(lecture);
            }
            //System.out.println("Generated data");
        };
    }

    private boolean hasData(LectureDAO lectureDAO) {
        return lectureDAO.count() != 0L;
    }
}
