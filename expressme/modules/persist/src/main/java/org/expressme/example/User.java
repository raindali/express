package org.expressme.example;

import org.expressme.simplejdbc.ActiveRecord;
import org.expressme.simplejdbc.annotations.Column;
import org.expressme.simplejdbc.annotations.Entity;
import org.expressme.simplejdbc.annotations.Id;
import org.expressme.simplejdbc.annotations.Table;

@Entity
@Table(name = "t_user")
public class User extends ActiveRecord<User, Integer> {
	@Id
	private Integer id;
	@Column(name="i_name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
