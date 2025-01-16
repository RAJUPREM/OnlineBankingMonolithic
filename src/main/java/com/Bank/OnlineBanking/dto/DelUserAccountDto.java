package com.Bank.OnlineBanking.dto;

public class DelUserAccountDto {
	
	public DelUserAccountDto(String userName, String accountNumber) {
		super();
		this.userName = userName;
		this.accountNumber = accountNumber;
	}

	public DelUserAccountDto() {
		super();
	}

	private String userName;
	
	private String accountNumber;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "DelUserAccountDto [userName=" + userName + ", accountNumber=" + accountNumber + "]";
	}

}
