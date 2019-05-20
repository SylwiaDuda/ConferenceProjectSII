package com.pl.conference.service;

import com.pl.conference.data.entity.Lecture;
import com.pl.conference.data.entity.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailService {

    public static void sendEmail(User user, Lecture lecture) {
        try {
            FileWriter fileWriter = new FileWriter("powiadomienia.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            List<String> email = buildMessage(user, lecture);
            try {
                for (String line : email) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } finally {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, "Email send error");
        }
    }

    private static List<String> buildMessage(User user, Lecture lecture) {
        List<String> email = new ArrayList<>();
        email.add("Data wyslania: " + generateDate(new Date()));
        email.add("Do: " + user.getEmail());
        email.add(buildMessageContent(lecture));
        return email;
    }

    public static String generateDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private static String buildMessageContent(Lecture lecture) {
        Date lectureDate = lecture.getStartDate();
        String content = "Dokonano reserwacji na prelekcję: " + lecture.getLectureName() + ". "
                + "Termin: " + generateDate(lectureDate);
        return content;
    }
}
