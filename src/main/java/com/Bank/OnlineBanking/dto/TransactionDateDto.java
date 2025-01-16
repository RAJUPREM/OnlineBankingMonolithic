package com.Bank.OnlineBanking.dto;

public class TransactionDateDto {
	
	public TransactionDateDto(String date) {
		super();
		this.date = date;
	}

	public TransactionDateDto() {
		super();
	}

	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "TransactionDateDto [date=" + date + "]";
	}

}
