package org.expressme.simplejdbc;

import org.expressme.simplejdbc.annotations.Column;
import org.expressme.simplejdbc.annotations.Entity;
import org.expressme.simplejdbc.annotations.Id;
import org.expressme.simplejdbc.annotations.Table;
import org.expressme.simplejdbc.annotations.Transient;
import org.expressme.simplejdbc.core.ActiveRecord;

@Entity
@Table(name = "t_user")
public class User extends ActiveRecord<User, Integer> {
	@Id
	private Integer id;
	@Column(name="i_name")
	private String name;
	private String nick;
	@Transient
	private String mail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
