package com.Bank.OnlineBanking.JwtUtility;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bank.OnlineBanking.dto.UserDto;
import com.Bank.OnlineBanking.entity.AuthRequest;
import com.Bank.OnlineBanking.entity.AuthResponse;
import com.Bank.OnlineBanking.entity.User;
import com.Bank.OnlineBanking.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UserService userServiceImpl;
	
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
       
            System.out.println("Attempting authentication for: " + authRequest.getUsername());
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername()
                        , authRequest.getPassword()));
                if (authentication.isAuthenticated()) {
                    return ResponseEntity.ok(new AuthResponse(jwtTokenProvider.generateToken(authRequest.getUsername()),"Success"));
                } else {
                    throw new Exception("Person is not found sorry !!");
                }
            }
            catch (Exception ex){
                throw new Exception("user name not found");
            }
    
    }
        
    
    @PostMapping("/createDefaultAdmin")
	public ResponseEntity<User> createNewUser(@RequestBody UserDto userDto)
	{
		User userRes=userServiceImpl.createNewUser(userDto);
		return new ResponseEntity<User>(userRes,HttpStatus.CREATED);
	}

}
