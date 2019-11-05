/*
 * File: Member.java
 * Project: db
 * File Created: Tuesday, 22nd October 2019 7:30:15 pm
 * Author: sangmok (appsoga@gmail.com)
 * -----
 * Copyright 2019 - 2019 APPSOGA Inc.
 */
package com.example.demo.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @Column(name = "ENABLED", nullable = false, length = 1)
    private Boolean enabled = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRES_ON")
    private Date expiresOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ACCESSS_ON")
    private Date lastAccessedOn;
}
