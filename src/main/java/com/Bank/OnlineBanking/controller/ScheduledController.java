package com.Bank.OnlineBanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bank.OnlineBanking.entity.ScheduleTransaction;
import com.Bank.OnlineBanking.services.ScheduleTransactionService;

@RestController
@RequestMapping("/schedule")
public class ScheduledController {
	
	@Autowired
	ScheduleTransactionService scheduleTransactionService;
	
	@GetMapping("/welcome")
	public String welcome()
	{
		return "Welcome Scheduler";
	}
	
	
	@GetMapping("/onlineTransferScheduled")
	public ResponseEntity<String> transferAmountScheduled(@RequestBody ScheduleTransaction scheduleTransaction)
	{
		String res=null;
		try {
			res=scheduleTransactionService.transferAmountScheduled(scheduleTransaction);
		}
		catch(RuntimeException ex)
		{
			res=ex.getMessage();
		}
		
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	@GetMapping("/cashDepositeScheduled")
	public ResponseEntity<String> cashDepositeScheduled(@RequestBody ScheduleTransaction scheduleTransaction)
	{
		String res=null;
		try {
			res=scheduleTransactionService.cashDepositeScheduled(scheduleTransaction);
		}
		catch(RuntimeException ex)
		{
			res=ex.getMessage();
		}
		
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}

}
