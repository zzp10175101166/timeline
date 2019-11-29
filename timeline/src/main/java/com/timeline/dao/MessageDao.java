package com.timeline.dao;

import com.timeline.dataObject.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface MessageDao extends JpaRepository<Message,Long> {
//    public Page<Message> findAll(Pageable pageable);

}
