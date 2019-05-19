package com.pl.conference.ui.view.components;

import com.pl.conference.data.entity.Lecture;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class LectureField extends VerticalLayout {

    private Lecture lecture;
    private Label lectureName;
    private Label noPlaces;
    private Button reservationButton;
    private Button cancelButton;

    public LectureField(Lecture lecture) {
        this.lecture = lecture;
        build();
    }

    public void build() {
        String name = lecture.getLectureName();
        this.lectureName = new Label(name);
        this.reservationButton = new Button("Zarezerwuj");
        this.reservationButton.addClickListener(e -> reserve());
        this.noPlaces = new Label("Brak miejsc");
        this.cancelButton = new Button("Anuluj");
        this.reservationButton.addClickListener(e -> CancelReserve());
        addComponents(lectureName, reservationButton);
    }

    private void reserve() {
        // implementation
    }

    private void CancelReserve() {
        // implementation
    }
}
