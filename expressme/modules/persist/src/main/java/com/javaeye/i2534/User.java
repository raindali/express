package com.javaeye.i2534;

import com.javaeye.i2534.annotations.Column;
import com.javaeye.i2534.annotations.Id;
import com.javaeye.i2534.annotations.Table;

@Table
public class User {

	@Id
	private int id;
	@Column(name="t_name")
	private String name;
	@Column
	private String nick;
	@Column
	private String mail;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
}
