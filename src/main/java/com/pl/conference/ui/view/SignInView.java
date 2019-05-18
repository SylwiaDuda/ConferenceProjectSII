package com.pl.conference.ui.view;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;

import javax.annotation.PostConstruct;

@SpringView
public class SignInView extends VerticalLayout implements View {

    private static final String TITLE = "Logowanie";
    private static final String CAPTION_LOGIN = "Login:";
    private static final String CAPTION_PASSWORD = "Hasło:";
    private static final String CAPTION_SIGN_IN = "Zaloguj się";

    private Panel signInPanel;
    private FormLayout signInForm;
    private ComboBox<String> loginComboBox;
    private PasswordField passwordField;
    private Button signInButton;

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
        loginComboBox.setItems("user1", "user2", "user3", "user4", "user5");

        passwordField = new PasswordField(CAPTION_PASSWORD);
        signInButton = new Button(CAPTION_SIGN_IN);
    }
}
