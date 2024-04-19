/*
package com.gjevents.usermanagementservice.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "SPRING_SESSION")
public class SpringSession {

    @Id
    @Column(name = "PRIMARY_ID")
    private String primaryId;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @Column(name = "CREATION_TIME")
    private Long creationTime;

    @Column(name = "LAST_ACCESS_TIME")
    private Long lastAccessTime;

    @Column(name = "MAX_INACTIVE_INTERVAL")
    private Integer maxInactiveInterval;

    @Column(name = "EXPIRY_TIME")
    private Long expiryTime;

    @Column(name = "PRINCIPAL_NAME")
    private String principalName;

    @OneToMany(mappedBy = "springSession", cascade = CascadeType.ALL)
    private Set<SpringSessionAttributes> springSessionAttributes;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Integer getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(Integer maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public Set<SpringSessionAttributes> getSpringSessionAttributes() {
        return springSessionAttributes;
    }

    public void setSpringSessionAttributes(Set<SpringSessionAttributes> springSessionAttributes) {
        this.springSessionAttributes = springSessionAttributes;
    }
}*/
