package com.Bank.OnlineBanking.servicesImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Bank.OnlineBanking.Notification.EmailService;
import com.Bank.OnlineBanking.Notification.SmsService;
import com.Bank.OnlineBanking.constant.Constraints;
import com.Bank.OnlineBanking.dto.CashDepositeDto;
import com.Bank.OnlineBanking.dto.TransferAmountDto;
import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.ScheduleTransaction;
import com.Bank.OnlineBanking.entity.Transaction;
import com.Bank.OnlineBanking.repository.AccountRepository;
import com.Bank.OnlineBanking.repository.RoleRepository;
import com.Bank.OnlineBanking.repository.TransactionRepository;
import com.Bank.OnlineBanking.repository.UserRepository;
import com.Bank.OnlineBanking.services.AccountService;
import com.Bank.OnlineBanking.services.ScheduleTransactionService;
import com.Bank.OnlineBanking.services.TransactionService;

import jakarta.transaction.Transactional;

@Service
public class ScheduleTransactionServiceImpl implements ScheduleTransactionService{
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	TransactionService transactionServiceImpl;
	
	@Autowired
	AccountService accountServiceIml;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	EmailService emailService;

	@Override
	@Transactional
	public String transferAmountScheduled(ScheduleTransaction scheduleTransaction) {

		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(scheduleTransaction.getSourceAccountNumber());
		
		Transaction newTransaction=null;
		Account sourceAccount=null;
		Account destinationAccount=null;
		
		try {
			sourceAccount=tempAccount.get();
		}catch(RuntimeException ex)
		{
			//return "Source Account is not available";
			throw new RuntimeException("Source Account "+scheduleTransaction.getSourceAccountNumber()+" is not available");
		}
		
		Optional<Account> tempAccount2=accountRepository.getAccountByAccountNumber(scheduleTransaction.getDestinationAccountNumber());
		
		try {
			destinationAccount=tempAccount2.get();
		}catch(RuntimeException ex)
		{
			//return "Destination Account is not available";
			throw new RuntimeException("Destination Account "+scheduleTransaction.getDestinationAccountNumber()+" is not available");
		}
		
		if(sourceAccount.getBalance()>=scheduleTransaction.getAmount())
		{
			sourceAccount.setBalance(sourceAccount.getBalance()-scheduleTransaction.getAmount());
			destinationAccount.setBalance(destinationAccount.getBalance()+scheduleTransaction.getAmount());
			
			accountRepository.save(sourceAccount);
			accountRepository.save(destinationAccount);
			
			newTransaction=new Transaction();
			
			newTransaction.setFromAccount(sourceAccount);
			newTransaction.setToAccount(destinationAccount);
			newTransaction.setAmount(scheduleTransaction.getAmount());
			
//			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
//	        LocalDateTime now = LocalDateTime.now();  
			newTransaction.setTransactionDate(scheduleTransaction.getScheduledTime());
			
			newTransaction.setType(Constraints.SCHEDULED_OT);
			newTransaction.setStatus(scheduleTransaction.getStatus());
			
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
			
			return Constraints.TRANS_SCHED_AT+scheduleTransaction.getScheduledTime();
		}
		else
		{
			//return "Insufficient Balance";
			throw new RuntimeException(Constraints.INSUFF_SOURCE_ACC+scheduleTransaction.getSourceAccountNumber());
		}
	}

	@Override
	@Transactional
	public String cashDepositeScheduled(ScheduleTransaction scheduleTransaction) {

		Optional<Account> tempAccount=accountRepository.getAccountByAccountNumber(scheduleTransaction.getSourceAccountNumber());
		
		Transaction newTransaction=null;
		Account sourceAccount=null;
		
		try {
			sourceAccount=tempAccount.get();
		}catch(RuntimeException ex)
		{
			//return "Source Account is not available";
			throw new RuntimeException("Source Account "+scheduleTransaction.getSourceAccountNumber()+" is not available");
		}
		
		sourceAccount.setBalance(sourceAccount.getBalance()+scheduleTransaction.getAmount());
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
		newTransaction.setAmount(scheduleTransaction.getAmount());
		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
//      LocalDateTime now = LocalDateTime.now();  
		newTransaction.setTransactionDate(scheduleTransaction.getScheduledTime());
		
		newTransaction.setType("SCHEDULED-CD");
		newTransaction.setStatus(scheduleTransaction.getStatus());
		
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
		
		return "Deposited Scheduled at :"+scheduleTransaction.getScheduledTime();
	}

	@Override
	@Scheduled(fixedRate = 2000)
	public void proccessScheduledTransactions() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
		Optional<List<Transaction>> tempTransactionsL=transactionRepository.getTransactionsByTransactionStatusAndCurrentTime("PENDING",dtf.format(now));
		
		for(Transaction tempTransaction:tempTransactionsL.get())
		{
			String res="";
			
			if(tempTransaction.getType().equals(Constraints.SCHEDULED_CD))
			{
				CashDepositeDto cashDepositeDto=new CashDepositeDto();
				cashDepositeDto.setSourceAccountNumber(tempTransaction.getFromAccount().getAccountNumber());
				cashDepositeDto.setAmount(tempTransaction.getAmount());
				res=accountServiceIml.cashDeposite(cashDepositeDto);
			}
			else if(tempTransaction.getType().equals(Constraints.SCHEDULED_OT))
			{
				TransferAmountDto transferAmountDto=new TransferAmountDto();
				transferAmountDto.setSourceAccountNumber(tempTransaction.getFromAccount().getAccountNumber());
				transferAmountDto.setDestinationAccountNumber(tempTransaction.getToAccount().getAccountNumber());
				transferAmountDto.setAmount(tempTransaction.getAmount());
				res=accountServiceIml.transferAmount(transferAmountDto);
			}
			
			if(res.equals(Constraints.TRANS_SUCCESS) || res.equals(Constraints.DEPOSIT_SUCCESS))
			{
				tempTransaction.setType(Constraints.TRANSFER_SHEDULED_DONE);
				tempTransaction.setStatus(Constraints.COMPLETED);
				transactionRepository.save(tempTransaction);
				
				// Send alerts
		        String message = tempTransaction.toString();

//		        // Send email
//		        emailService.sendEmail(
//		        		sourceAccount.getUser().getEmail(),
//		                "Transaction Alert",
//		                message
//		        );

		        // Send SMS
		        smsService.sendSms(
		        		Constraints.COUNTRY_CODE+tempTransaction.getFromAccount().getUser().getPhone(),
		                message
		        );
			}
			
		}
		
	}

}
