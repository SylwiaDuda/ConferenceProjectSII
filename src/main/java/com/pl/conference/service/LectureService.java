package com.pl.conference.service;

import com.pl.conference.dao.LectureDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    @Autowired
    LectureDAO lectureDAO;
}
