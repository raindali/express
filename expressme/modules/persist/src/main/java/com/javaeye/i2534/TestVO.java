package com.javaeye.i2534;

import java.util.Date;

import com.javaeye.i2534.annotations.Column;
import com.javaeye.i2534.annotations.Id;
import com.javaeye.i2534.annotations.Table;
import com.javaeye.i2534.annotations.Transient;

@Table(name = "demo")
public class TestVO {

	@Id("id")
	private String biaoShi;

	private int count;

	@Column(insertable=false)
	private char flag;

	private Date time;
	@Transient
	private String other;

	public String getBiaoShi() {
		return biaoShi;
	}

	public void setBiaoShi(String biaoShi) {
		this.biaoShi = biaoShi;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
}
