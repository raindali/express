package org.expressme.examples.typecho.entity;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class Metas {
	private int mid;
	private String name;
	private String slug;
	private String type;
	private String description;
	private int counts;
	private int orders;
}
