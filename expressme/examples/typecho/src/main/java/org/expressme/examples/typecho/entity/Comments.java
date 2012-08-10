package org.expressme.examples.typecho.entity;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class Comments {
	private int coid;
	private int cid;
	private int created;
	private String author;
	private int authorid;
	private int ownerid;
	private String mail;
	private String url;
	private String ip;
	private String agent;
	private String text;
	private String type;
	private String status;
	private int parent;
}