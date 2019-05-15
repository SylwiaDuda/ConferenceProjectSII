package com.pl.conference.dao;

import com.pl.conference.entity.BasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicDAO<ENTITY extends BasicEntity, ID> extends JpaRepository<ENTITY, ID> {
}
