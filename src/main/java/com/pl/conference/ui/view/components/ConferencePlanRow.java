package com.pl.conference.ui.view.components;

import com.pl.conference.data.entity.Lecture;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConferencePlanRow extends HorizontalLayout {

    private List<Lecture> lectures;
    private Label lectureHourLabel;

    public ConferencePlanRow(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void build() {
        this.setWidth("100%");
        createlectureHourLabel();
        for (Lecture lecture : lectures) {
            LectureField lectureField = new LectureField(lecture);
            this.addComponent(lectureField);
        }
    }

    private void createlectureHourLabel() {
        Lecture lecture = lectures.get(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date lectureStartDate = lecture.getStartDate();
        Date lectureEndDate = addHoursAndMinutesToDate(lectureStartDate, 1, 45);
        String lectureStartHour = dateFormat.format(lectureStartDate);
        String lectureEndHour = dateFormat.format(lectureEndDate);
        String durationOfLecture = lectureEndHour + " - " + lectureEndHour;
        lectureHourLabel = new Label(durationOfLecture);
        this.addComponent(lectureHourLabel);
        this.setComponentAlignment(lectureHourLabel, Alignment.MIDDLE_CENTER);
    }

    public Date addHoursAndMinutesToDate(Date date, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
}
