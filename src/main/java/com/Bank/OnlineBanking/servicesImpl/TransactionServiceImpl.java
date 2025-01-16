package com.Bank.OnlineBanking.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bank.OnlineBanking.dto.TransactionDateDto;
import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.Transaction;
import com.Bank.OnlineBanking.repository.AccountRepository;
import com.Bank.OnlineBanking.repository.TransactionRepository;
import com.Bank.OnlineBanking.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public Transaction getTransactionById(Long transactionId) {
		return null;
	}

	@Override
	public List<Transaction> getTransactionsByFromAccountNumber(String accountNumber) {
		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(accountNumber);
		Optional<List<Transaction>> tempTranList=transactionRepository.getTransactionsByFromAccountNumber(tempAccount.get().getId());
		return tempTranList.get();
	}

	@Override
	public List<Transaction> getTransactionByToAccountNumber(String accountNumber) {
		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(accountNumber);
		Optional<List<Transaction>> tempTranList=transactionRepository.getTransactionsByToAccountNumber(tempAccount.get().getId());
		return tempTranList.get();
	}

	@Override
	public List<Transaction> getTransactionsByTransactionDate(TransactionDateDto transactionDateDto) {
		Optional<List<Transaction>> tempTranList=transactionRepository.getTransactionsByTransactionDate(transactionDateDto.getDate());
		return tempTranList.get();
	}

	@Override
	public List<Transaction> getTransactionsByTransactionType(String transactionType) {
		Optional<List<Transaction>> tempTranList=transactionRepository.getTransactionsByTransactionType(transactionType);
		return tempTranList.get();
	}

	@Override
	public List<Transaction> getTransactionsByTransactionStatus(String transactionStatus) {
		Optional<List<Transaction>> tempTranList=transactionRepository.getTransactionsByTransactionStatus(transactionStatus);
		return tempTranList.get();
	}

}
