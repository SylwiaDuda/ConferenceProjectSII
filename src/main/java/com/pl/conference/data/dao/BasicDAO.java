package com.pl.conference.data.dao;

import com.pl.conference.data.entity.BasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicDAO<ENTITY extends BasicEntity, ID> extends JpaRepository<ENTITY, ID> {


}
