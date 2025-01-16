package com.Bank.OnlineBanking.servicesImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Bank.OnlineBanking.dto.UserDto;
import com.Bank.OnlineBanking.entity.Role;
import com.Bank.OnlineBanking.entity.User;
import com.Bank.OnlineBanking.repository.RoleRepository;
import com.Bank.OnlineBanking.repository.UserRepository;
import com.Bank.OnlineBanking.services.UserService;

@Service	
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createNewUser(UserDto userDto) {
		
		User user=new User();
		user.setPersonName(userDto.getPersonName());
		user.setPersonMotherName(userDto.getPersonMotherName());
		user.setAddress(userDto.getAddress());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		//user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
		user.setPhone(userDto.getPhone());
		
		Set<Role> rset=new HashSet<Role>();
		
		for(String temp:userDto.getRoles()) {
			Role roles=new Role();
			Optional<Role> tempRole=roleRepository.getRoleByRoleName(temp);
			
			if(tempRole.isEmpty())
			{
				roles.setName(temp);
				Role r=roleRepository.save(roles);
				rset.add(r);
			}
			else
			{
				rset.add(tempRole.get());
			}
			
		}
		
		user.setRoles(rset);
		user.setUsername(userDto.getUserName());
		userRepository.save(user);
		return user;
	}

	@Override
	public User getUserByUserName(String userName) {
		Optional<User> tempUser=userRepository.getUserByUserName(userName);
		return tempUser.get();
	}

	@Override
	public String deleteUserByUserName(String userName) {
		Optional<User> tempUser=userRepository.getUserByUserName(userName);
	    userRepository.deleteById(tempUser.get().getId());
	    return "User "+userName+" deleted Successfully";
	}

	@Override
	public User updateUser(UserDto userDto) {
		Optional<User> tempUser=userRepository.getUserByUserName(userDto.getUserName());
		
		if(userDto.getPersonName()!=null)
		{
			tempUser.get().setPersonName(userDto.getPersonName());
		}
		
		if(userDto.getPersonMotherName()!=null)
		{
			tempUser.get().setPersonMotherName(userDto.getPersonMotherName());
		}
		
		if(userDto.getPassword()!=null)
		{
			tempUser.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
			//tempUser.get().setPassword(userDto.getPassword());
			//tempUser.get().setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
		}
		
		if(userDto.getAddress()!=null)
		{
			tempUser.get().setAddress(userDto.getAddress());
		}
		
		if(userDto.getEmail()!=null)
		{
			tempUser.get().setEmail(userDto.getEmail());
		}
		
		if(userDto.getPhone()!=null)
		{
			tempUser.get().setPhone(userDto.getPhone());
		}
		
		if(userDto.getRoles()!=null)
		{
			//tempUser.get().setRoles(userDto.getRoles());
		}
		
		
		userRepository.save(tempUser.get());
		
		return tempUser.get();
	}

}
