package com.pl.conference.ui.view;

import com.pl.conference.service.UserService;
import com.pl.conference.ui.navigation.NavigationManager;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;

@SpringView
public class SettingsView extends VerticalLayout implements View {

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
        passwordField.addFocusListener(event -> clearNotification());
        emailField = new TextField(CAPTION_ENTER_EMAIL);
        emailField.addFocusListener(event -> clearNotification());
        repeatEmailField = new TextField(CAPTION_REPEAT_EMAIL);
        repeatEmailField.addFocusListener(event -> clearNotification());
        saveButton = new Button(CAPTION_SAVE);
        saveButton.addClickListener(clickEvent -> save());
    }

    private void save() {
        String password = passwordField.getValue();
        String enteredEmail = emailField.getValue();
        String repeatedEmail = repeatEmailField.getValue();

        validation(password, enteredEmail, repeatedEmail);
    }


    private boolean validation(String password, String enteredEmail, String repeatedEmail) {
        boolean sameEmail = enteredEmail.equals(repeatedEmail);

        if (!sameEmail) {
            repeatEmailField.setComponentError(new UserError("Błędne dane"));
            Notification.show("Wprowadzono inne adresy E-mail!", Notification.Type.ERROR_MESSAGE);
            return false;
        }

//        if(Objects.isNull(enteredEmail) || Objects.isNull(repeatedEmail) || Objects.isNull(password)){
//
//        }
        return true;
    }

    private void clearNotification() {
        repeatEmailField.setComponentError(null);

    }
}