package com.pl.conference.ui.view;

import com.pl.conference.service.UserService;
import com.pl.conference.ui.navigation.NavigationManager;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringView
public class SignInView extends VerticalLayout implements View {

    private static final String TITLE = "Logowanie";
    private static final String CAPTION_LOGIN = "Login:";
    private static final String CAPTION_PASSWORD = "Hasło:";
    private static final String CAPTION_SIGN_IN = "Zaloguj się";

    private final UserService userService;
    private final NavigationManager navigationManager;

    private Panel signInPanel;
    private FormLayout signInForm;
    private ComboBox<String> loginComboBox;
    private PasswordField passwordField;
    private Button signInButton;

    private Map<String, String> userSignInData;

    @Autowired
    public SignInView(UserService userService, NavigationManager navigationManager) {
        this.userService = userService;
        this.navigationManager = navigationManager;
    }

    @PostConstruct
    void init() {
        buildSignInPanel();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private void buildSignInPanel() {

        signInPanel = new Panel(TITLE);
        signInPanel.setIcon(VaadinIcons.SIGN_IN);
        signInPanel.setSizeUndefined();

        signInForm = new FormLayout();
        signInForm.setMargin(true);

        createSignInFormComponents();

        addComponent(signInPanel);
        signInPanel.setContent(signInForm);
        signInForm.addComponents(loginComboBox, passwordField, signInButton);

        setComponentAlignment(signInPanel, Alignment.MIDDLE_CENTER);
    }

    private void createSignInFormComponents() {
        loginComboBox = new ComboBox<>(CAPTION_LOGIN);
        userSignInData = userService.findUserSignInData();
        List<String> emails = new ArrayList<>(userSignInData.keySet());
        loginComboBox.setItems(emails);
        loginComboBox.addValueChangeListener(this::onEmailValueChange);

        passwordField = new PasswordField(CAPTION_PASSWORD);

        signInButton = new Button(CAPTION_SIGN_IN);
        signInButton.addClickListener(clickEvent -> {
            //to implementation
            navigationManager.navigateTo(ConferencePlanView.class);
        });
    }

    private void onEmailValueChange(HasValue.ValueChangeEvent<String> event) {
        String email = event.getValue();
        if (Objects.nonNull(email)) {
            String password = userSignInData.get(email);
            passwordField.setValue(password);
        } else {
            passwordField.setValue("");
        }
    }
}
