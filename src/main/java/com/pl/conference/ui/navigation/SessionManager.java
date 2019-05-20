package com.pl.conference.ui.navigation;

import com.pl.conference.data.entity.User;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class SessionManager {
    public static void setLoggedInUser(UI ui, User user) {
        VaadinSession vaadinSession = getVaadinSession(ui);
        vaadinSession.setAttribute("loggedInUserEmail", user.getEmail());
        vaadinSession.setAttribute("loggedInUser", user);
    }

    public static User getLoggedInUser(UI ui) {
        VaadinSession vaadinSession = getVaadinSession(ui);
        return (User) vaadinSession.getAttribute("loggedInUser");
    }

    public static String getLoggedInUserEmail(UI ui) {
        VaadinSession vaadinSession = getVaadinSession(ui);
        return (String) vaadinSession.getAttribute("loggedInUserEmail");
    }

    public static void closeVaadinSession(UI ui) {
        getVaadinSession(ui).close();
        ui.getPage().reload();
    }

    private static VaadinSession getVaadinSession(UI ui) {
        UI currentUI = ui.getCurrent();
        return currentUI.getSession();
    }
}

