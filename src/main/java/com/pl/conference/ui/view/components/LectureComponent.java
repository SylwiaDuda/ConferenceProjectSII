package com.pl.conference.ui.view.components;

import com.pl.conference.data.entity.Lecture;
import com.pl.conference.data.entity.User;
import com.pl.conference.service.LectureService;
import com.pl.conference.service.UserService;
import com.pl.conference.ui.navigation.SessionManager;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class LectureComponent extends VerticalLayout {

    private UserService userService;
    private LectureService lectureService;

    Consumer<LectureComponent> onComponentChange;
    private Lecture lecture;
    private Label lectureName;
    private Label noPlaces;
    private Button reservationButton;
    private Button cancelReservation;

    public LectureComponent(UserService userService, LectureService lectureService) {
        this.userService = userService;
        this.lectureService = lectureService;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void build(Lecture lecture) {
        this.lecture = lecture;
        String name = lecture.getLectureName();
        this.lectureName = new Label(name);
        lectureName.addStyleName(ValoTheme.LABEL_COLORED);
        lectureName.addStyleName(ValoTheme.LABEL_BOLD);
        this.reservationButton = new Button("Zarezerwuj");
        this.reservationButton.addClickListener(e -> reserve());
        this.noPlaces = new Label("Brak miejsc");
        this.cancelReservation = new Button("Anuluj");
        cancelReservation.addClickListener(this::cancelReservation);
        addComponents(lectureName, reservationButton, noPlaces, cancelReservation);
        setVisibilityComponents();
    }

    public void setOnComponentChange(Consumer<LectureComponent> onComponentChange) {
        this.onComponentChange = onComponentChange;
    }

    private void cancelReservation(Button.ClickEvent clickEvent) {
        UI ui = UI.getCurrent();
        User user = SessionManager.getLoggedInUser(getUI());
        user = userService.cancelReservation(user, lecture);
        SessionManager.setLoggedInUser(ui, user);
        Long lectureId = lecture.getId();
        Optional<Lecture> lectureOptional = lectureService.findById(lectureId);
        if (lectureOptional.isPresent()) {
            lecture = lectureOptional.get();
        }
        onComponentChange.accept(this);
    }

    private void reserve() {
        UI ui = getUI().getCurrent();
        User user = SessionManager.getLoggedInUser(ui);
        if (Objects.isNull(user)) {
            ReservationWindow reservationWindow = new ReservationWindow(this, userService);
            reservationWindow.build();
            ui.addWindow(reservationWindow);
            reservationWindow.setModal(true);
        } else {
            String userName = user.getUserName();
            String email = user.getEmail();
            user = userService.reserve(userName, email, lecture);
            SessionManager.setLoggedInUser(ui, user);
            Optional<Lecture> lectureOptional = lectureService.findById(lecture.getId());
            if(lectureOptional.isPresent()){
                lecture = lectureOptional.get();
            }
        }
        onComponentChange.accept(this);
    }

    public void setVisibilityComponents() {
        User user = SessionManager.getLoggedInUser(getUI());
        Set<User> users = lecture.getParticipantsOfLecture();
        if (Objects.nonNull(user)) {
            setButtonsVisibilityForLoggedUser(user, users);
        } else {
            setButtonsVisibilityForAnonymousUser(users);
        }
    }

    private void setButtonsVisibilityForAnonymousUser(Set<User> users) {
        reservationButton.setVisible(false);
        cancelReservation.setVisible(false);
        noPlaces.setVisible(false);
        if (users.size() >= 5) {
            noPlaces.setVisible(true);
        } else {
            reservationButton.setVisible(true);
        }
    }

    private void setButtonsVisibilityForLoggedUser(User user, Set<User> users) {
        boolean lectureIsBooked = users.stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
        Set<Lecture> userLectures = user.getLectures();
        boolean isAssignedToOtherPathLecture = userLectures.stream()
                .anyMatch(l -> l.getStartDate().equals(lecture.getStartDate()));
        reservationButton.setVisible(false);
        cancelReservation.setVisible(false);
        noPlaces.setVisible(false);
        if (lectureIsBooked) {
            cancelReservation.setVisible(true);
        } else {
            if (users.size() >= 5) {
                noPlaces.setVisible(true);
            } else if (!isAssignedToOtherPathLecture) {
                reservationButton.setVisible(true);
            }
        }
    }
}
