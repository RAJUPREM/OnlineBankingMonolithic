package com.Bank.OnlineBanking.servicesImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bank.OnlineBanking.Notification.EmailService;
import com.Bank.OnlineBanking.Notification.SmsService;
import com.Bank.OnlineBanking.dto.AccountDto;
import com.Bank.OnlineBanking.dto.CashDepositeDto;
import com.Bank.OnlineBanking.dto.DelUserAccountDto;
import com.Bank.OnlineBanking.dto.TransferAmountDto;
import com.Bank.OnlineBanking.dto.WithdrawalCashDto;
import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.Transaction;
import com.Bank.OnlineBanking.entity.User;
import com.Bank.OnlineBanking.repository.AccountRepository;
import com.Bank.OnlineBanking.repository.RoleRepository;
import com.Bank.OnlineBanking.repository.TransactionRepository;
import com.Bank.OnlineBanking.repository.UserRepository;
import com.Bank.OnlineBanking.services.AccountService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	EmailService emailService;

	@Override
	public Account createNewAccount(String userName) {
		
		Optional<User> user=userRepository.getUserByUserName(userName);
		
//		User user=new User();
//		user.setAddress(userAccDto.getAddress());
//		user.setPhone(userAccDto.getPhone());
//		
//		Set<Role> rset=new HashSet<Role>();
//		
//		for(String temp:userAccDto.getRoles()) {
//			Role roles=new Role();
//			Optional<Role> tempRole=roleRepository.getRoleByRoleName(temp);
//			
//			if(tempRole.isEmpty())
//			{
//				roles.setName(temp);
//				Role r=roleRepository.save(roles);
//				rset.add(r);
//			}
//			else
//			{
//				rset.add(tempRole.get());
//			}
//			
//		}
//		
//		user.setRoles(rset);
//		user.setUsername(userAccDto.getUserName());
//		user.setEmail(userAccDto.getEmail());
		
		Account account=new Account();
		account.setBalance(1000.00);
		account.setUser(user.get());
		//int accNo=(int) Math.round(Math.floor(Math.random()));
		int accNo=ThreadLocalRandom.current().nextInt();
		account.setAccountNumber("SBI"+user.get().getId()+accNo);
		
		Account acc=accountRepository.save(account);
		return acc;
	}

	@Override
	public Account getAccountByAccountNumber(String accountNumber) {
		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(accountNumber);
		return tempAccount.get();
	}

	@Override
	public User getUserByAccountNumber(String accountNumber) {
		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(accountNumber);
		return tempAccount.get().getUser();
	}

	@Override
	public Account updateAccountDetails(AccountDto accountDto) {
		
		return null;
	}

	@Override
	public String deleteAccountByUserNameAndAccountNumber(DelUserAccountDto delUserAccountDto) {
		Optional<User> tempUser=userRepository.getUserByUserName(delUserAccountDto.getUserName());
		Set<Account> laccounts=tempUser.get().getAccounts();
		
		Optional<Account> accDel=laccounts.stream().filter(x->x.getAccountNumber().equals(delUserAccountDto.getAccountNumber())).findFirst();
		accountRepository.deleteById(accDel.get().getId());
		
		return "Account "+accDel.get().getAccountNumber()+" has deleted Successfully";
	}

	@Override
	@Transactional
	public String transferAmount(TransferAmountDto transferAmountDto) throws RuntimeException {
		
		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(transferAmountDto.getSourceAccountNumber());
		
		Transaction newTransaction=null;
		Account sourceAccount=null;
		Account destinationAccount=null;
		
		try {
			sourceAccount=tempAccount.get();
		}catch(RuntimeException ex)
		{
			//return "Source Account is not available";
			throw new RuntimeException("Source Account "+transferAmountDto.getSourceAccountNumber()+" is not available");
		}
		
		Optional<Account> tempAccount2=accountRepository.getAccountByAccountNumber(transferAmountDto.getDestinationAccountNumber());
		
		try {
			destinationAccount=tempAccount2.get();
		}catch(RuntimeException ex)
		{
			//return "Destination Account is not available";
			throw new RuntimeException("Destination Account "+transferAmountDto.getDestinationAccountNumber()+" is not available");
		}
		
		if(sourceAccount.getBalance()>=transferAmountDto.getAmount())
		{
			sourceAccount.setBalance(sourceAccount.getBalance()-transferAmountDto.getAmount());
			destinationAccount.setBalance(destinationAccount.getBalance()+transferAmountDto.getAmount());
			
			accountRepository.save(sourceAccount);
			accountRepository.save(destinationAccount);
			
			newTransaction=new Transaction();
			
			newTransaction.setFromAccount(sourceAccount);
			newTransaction.setToAccount(destinationAccount);
			newTransaction.setAmount(transferAmountDto.getAmount());
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	        LocalDateTime now = LocalDateTime.now();  
			newTransaction.setTransactionDate(dtf.format(now));
			
			newTransaction.setType("TRANSFER");
			newTransaction.setStatus("COMPLETED");
			
			transactionRepository.save(newTransaction);
			
			// Send alerts
	        String message = newTransaction.toString();

//	        // Send email
//	        emailService.sendEmail(
//	        		sourceAccount.getUser().getEmail(),
//	                "Transaction Alert",
//	                message
//	        );

	        // Send SMS
	        smsService.sendSms(
	        		"+910"+sourceAccount.getUser().getPhone(),
	                message
	        );
			
			return "Transaction Successfull";
		}
		else
		{
			//return "Insufficient Balance";
			throw new RuntimeException("Insufficient Balance in source account : "+transferAmountDto.getSourceAccountNumber());
		}
	}

	@Override
	@Transactional
	public String withdrawalCash(WithdrawalCashDto withdrawalCashDto) {
		
		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(withdrawalCashDto.getSourceAccountNumber());
		
		Transaction newTransaction=null;
		Account sourceAccount=null;
		
		try {
			sourceAccount=tempAccount.get();
		}catch(RuntimeException ex)
		{
			//return "Source Account is not available";
			throw new RuntimeException("Source Account "+withdrawalCashDto.getSourceAccountNumber()+" is not available");
		}
		
		if(sourceAccount.getBalance()>=withdrawalCashDto.getAmount())
		{
		sourceAccount.setBalance(sourceAccount.getBalance()-withdrawalCashDto.getAmount());
		accountRepository.save(sourceAccount);
		
//		Optional<Account> tempAcc=accountRepository.getAccountByAccountNumber("sbi");
//		
//		Account accre=null;
//		
//		if(tempAcc.isEmpty())
//		{
//			Account acc=new Account();
//			acc.setAccountNumber("SBI");
//			acc.setBalance(0.0);
//			acc.setUser(new User());
//			accre=accountRepository.save(acc);	
//		}
//		else
//		{
//			accre=tempAcc.get();
//		}
		
		newTransaction=new Transaction();
		
		newTransaction.setFromAccount(sourceAccount);
		newTransaction.setToAccount(sourceAccount);
		newTransaction.setAmount(withdrawalCashDto.getAmount());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
		newTransaction.setTransactionDate(dtf.format(now));
		
		newTransaction.setType("WITHDRAWAL");
		newTransaction.setStatus("COMPLETED");
		
		transactionRepository.save(newTransaction);
		
		// Send alerts
        String message = newTransaction.toString();

//        // Send email
//        emailService.sendEmail(
//        		sourceAccount.getUser().getEmail(),
//                "Transaction Alert",
//                message
//        );

        // Send SMS
        smsService.sendSms(
        		"+910"+sourceAccount.getUser().getPhone(),
                message
        );
		
		return "Withdrawal Successfully";
		}
		else
		{
			//return "Insufficient Balance";
			throw new RuntimeException("Insufficient Balance in source account : "+withdrawalCashDto.getSourceAccountNumber());
		}
	}

	@Override
	@Transactional
	public String cashDeposite(CashDepositeDto cashDepositeDto) {
		
		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(cashDepositeDto.getSourceAccountNumber());
		
		Transaction newTransaction=null;
		Account sourceAccount=null;
		
		try {
			sourceAccount=tempAccount.get();
		}catch(RuntimeException ex)
		{
			//return "Source Account is not available";
			throw new RuntimeException("Source Account "+cashDepositeDto.getSourceAccountNumber()+" is not available");
		}
		
		sourceAccount.setBalance(sourceAccount.getBalance()+cashDepositeDto.getAmount());
		accountRepository.save(sourceAccount);
		
//		Optional<Account> tempAcc=accountRepository.getAccountByAccountNumber("sbi");
//		
//		Account accre=null;
//		
//		if(tempAcc.isEmpty())
//		{
//			Account acc=new Account();
//			acc.setAccountNumber("SBI");
//			acc.setBalance(0.0);
//			acc.setUser(new User());
//			accre=accountRepository.save(acc);	
//		}
//		else
//		{
//			accre=tempAcc.get();
//		}
		
		newTransaction=new Transaction();
		
		newTransaction.setFromAccount(sourceAccount);
		newTransaction.setToAccount(sourceAccount);
		newTransaction.setAmount(cashDepositeDto.getAmount());
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
		newTransaction.setTransactionDate(dtf.format(now));
		
		newTransaction.setType("DIPOSITED");
		newTransaction.setStatus("COMPLETED");
		
		transactionRepository.save(newTransaction);
		
		// Send alerts
        String message = newTransaction.toString();

//        // Send email
//        emailService.sendEmail(
//        		sourceAccount.getUser().getEmail(),
//                "Transaction Alert",
//                message
//        );

        // Send SMS
        smsService.sendSms(
        		"+910"+sourceAccount.getUser().getPhone(),
                message
        );
		
		return "Deposited Successfull";
	}

}
