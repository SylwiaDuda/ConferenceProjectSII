package com.pl.conference.ui.view;

import com.pl.conference.data.entity.User;
import com.pl.conference.service.UserService;
import com.pl.conference.ui.navigation.NavigationManager;
import com.pl.conference.ui.navigation.SessionManager;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SpringView
public class SettingsView extends VerticalLayout implements View {

    private static final String INCORRECT_DATA = "Błędne dane!";
    private static final String TITLE = "Aktualizacja e-mail";
    private static final String CAPTION_PASSWORD = "Hasło:";
    private static final String CAPTION_ENTER_EMAIL = "Wprowadź nowy e-mail:";
    private static final String CAPTION_REPEAT_EMAIL = "Powtórz e-mail:";
    private static final String CAPTION_SAVE = "Zapisz";

    private final UserService userService;
    private final NavigationManager navigationManager;

    private Panel settingsPanel;
    private FormLayout settingsForm;
    private PasswordField passwordField;
    private TextField emailField;
    private TextField repeatEmailField;
    private Button saveButton;

    @Autowired
    public SettingsView(UserService userService, NavigationManager navigationManager) {
        this.userService = userService;
        this.navigationManager = navigationManager;
    }

    @PostConstruct
    void init() {
        buildSettingsPanel();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        User loggedInUser = SessionManager.getLoggedInUser(getUI());
        if (Objects.isNull(loggedInUser)) {
            navigationManager.navigateTo(ConferencePlanView.class);
        } else {
            this.removeAllComponents();
            init();
        }
    }

    private void buildSettingsPanel() {

        settingsPanel = new Panel(TITLE);
        settingsPanel.setIcon(VaadinIcons.EDIT);
        settingsPanel.setSizeUndefined();

        settingsForm = new FormLayout();
        settingsForm.setMargin(true);

        createSettingsFormComponents();

        addComponent(settingsPanel);
        settingsPanel.setContent(settingsForm);
        settingsForm.addComponents(emailField, repeatEmailField, passwordField, saveButton);

        setComponentAlignment(settingsPanel, Alignment.MIDDLE_CENTER);
    }

    private void createSettingsFormComponents() {

        passwordField = new PasswordField(CAPTION_PASSWORD);
        emailField = new TextField(CAPTION_ENTER_EMAIL);
        repeatEmailField = new TextField(CAPTION_REPEAT_EMAIL);
        saveButton = new Button(CAPTION_SAVE);
        saveButton.addClickListener(clickEvent -> save());
    }

    private void save() {

        String password = passwordField.getValue();
        String enteredEmail = emailField.getValue();
        String repeatedEmail = repeatEmailField.getValue();

        boolean correctValidation = correctValidation(password, enteredEmail, repeatedEmail);
        if (correctValidation) {
            String email = SessionManager.getLoggedInUserEmail(getUI());
            User user = userService.changeEmail(email, enteredEmail, password);
            if (Objects.nonNull(user)) {
                UI ui = this.getUI();
                Page page = ui.getPage();
                SessionManager.setLoggedInUser(ui, user);
                page.reload();
                Notification.show("Zmieniono dane");
            } else {
                Notification.show(INCORRECT_DATA, Notification.Type.ERROR_MESSAGE);
            }
        } else {
            Notification.show(INCORRECT_DATA, Notification.Type.ERROR_MESSAGE);
        }
    }

    private boolean correctValidation(String password, String enteredEmail, String repeatedEmail) {

        if (Objects.isNull(enteredEmail) || Objects.isNull(repeatedEmail) || Objects.isNull(password)) {
            return false;
        }
        return enteredEmail.equals(repeatedEmail);
    }
}