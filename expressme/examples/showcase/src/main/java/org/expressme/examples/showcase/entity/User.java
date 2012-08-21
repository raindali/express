package org.expressme.examples.showcase.entity;

import org.expressme.simplejdbc.annotations.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends AbstractAuditable<User, Integer> {
	private Integer uid;
	private String username;
	private String password;
	private String nick;
	private String mail;
	private Integer department;
}