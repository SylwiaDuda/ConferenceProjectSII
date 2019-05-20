package com.pl.conference.service;

import com.pl.conference.data.dao.LectureDAO;
import com.pl.conference.data.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LectureService {

    private final LectureDAO lectureDAO;

    @Autowired
    public LectureService(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    public List<Lecture> findAll() {
        return lectureDAO.findAll();
    }

    public Optional<Lecture> findById(long id) {
        return lectureDAO.findById(id);
    }
}
