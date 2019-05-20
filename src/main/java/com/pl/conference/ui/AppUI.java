package com.pl.conference.ui;

import com.pl.conference.ui.navigation.NavigationManager;
import com.vaadin.annotations.Title;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Title("Konferencja")

public class AppUI extends UI {

    private final SpringViewProvider springViewProvider;
    private final NavigationManager navigationManager;
    private MainView mainView;

    @Autowired
    public AppUI(SpringViewProvider springViewProvider, NavigationManager navigationManager) {
        this.springViewProvider = springViewProvider;
        this.navigationManager = navigationManager;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        mainView = new MainView(navigationManager);
        mainView.build();
        setContent(mainView);
        navigationManager.init(this, mainView.getContent());
        navigationManager.navigateToDefaultView();
    }

}
