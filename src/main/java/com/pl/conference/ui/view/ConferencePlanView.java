package com.pl.conference.ui.view;

import com.pl.conference.data.entity.Lecture;
import com.pl.conference.service.LectureService;
import com.pl.conference.service.UserService;
import com.pl.conference.ui.navigation.NavigationManager;
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

    private static final String TITLE_LABEL_BREAK = "Przerwa";
    private static final String CAPTION_FIRST_DAY = "1 czerwiec 2019";
    private static final String CAPTION_SECOND_DAY = "2 czerwiec 2019";

    private final LectureService lectureService;
    private final UserService userService;
    private final NavigationManager navigationManager;

    private TabSheet tabsheet;
    private VerticalLayout firstDayContent;
    private VerticalLayout secondDayContent;

    @Autowired
    public ConferencePlanView(LectureService lectureService, NavigationManager navigationManager, UserService userService) {
        this.lectureService = lectureService;
        this.navigationManager = navigationManager;
        this.userService = userService;
    }

    @PostConstruct
    void init() {
        buildTabSheet();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.removeAllComponents();
        init();
    }

    private void buildTabSheet() {
        tabsheet = new TabSheet();
        this.addComponent(tabsheet);
        firstDayContent = new VerticalLayout();
        tabsheet.addTab(firstDayContent, CAPTION_FIRST_DAY);
        secondDayContent = new VerticalLayout();
        tabsheet.addTab(secondDayContent, CAPTION_SECOND_DAY);
        List<Lecture> lectures = lectureService.findAll();
        createTabContent(firstDayContent, lectures.subList(0, 6));
        createTabContent(secondDayContent, lectures.subList(6, 12));
    }

    private void createTabContent(VerticalLayout dayContent, List<Lecture> lectures) {
        ConferencePlanRow firstRow = new ConferencePlanRow(userService, lectureService);
        firstRow.build(lectures.subList(0, 3));
        Label breakLabel = new Label(TITLE_LABEL_BREAK);
        ConferencePlanRow secondRow = new ConferencePlanRow(userService, lectureService);
        secondRow.build(lectures.subList(3, 6));
        dayContent.addComponents(firstRow, breakLabel, secondRow);
        dayContent.setComponentAlignment(breakLabel, Alignment.MIDDLE_CENTER);
    }
}

