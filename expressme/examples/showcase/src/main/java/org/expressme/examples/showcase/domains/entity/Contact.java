package org.expressme.examples.showcase.domains.entity;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class Contact {
	private int cid;
	private String name;
	private String mobile;
	private String company;
	private String nick;
	private String department;
	private String mail;
	private String group;
}
