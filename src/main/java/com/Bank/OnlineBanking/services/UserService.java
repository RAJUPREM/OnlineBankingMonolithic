package com.Bank.OnlineBanking.services;

import com.Bank.OnlineBanking.dto.UserDto;
import com.Bank.OnlineBanking.entity.User;

public interface UserService {
	
	public User createNewUser(UserDto userDto);
	
	public User getUserByUserName(String userName);
	
	public String deleteUserByUserName(String userName);
	
	public User updateUser(UserDto userDto);

}
