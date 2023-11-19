package com.feeling.app.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String password;

    public User() { }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
