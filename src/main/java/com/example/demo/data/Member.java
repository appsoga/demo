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
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.demo.data.converter.MemberGroupConverter;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@lombok.Data
@Table(name = "TB_MEMBER")
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Convert(attributeName = "value", converter = MemberGroupConverter.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "MTYPE", length = 18, nullable = false)
	private MemberGroup group;

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
	private Boolean enabled;

	@Temporal(TemporalType.DATE)
	@Column(name = "EXPIRES_ON")
	private Date expiresOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_ACCESSS_ON")
	private Date lastAccessedOn;

}
