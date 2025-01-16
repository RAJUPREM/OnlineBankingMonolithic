package com.Bank.OnlineBanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Bank.OnlineBanking.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
	
	@Query(nativeQuery=true,value="select * from accounts where account_number = :accountNumber")
	public Optional<Account> getAccountByAccountNumber(@Param("accountNumber") String accountNumber);

}
