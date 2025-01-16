package com.Bank.OnlineBanking.JwtUtility;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Bank.OnlineBanking.entity.User;
import com.Bank.OnlineBanking.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Fetching user by username: " + username);
         
        CustomUser customUser= new CustomUser( userRepository.getUserByUserName(username)
        		.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
        
        System.out.println("Fetching user by username: " + customUser);
        
        return customUser;

//        System.out.println("Found user: " + user);
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword()
////                user.getRoles().stream()
////                        .map(role -> new SimpleGrantedAuthority(role.getName()))
////                        .collect(Collectors.toList())
//        );
    }

}

