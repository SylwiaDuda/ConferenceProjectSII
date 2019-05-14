package com.pl.conference.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lectures")
public class Lecture extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_lecture")
    private int id;

    @Column(name = "lecture_name")
    @NotNull
    private String lectureName;

    @Column(name = "start_date")
    @NotNull
    private Timestamp startDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Lectures_Users",
            joinColumns = { @JoinColumn(name = "id_lecture") },
            inverseJoinColumns = { @JoinColumn(name = "id_user") }
    )
    @Max(5)
    private Set<User> participantsOfLecture = new HashSet<User>(0);

    public Lecture() { }

    public Lecture(@NotNull String lectureName, @NotNull Timestamp startDate) {
        this.lectureName = lectureName;
        this.startDate = startDate;
    }

    public Lecture(@NotNull String lectureName, @NotNull Timestamp startDate, Set<User> participantsOfLecture) {
        this.lectureName = lectureName;
        this.startDate = startDate;
        this.participantsOfLecture = participantsOfLecture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Set<User> getParticipantsOfLecture() {
        return participantsOfLecture;
    }

    public void setParticipantsOfLecture(Set<User> participantsOfLecture) {
        this.participantsOfLecture = participantsOfLecture;
    }
}
