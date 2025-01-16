package com.Bank.OnlineBanking.services;

import com.Bank.OnlineBanking.dto.AccountDto;
import com.Bank.OnlineBanking.dto.CashDepositeDto;
import com.Bank.OnlineBanking.dto.DelUserAccountDto;
import com.Bank.OnlineBanking.dto.TransferAmountDto;
import com.Bank.OnlineBanking.dto.WithdrawalCashDto;
import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.User;

public interface AccountService {
	
	//public Account createNewAccount(UserAccDto userAccDto);
	public Account createNewAccount(String userName);
	
	public Account getAccountByAccountNumber(String accountNumber);
	
	public User getUserByAccountNumber(String accountNumber);
	
	public Account updateAccountDetails(AccountDto accountDto);
	
	public String deleteAccountByUserNameAndAccountNumber(DelUserAccountDto delUserAccountDto);
	
	public String transferAmount(TransferAmountDto transferAmountDto);
	
	public String cashDeposite(CashDepositeDto cashDepositeDto);
	
	public String withdrawalCash(WithdrawalCashDto withdrawalCashDto);

}
