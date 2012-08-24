package org.expressme.examples.showcase.entity;

import java.io.Serializable;

import org.expressme.simplejdbc.annotations.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User<T, ID extends Serializable> extends AbstractAuditable<T, ID> {
	private Integer uid;
	private String username;
	private String password;
	private String nick;
	private String mail;
	private Integer department;
}