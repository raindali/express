package org.expressme.examples.showcase.entity;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class User {
	private int uid;
	private String username;
	private String password;
	private String nick;
	private String mail;
	private int department;
}