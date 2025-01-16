package com.Bank.OnlineBanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bank.OnlineBanking.dto.UserDto;
import com.Bank.OnlineBanking.entity.User;
import com.Bank.OnlineBanking.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userServiceImpl;
	
	@GetMapping("/welcome")
	public String welcome()
	{
		return "Welcome Users";
	}
	
	@PostMapping("/createNewUser")
	public ResponseEntity<User> createNewUser(@RequestBody UserDto userDto)
	{
		User userRes=userServiceImpl.createNewUser(userDto);
		return new ResponseEntity<User>(userRes,HttpStatus.CREATED);
	}
	
	@GetMapping("/{userName}")
	public ResponseEntity<User> getUserByUserName(@PathVariable String userName)
	{
		User userRes=userServiceImpl.getUserByUserName(userName);
		return new ResponseEntity<User>(userRes,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{userName}")
	public ResponseEntity<String> deleteUserByUserName(@PathVariable String userName)
	{
		String deletedUser=userServiceImpl.deleteUserByUserName(userName);
		return new ResponseEntity<String>(deletedUser,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody UserDto userDto)
	{
		User userRes=userServiceImpl.updateUser(userDto);
		return new ResponseEntity<User>(userRes,HttpStatus.ACCEPTED);
	}
}
