package org.expressme.examples.typecho.entity;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class Contents {
	private int cid;
	private String title;
	private String slug;
	private int created;
	private int modified;
	private String text;
	private int orders;
	private int authorid;
	private String template;
	private String type;
	private String status;
	private String password;
	private int comments;
	private String allowcomment;
	private String allowping;
	private String allowfeed;
	private int parent;
}
