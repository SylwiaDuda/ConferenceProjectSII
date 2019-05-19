package com.pl.conference.ui.view;

import com.pl.conference.data.entity.Lecture;
import com.pl.conference.service.LectureService;
import com.pl.conference.ui.view.components.ConferencePlanRow;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView
public class ConferencePlanView extends VerticalLayout implements View {

    private final LectureService lectureService;

    private TabSheet tabsheet;
    private VerticalLayout firstDayContent;
    private VerticalLayout secondDayContent;

    @Autowired
    public ConferencePlanView(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostConstruct
    void init() {
        buildTabSheet();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private void buildTabSheet() {
        tabsheet = new TabSheet();
        this.addComponent(tabsheet);
        firstDayContent = new VerticalLayout();
        tabsheet.addTab(firstDayContent, "1 czerwiec 2019");
        secondDayContent = new VerticalLayout();
        tabsheet.addTab(secondDayContent, "2 czerwiec 2019");
        List<Lecture> lectures = lectureService.findAll();
        createTabContent(firstDayContent, lectures.subList(0, 6));
        createTabContent(secondDayContent, lectures.subList(6, 12));
    }

    private void createTabContent(VerticalLayout dayContent, List<Lecture> lectures) {
        ConferencePlanRow firstRow = new ConferencePlanRow(lectures.subList(0, 3));
        firstRow.build();
        Label breakLabel = new Label("Przerwa");
        ConferencePlanRow secondRow = new ConferencePlanRow(lectures.subList(3, 6));
        secondRow.build();
        dayContent.addComponents(firstRow, breakLabel, secondRow);
        dayContent.setComponentAlignment(breakLabel, Alignment.MIDDLE_CENTER);
    }
}

