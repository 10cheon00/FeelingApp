package com.feeling.app.feeling.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Feeling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp createdDate;

    @Column
    private Long userId;

    @Column
    private String description;

    public Feeling() {

    }

    public Feeling(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}
