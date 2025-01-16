package com.Bank.OnlineBanking.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bank.OnlineBanking.dto.RoleDto;
import com.Bank.OnlineBanking.entity.Role;
import com.Bank.OnlineBanking.entity.User;
import com.Bank.OnlineBanking.repository.RoleRepository;
import com.Bank.OnlineBanking.repository.UserRepository;
import com.Bank.OnlineBanking.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Role createNewRole(Role role) {
		Role r=roleRepository.save(role);
		return r;
	}

	@Override
	public String deleteRoleByRoleName(String roleName) {
		roleRepository.getRoleByRoleName(roleName);
		return "Deleted Successfully";
	}

	@Override
	public Role updateRole(RoleDto role) {
		
		return null;
	}

	@Override
	public List<Role> getAllRoles() {
		List<Role> lroles=roleRepository.findAll();
		return lroles;
	}

	@Override
	public Role getRoleByRoleName(String roleName) {
		 Optional<Role> tempRole=roleRepository.getRoleByRoleName(roleName);
		return tempRole.get();
	}

	@Override
	public Role getRoleByUserName(String userName) {
		Optional<User> tempUser=userRepository.getUserByUserName(userName);
		Optional<Role> tempRole=roleRepository.getRoleByUserName(tempUser.get().getId());
		return tempRole.get();
	}

}
