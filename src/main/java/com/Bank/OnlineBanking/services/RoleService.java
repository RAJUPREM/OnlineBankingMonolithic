package com.Bank.OnlineBanking.services;

import java.util.List;

import com.Bank.OnlineBanking.dto.RoleDto;
import com.Bank.OnlineBanking.entity.Role;

public interface RoleService {
	
	public Role createNewRole(Role role);
	
	public String deleteRoleByRoleName(String roleName);
	
	public Role updateRole(RoleDto role);
	
	public List<Role> getAllRoles();
	
	public Role getRoleByRoleName(String roleName);
	
	public Role getRoleByUserName(String userName);

}
