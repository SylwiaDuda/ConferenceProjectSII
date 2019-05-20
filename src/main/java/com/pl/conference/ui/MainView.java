package com.pl.conference.ui;

import com.pl.conference.ui.navigation.NavigationManager;
import com.pl.conference.ui.navigation.SessionManager;
import com.pl.conference.ui.view.ConferencePlanView;
import com.pl.conference.ui.view.SettingsView;
import com.pl.conference.ui.view.SignInView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Objects;

public class MainView extends HorizontalLayout {

    private static final String TITLE = "KONFERENCJA";
    private static final String CAPTION_CONFERENCE_PLAN = "Plan konferencji";
    private static final String CAPTION_SETTINGS = "Ustawienia";
    private static final String CAPTION_SIGN_IN = "Zaloguj się";
    private static final String CAPTION_LOG_OUT = "Wyloguj się";

    private final NavigationManager navigationManager;

    private CssLayout menu;
    private Label title;
    private Button conferencePlan;
    private Button settings;
    private Button signIn;
    private Button logOut;
    private CssLayout content;

    public MainView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public CssLayout getContent() {
        return content;
    }

    public void build() {

        createMenu();
        content = new CssLayout();
        content.setWidth("100%");
        addComponents(menu, content);

        setExpandRatio(content, 1.0F);
        setSizeFull();

    }

    private void createMenu() {

        menu = new CssLayout();
        menu.setWidth("200");
        menu.setHeight("100%");
        menu.addStyleName(ValoTheme.MENU_ROOT);

        title = new Label(TITLE);
        title.addStyleName(ValoTheme.MENU_TITLE);
        title.setWidth("100%");

        createConferencePlanButton();
        createSettingsButton();
        createSignInButton();
        createLogOutButton();
        //addMenuComponents();
        menu.addComponents(title, conferencePlan, signIn, settings, logOut);
    }

    private void addMenuComponents() {
        String loggedInUser = SessionManager.getLoggedInUser(getUI());
        if (Objects.isNull(loggedInUser)) {
            menu.addComponents(title, conferencePlan, signIn);
        } else {
            menu.addComponents(title, conferencePlan, settings, logOut);
        }
    }

    private void createConferencePlanButton() {
        conferencePlan = new Button(CAPTION_CONFERENCE_PLAN, e -> navigationManager.navigateTo(ConferencePlanView.class));
        conferencePlan.addStyleName(ValoTheme.BUTTON_LINK);
        conferencePlan.addStyleName(ValoTheme.MENU_ITEM);
        conferencePlan.setIcon(VaadinIcons.HOME);
    }

    private void createSettingsButton() {
        settings = new Button(CAPTION_SETTINGS, e -> navigationManager.navigateTo(SettingsView.class));
        settings.addStyleName(ValoTheme.BUTTON_LINK);
        settings.addStyleName(ValoTheme.MENU_ITEM);
        settings.setIcon(VaadinIcons.COGS);
    }

    private void createSignInButton() {
        signIn = new Button(CAPTION_SIGN_IN, e -> navigationManager.navigateTo(SignInView.class));
        signIn.addStyleName(ValoTheme.BUTTON_LINK);
        signIn.addStyleName(ValoTheme.MENU_ITEM);
        signIn.setIcon(VaadinIcons.SIGN_IN);
    }

    private void createLogOutButton() {
        logOut = new Button(CAPTION_LOG_OUT, e -> logout());
        logOut.addStyleName(ValoTheme.BUTTON_LINK);
        logOut.addStyleName(ValoTheme.MENU_ITEM);
        logOut.setIcon(VaadinIcons.SIGN_OUT);
    }

    private void logout() {
        UI ui = getUI();
        SessionManager.closeVaadinSession(ui);
    }
}
