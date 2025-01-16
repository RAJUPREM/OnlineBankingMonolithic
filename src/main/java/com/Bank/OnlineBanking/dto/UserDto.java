package com.Bank.OnlineBanking.dto;

import java.util.Set;

import com.Bank.OnlineBanking.entity.Address;
import com.Bank.OnlineBanking.entity.Role;

import jakarta.persistence.Column;

public class UserDto {
	
	public UserDto(String userName, String personName, String personMotherName, String password, String email,
			String phone, Address address, Set<String> roles) {
		super();
		this.userName = userName;
		this.personName = personName;
		this.personMotherName = personMotherName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.roles = roles;
	}

	public UserDto() {
		super();
	}
	
	private String userName;
	
    private String personName;
    
    private String personMotherName;
	
	private String password;
	
	private String email;
	
	private String phone;
	
	private Address address;
	
	private Set<String> roles;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonMotherName() {
		return personMotherName;
	}

	public void setPersonMotherName(String personMotherName) {
		this.personMotherName = personMotherName;
	}

	@Override
	public String toString() {
		return "UserDto [userName=" + userName + ", personName=" + personName + ", personMotherName=" + personMotherName
				+ ", password=" + password + ", email=" + email + ", phone=" + phone + ", address=" + address
				+ ", roles=" + roles + "]";
	}

}
