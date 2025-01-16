package com.Bank.OnlineBanking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bank.OnlineBanking.dto.TransactionDateDto;
import com.Bank.OnlineBanking.entity.Transaction;
import com.Bank.OnlineBanking.services.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	TransactionService transactionServiceImpl;
	
	@GetMapping("/welcome")
	public String welcome()
	{
		return "Welcome for New Transaction";
	}
	
	@GetMapping("/from/{accountNumber}")
	public ResponseEntity<List<Transaction>> getTransactionsByFromAccountNumber(@PathVariable String accountNumber)
	{
		List<Transaction> ltran=transactionServiceImpl.getTransactionsByFromAccountNumber(accountNumber);
		return new ResponseEntity<List<Transaction>>(ltran,HttpStatus.OK);
	}
	
	@GetMapping("/to/{accountNumber}")
	public ResponseEntity<List<Transaction>> getTransactionByToAccountNumber(@PathVariable String accountNumber)
	{
		List<Transaction> ltran=transactionServiceImpl.getTransactionByToAccountNumber(accountNumber);
		return new ResponseEntity<List<Transaction>>(ltran,HttpStatus.OK);
	}
	
	@GetMapping("/date")
	public ResponseEntity<List<Transaction>> getTransactionsByTransactionDate(@RequestBody TransactionDateDto transactionDateDto)
	{
		List<Transaction> ltran=transactionServiceImpl.getTransactionsByTransactionDate(transactionDateDto);
		return new ResponseEntity<List<Transaction>>(ltran,HttpStatus.OK);
	}
	
	@GetMapping("/type/{type}")
	public ResponseEntity<List<Transaction>> getTransactionsByTransactionType(@PathVariable String type)
	{
		List<Transaction> ltran=transactionServiceImpl.getTransactionsByTransactionType(type);
		return new ResponseEntity<List<Transaction>>(ltran,HttpStatus.OK);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Transaction>> getTransactionsByTransactionStatus(@PathVariable String status)
	{
		List<Transaction> ltran=transactionServiceImpl.getTransactionsByTransactionStatus(status);
		return new ResponseEntity<List<Transaction>>(ltran,HttpStatus.OK);
	}

}
