package com.Bank.OnlineBanking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Bank.OnlineBanking.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
	
	@Query(nativeQuery=true,value="select * from transactions where from_account_id = :fromAccountNumberId")
	public Optional<List<Transaction>> getTransactionsByFromAccountNumber(@Param("fromAccountNumberId") Long fromAccountNumberId);
	
	@Query(nativeQuery=true,value="select * from transactions where to_account_id = :toAccountNumberId")
	public Optional<List<Transaction>> getTransactionsByToAccountNumber(@Param("toAccountNumberId") Long toAccountNumberId);
	
	@Query(nativeQuery=true,value="select * from transactions where transaction_date = :date")
	public Optional<List<Transaction>> getTransactionsByTransactionDate(@Param("date") String date);
	
	@Query(nativeQuery=true,value="select * from transactions where type = :type")
	public Optional<List<Transaction>> getTransactionsByTransactionType(@Param("type") String type);
	
	@Query(nativeQuery=true,value="select * from transactions where status = :status")
	public Optional<List<Transaction>> getTransactionsByTransactionStatus(@Param("status") String status);
	
	@Query(nativeQuery=true,value="select * from transactions where status = :status and transaction_date <= :sheduledTime")
	public Optional<List<Transaction>> getTransactionsByTransactionStatusAndCurrentTime(@Param("status") String status,@Param("sheduledTime") String sheduledTime);

}
