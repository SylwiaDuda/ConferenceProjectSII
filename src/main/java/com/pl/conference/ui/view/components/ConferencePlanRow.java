package com.pl.conference.ui.view.components;

import com.pl.conference.data.entity.Lecture;
import com.pl.conference.service.LectureService;
import com.pl.conference.service.UserService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConferencePlanRow extends HorizontalLayout {

    private List<Lecture> lectures;
    private Label lectureHourLabel;

    private UserService userService;
    private LectureService lectureService;

    public ConferencePlanRow(UserService userService, LectureService lectureService) {
        this.userService = userService;
        this.lectureService =  lectureService;
    }

    public void build(List<Lecture> lectures) {
        this.lectures = lectures;
        this.setWidth("100%");
        createLectureHourLabel();

        for (Lecture lecture : lectures) {
            LectureComponent lectureComponent = new LectureComponent(userService, lectureService);
            lectureComponent.build(lecture);
            lectureComponent.setOnComponentChange(this::updateRow);
            this.addComponent(lectureComponent);
        }
    }

    private void createLectureHourLabel() {
        Lecture lecture = lectures.get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date lectureStartDate = lecture.getStartDate();
        Date lectureEndDate = addHoursAndMinutesToDate(lectureStartDate, 1, 45);
        String lectureStartHour = dateFormat.format(lectureStartDate);
        String lectureEndHour = dateFormat.format(lectureEndDate);
        String durationOfLecture = lectureStartHour + " - " + lectureEndHour;
        lectureHourLabel = new Label(durationOfLecture);
        this.addComponent(lectureHourLabel);
        this.setComponentAlignment(lectureHourLabel, Alignment.MIDDLE_CENTER);
    }

    private Date addHoursAndMinutesToDate(Date date, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    private void updateRow(LectureComponent component){
        for (int i = 0; i < this.getComponentCount(); i++) {
            Component c = this.getComponent(i);
            if (c instanceof LectureComponent) {
                LectureComponent lectureComponent = (LectureComponent) c;
                lectureComponent.setVisibilityComponents();
            }
        }
    }
}
