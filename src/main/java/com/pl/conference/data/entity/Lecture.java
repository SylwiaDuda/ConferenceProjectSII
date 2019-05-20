package com.pl.conference.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lectures")
public class Lecture extends BasicEntity {

    @Column(name = "lecture_name")
    @NotNull
    private String lectureName;


    @Column(name = "start_date")
    @NotNull
    private Date startDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Lectures_Users",
            joinColumns = {@JoinColumn(name = "id_lecture")},
            inverseJoinColumns = {@JoinColumn(name = "id_user")}
    )
    private Set<User> participantsOfLecture = new HashSet<>(0);

    public Lecture() {
    }

    public Lecture(@NotNull String lectureName, @NotNull Date startDate) {
        this.lectureName = lectureName;
        this.startDate = startDate;
    }

    public Lecture(@NotNull String lectureName, @NotNull Date startDate, Set<User> participantsOfLecture) {
        this.lectureName = lectureName;
        this.startDate = startDate;
        this.participantsOfLecture = participantsOfLecture;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Set<User> getParticipantsOfLecture() {
        return participantsOfLecture;
    }

    public void setParticipantsOfLecture(Set<User> participantsOfLecture) {
        this.participantsOfLecture = participantsOfLecture;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "lectureName='" + lectureName + '\'' +
                ", startDate=" + startDate +
                ", participantsOfLecture=" + participantsOfLecture +
                '}';
    }
}
