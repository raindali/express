package org.expressme.examples.typecho.entity;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class Users {
	private int uid;
	private String name;
	private String password;
	private String mail;
	private String url;
	private String nick;
	private int created;
	private int activated;
	private int logged;
	private String groupname;
	private String authcode;
}