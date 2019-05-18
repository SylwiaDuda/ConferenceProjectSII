package com.pl.conference.data;

import com.pl.conference.data.dao.UserDAO;
import com.pl.conference.data.entity.User;
import com.vaadin.spring.annotation.SpringComponent;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(UserDAO userDAO) {
        return args -> {
            if (hasData(userDAO)) {
                //System.out.println("Using existing database");
                return;
            }
            for (int i = 1; i <= 10; i++) {
                User user = new User("User" + i, "user" + i + "@vp.pl", "user" + i);
                userDAO.save(user);
            }
            //System.out.println("Generated data");
        };
    }

    private boolean hasData(UserDAO userDAO) {
        return userDAO.count() != 0L;
    }
}
