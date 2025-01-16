package com.Bank.OnlineBanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bank.OnlineBanking.dto.CashDepositeDto;
import com.Bank.OnlineBanking.dto.DelUserAccountDto;
import com.Bank.OnlineBanking.dto.TransferAmountDto;
import com.Bank.OnlineBanking.dto.UserAccDto;
import com.Bank.OnlineBanking.dto.WithdrawalCashDto;
import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.Transaction;
import com.Bank.OnlineBanking.entity.User;
import com.Bank.OnlineBanking.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	AccountService accountServiceImpl;
	
	@GetMapping("/welcome")
	public String welcome()
	{
		return "Welcome for New Account";
	}
	
	@PostMapping("/createNewAccount/{userName}")
	public ResponseEntity<Account> createNewAccount(@PathVariable String userName)
	{
		Account accountResponse=accountServiceImpl.createNewAccount(userName);
		return new ResponseEntity<Account>(accountResponse,HttpStatus.CREATED);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber)
	{
		Account accountResponse=accountServiceImpl.getAccountByAccountNumber(accountNumber);
		return new ResponseEntity<Account>(accountResponse,HttpStatus.OK);
	}
	
	@GetMapping("/user/{accountNumber}")
	public ResponseEntity<User> getUserByAccountNumber(@PathVariable String accountNumber)
	{
		User user=accountServiceImpl.getUserByAccountNumber(accountNumber);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<String> deleteAccountByUserNameAndAccountNumber(@RequestBody DelUserAccountDto delUserAccountDto)
	{
		String deleteResponse=accountServiceImpl.deleteAccountByUserNameAndAccountNumber(delUserAccountDto);
		return new ResponseEntity<String>(deleteResponse,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/onlineTransfer")
	public ResponseEntity<String> transferAmount(@RequestBody TransferAmountDto transferAmountDto)
	{
		String res=null;
		try {
			res=accountServiceImpl.transferAmount(transferAmountDto);
		}
		catch(RuntimeException ex)
		{
			res=ex.getMessage();
		}
		
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	@GetMapping("/cashDeposite")
	public ResponseEntity<String> cashDeposite(@RequestBody CashDepositeDto cashDepositeDto)
	{
		String res=null;
		try {
			res=accountServiceImpl.cashDeposite(cashDepositeDto);
		}
		catch(RuntimeException ex)
		{
			res=ex.getMessage();
		}
		
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	@GetMapping("/cashWithdrawal")
	public ResponseEntity<String> withdrawalCash(@RequestBody WithdrawalCashDto withdrawalCashDto)
	{
		String res=null;
		try {
			res=accountServiceImpl.withdrawalCash(withdrawalCashDto);
		}
		catch(RuntimeException ex)
		{
			res=ex.getMessage();
		}
		
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}

}
