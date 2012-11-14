package org.expressme.examples.showcase.domains.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.expressme.persist.annotations.Entity;

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