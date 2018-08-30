package com.kimhj.helloboot.board.vo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Member {

	private long index;
	
	@NotEmpty(message = "Name is mandatory.")
	private String name;

	@NotEmpty(message = "E-mail is mandatory.")
	@Email(message = "Check the e-mail patterns.")
	private String email;

	@NotEmpty(message = "Password is mandatory.")
	private String password;

	
	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
