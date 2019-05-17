package com.pl.conference.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView
public class SettingsView extends VerticalLayout implements View {

    @PostConstruct
    void init() {
        Label label = new Label("Settings view");
        addComponent(label);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}