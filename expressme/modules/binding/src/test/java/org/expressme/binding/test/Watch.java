package org.expressme.binding.test;

public class Watch {
	private int id;
	private String name;
	private String[] haha;
	private Dog dog;
	private Dog[] dogs;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Dog getDog() {
		return dog;
	}
	public void setDog(Dog dog) {
		this.dog = dog;
	}
	public void setHaha(String[] haha) {
		this.haha = haha;
	}
	public String[] getHaha() {
		return haha;
	}
}
