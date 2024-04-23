package com.gjevents.usermanagementservice.service;

import com.gjevents.usermanagementservice.model.Session;

public interface SessionService {
    void saveSession(Session session);
    Session getSessionBySessionId(String sessionId);
    void deleteSession(Session session);
}