package com.gjevents.usermanagementservice.repository;

import com.gjevents.usermanagementservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository  extends JpaRepository<Session, Long>{


    Session findBySessionId(String sessionId);
    Session findSessionById(Long id);





}
