/*
 * File: Member.java
 * Project: db
 * File Created: Tuesday, 22nd October 2019 7:30:15 pm
 * Author: sangmok (appsoga@gmail.com)
 * -----
 * Copyright 2019 - 2019 APPSOGA Inc.
 */
package com.example.demo.data;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@lombok.Data
@Entity
@Table(name = "TB_MEMBER")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRES_ON")
    private Calendar expiresOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ACCESSS_ON")
    private Calendar lastAccessedOn;
}
