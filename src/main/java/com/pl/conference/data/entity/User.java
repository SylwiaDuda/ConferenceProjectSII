package com.pl.conference.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BasicEntity {

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(unique = true)
    @NotNull
    //@Pattern()
    private String email;

    @NotNull
    private String password;

    @ManyToMany(mappedBy = "participantsOfLecture", fetch = FetchType.EAGER)
    private Set<Lecture> lectures = new HashSet<>();

    public User() {
    }

    public User(@NotNull String userName, @NotNull String email, @NotNull String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(@NotNull String userName, @NotNull String email, @NotNull String password, Set<Lecture> lectures) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.lectures = lectures;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", lectures=" + lectures +
                '}';
    }
}
