package com.Bank.OnlineBanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Bank.OnlineBanking.entity.Account;
import com.Bank.OnlineBanking.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	//Optional<User> findByUsername(String username);
	
	@Query(nativeQuery=true,value="select * from users where username = :userName")
	public Optional<User> getUserByUserName(@Param("userName") String userName);

}
