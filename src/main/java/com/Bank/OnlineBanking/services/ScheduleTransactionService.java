package com.Bank.OnlineBanking.services;

import com.Bank.OnlineBanking.entity.ScheduleTransaction;

public interface ScheduleTransactionService {
	
	public String transferAmountScheduled(ScheduleTransaction scheduleTransaction);
	
	public String cashDepositeScheduled(ScheduleTransaction scheduleTransaction);
	
	public void proccessScheduledTransactions();

}
