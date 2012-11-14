package org.expressme.examples.showcase.domains.entity;

import lombok.Getter;
import lombok.Setter;

import org.expressme.persist.annotations.Entity;

@Getter
@Setter
@Entity
public class Department extends AbstractAuditable {
	private int did;
	private int parentDid;
	private String name;
	private int sort;
	private int state;
}
