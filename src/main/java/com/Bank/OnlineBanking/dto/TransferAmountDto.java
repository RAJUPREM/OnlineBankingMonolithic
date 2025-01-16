package com.Bank.OnlineBanking.dto;

public class TransferAmountDto {
	
	public TransferAmountDto(String sourceAccountNumber, String destinationAccountNumber, Double amount) {
		super();
		this.sourceAccountNumber = sourceAccountNumber;
		this.destinationAccountNumber = destinationAccountNumber;
		this.amount = amount;
	}

	public TransferAmountDto() {
		super();
	}

	private String sourceAccountNumber;
	
	private String destinationAccountNumber;
	
	private Double amount;

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(String destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TransferAmountDto [sourceAccountNumber=" + sourceAccountNumber + ", destinationAccountNumber="
				+ destinationAccountNumber + ", amount=" + amount + "]";
	}

}
