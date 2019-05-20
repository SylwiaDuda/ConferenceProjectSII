package com.pl.conference.ui.view.components;

import com.pl.conference.data.entity.Lecture;
import com.pl.conference.data.entity.User;
import com.pl.conference.service.MailService;
import com.pl.conference.service.UserService;
import com.pl.conference.ui.navigation.SessionManager;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

public class ReservationWindow extends Window {

    private static final String CAPTION_LOGIN = "Login";
    private static final String CAPTION_EMAIL = "E-mail";
    private static final String CAPTION_RESERVE = "Rezerwuj";
    private static final String CORRECT_DATA = "Wprowadzono poprawne dane";

    private UserService userService;

    private TextField userNameField;
    private TextField emailField;
    private Button resevationButton;
    private LectureComponent lectureComponent;

    public ReservationWindow(LectureComponent lectureComponent, UserService userService) {
        this.lectureComponent = lectureComponent;
        this.userService = userService;
    }

    public void build() {
        VerticalLayout content = new VerticalLayout();
        this.setContent(content);
        this.userNameField = new TextField(CAPTION_LOGIN);
        this.emailField = new TextField(CAPTION_EMAIL);
        this.resevationButton = new Button(CAPTION_RESERVE, clickEvent -> tryReserve());
        content.addComponents(userNameField, emailField, resevationButton);
        this.setResizable(false);
    }

    private void tryReserve() {
        UI ui = getUI();
        Page page = ui.getPage();
        this.getCaption();
        String login = userNameField.getValue();
        String email = emailField.getValue();
        String message = userService.checkUserData(login, email);
        if (message.equals(CORRECT_DATA)) {
            Lecture lecture = lectureComponent.getLecture();
            User user = userService.reserve(login, email, lecture);
            SessionManager.setLoggedInUser(ui, user);
            close();
            page.reload();
        } else {
            Notification.show(message, Notification.Type.ERROR_MESSAGE);
        }
    }
}
