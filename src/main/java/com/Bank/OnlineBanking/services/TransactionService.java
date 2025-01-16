package com.Bank.OnlineBanking.services;

import java.util.List;

import com.Bank.OnlineBanking.dto.TransactionDateDto;
import com.Bank.OnlineBanking.entity.Transaction;

public interface TransactionService {
	
	public Transaction getTransactionById(Long transactionId);
	
	public List<Transaction> getTransactionsByFromAccountNumber(String accountNumber);
	
	public List<Transaction> getTransactionByToAccountNumber(String accountNumber);
	
	public List<Transaction> getTransactionsByTransactionDate(TransactionDateDto transactionDateDto);
	
	public List<Transaction> getTransactionsByTransactionType(String transactionType);
	
	public List<Transaction> getTransactionsByTransactionStatus(String transactionStatus);

}
