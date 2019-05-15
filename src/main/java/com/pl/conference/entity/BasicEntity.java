package com.pl.conference.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class BasicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BasicEntity{" +
                "id=" + id +
                '}';
    }
}
