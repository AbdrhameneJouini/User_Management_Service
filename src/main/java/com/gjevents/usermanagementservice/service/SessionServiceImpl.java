package com.gjevents.usermanagementservice.service;

import com.gjevents.usermanagementservice.model.Session;
import com.gjevents.usermanagementservice.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void saveSession(Session session) {
        sessionRepository.save(session);
    }


    @Override
    public Session getSessionBySessionId(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    @Override
    public void deleteSession(Session session) {
        System.out.println("Deleting session");
        sessionRepository.delete(session);
        System.out.println("Session deleted");
    }
}