/*
package com.gjevents.usermanagementservice.model;

import jakarta.persistence.*;


@Entity
@Table(name = "SPRING_SESSION_ATTRIBUTES")
public class SpringSessionAttributes {

    @Id
    @Column(name = "SESSION_PRIMARY_ID")
    private String sessionPrimaryId;

    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;

    @Lob
    @Column(name = "ATTRIBUTE_BYTES")
    private byte[] attributeBytes;

    @ManyToOne
    @JoinColumn(name = "SESSION_PRIMARY_ID", referencedColumnName = "PRIMARY_ID", insertable = false, updatable = false)
    private SpringSession springSession;

    public String getSessionPrimaryId() {
        return sessionPrimaryId;
    }

    public void setSessionPrimaryId(String sessionPrimaryId) {
        this.sessionPrimaryId = sessionPrimaryId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public byte[] getAttributeBytes() {
        return attributeBytes;
    }

    public void setAttributeBytes(byte[] attributeBytes) {
        this.attributeBytes = attributeBytes;
    }

    public SpringSession getSpringSession() {
        return springSession;
    }

    public void setSpringSession(SpringSession springSession) {
        this.springSession = springSession;
    }
}*/
