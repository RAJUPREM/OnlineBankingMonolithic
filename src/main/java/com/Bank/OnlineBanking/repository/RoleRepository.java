package com.Bank.OnlineBanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Bank.OnlineBanking.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
	
	@Modifying
	@Query(nativeQuery=true,value="delete from roles where name = :roleName")
	public void deleteRoleByRoleName(@Param("roleName") String roleName);
	
	@Query(nativeQuery=true,value="select * from roles where name = :roleName")
	public Optional<Role> getRoleByRoleName(@Param("roleName") String roleName);
	
	@Query(nativeQuery=true,value="select * from roles where user_id = :userId")
	public Optional<Role> getRoleByUserName(@Param("userId") Long userId);

}
