package org.zainabed.projects.translation.model;

import java.util.Date;

import javax.persistence.*;

@MappedSuperclass
abstract public class BaseModel {

    public enum STATUS {EXTENDED, UPDATED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Date createdAt;
    protected Date updatedAt;
    protected STATUS status;

    @PrePersist
    public void beforeCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void beforeUpdate() {
       // this.status = STATUS.UPDATED;
        this.updatedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}