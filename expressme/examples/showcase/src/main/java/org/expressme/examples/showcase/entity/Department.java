package org.expressme.examples.showcase.entity;

import java.util.List;

import org.expressme.simplejdbc.annotations.Entity;
import org.expressme.simplejdbc.annotations.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Department extends AbstractAuditable {
	private int did;
	private int parentDid;
	private String name;
	private int sort;
	private int state;
	@Transient
	private Department parent;
	@Transient
	private List<Department> leaves;
}
