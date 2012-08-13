package org.expressme.examples.showcase.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Company {
	private int companyId;
	private String name;
	private String city;
	private String district;
	private String address;
	private String telephone;
	private String industry;
	private String keywords;
}
