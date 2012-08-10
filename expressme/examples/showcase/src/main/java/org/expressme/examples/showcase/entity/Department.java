package org.expressme.examples.showcase.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public @Getter @Setter class Department {
	private int did;
	private String name;
	private String code;
	private String prefixCode;
	private int sort;
	private int state;
	private Department prefix;
	private List<Department> suffixList;
}
