package com.pl.conference.ui.navigation;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class SessionManager {

    public static void setLoggedInUser(UI ui, String email) {
        getVaadinSession(ui).setAttribute("loggedInUser", email);
    }

    public static String getLoggedInUser(UI ui) {
        VaadinSession vaadinSession = getVaadinSession(ui);
        return (String) vaadinSession.getAttribute("loggedInUser");
    }

    public static void closeVaadinSession(UI ui) {
        getVaadinSession(ui).close();
        ui.getPage().setLocation("");
    }

    private static VaadinSession getVaadinSession(UI ui) {
        UI currentUI = ui.getCurrent();
        return currentUI.getSession();
    }
}

