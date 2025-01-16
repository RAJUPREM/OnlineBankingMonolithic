package com.Bank.OnlineBanking.dto;

import java.util.Set;

import com.Bank.OnlineBanking.entity.Address;
import com.Bank.OnlineBanking.entity.Role;

public class UserAccDto {

	public UserAccDto(String userName, String email, String phone, Address address, Set<String> roles) {
		super();
		this.userName = userName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.roles = roles;
	}

	public UserAccDto() {
		super();
	}

	private String userName;
	
	private String email;
	
	private String phone;
	
	private Address address;
	
	private Set<String> roles;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserAccDto [userName=" + userName + ", email=" + email + ", phone=" + phone + ", address=" + address
				+ ", roles=" + roles + "]";
	}

	

	
}
