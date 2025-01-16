package com.Bank.OnlineBanking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Embeddable
public class Address {

	public Address(String area, String district, String state, int pin) {
		super();
		this.area = area;
		this.district = district;
		this.state = state;
		this.pin = pin;
	}

	public Address() {
		super();
	}

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//private Long id;
	
	@Column(nullable = false)
	private String area;
	
	@Column(nullable = false)
	private String district;
	
	@Column(nullable = false)
	private String state;
	
	@Column(nullable = false)
	private int pin;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "Address [area=" + area + ", district=" + district + ", state=" + state + ", pin=" + pin + "]";
	}

}
