package com.Bank.OnlineBanking.entity;

public class ScheduleTransaction {
	
	public ScheduleTransaction(String sourceAccountNumber, String destinationAccountNumber, Double amount,
			String scheduledTime, String status) {
		super();
		this.sourceAccountNumber = sourceAccountNumber;
		this.destinationAccountNumber = destinationAccountNumber;
		this.amount = amount;
		this.scheduledTime = scheduledTime;
		this.status = status;
	}

	public ScheduleTransaction() {
		super();
	}

	private String sourceAccountNumber;
	
	private String destinationAccountNumber;
	
	private Double amount;
	
	private String scheduledTime;
	
	private String status;

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

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ScheduleTransaction [sourceAccountNumber=" + sourceAccountNumber + ", destinationAccountNumber="
				+ destinationAccountNumber + ", amount=" + amount + ", scheduledTime=" + scheduledTime + ", status="
				+ status + "]";
	}

}
