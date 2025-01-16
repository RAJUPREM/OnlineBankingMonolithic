package com.Bank.OnlineBanking.dto;

public class CashDepositeDto {
	
	public CashDepositeDto(String sourceAccountNumber, Double amount) {
		super();
		this.sourceAccountNumber = sourceAccountNumber;
		this.amount = amount;
	}

	public CashDepositeDto() {
		super();
	}

	private String sourceAccountNumber;
	
	private Double amount;

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CashDepositeDto [sourceAccountNumber=" + sourceAccountNumber + ", amount=" + amount + "]";
	}

}
