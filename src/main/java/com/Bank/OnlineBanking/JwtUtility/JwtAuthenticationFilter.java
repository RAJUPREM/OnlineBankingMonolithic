package com.Bank.OnlineBanking.JwtUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {

    	String jwtRequestTokenHeader = request.getHeader("Authorization");
        String userName = null;
        String bearerToken = null;
        if(jwtRequestTokenHeader != null && jwtRequestTokenHeader.startsWith("Bearer")) {
            bearerToken = jwtRequestTokenHeader.substring(7);
            try {
                userName = jwtTokenProvider.extractUsername(bearerToken);
            } catch (Exception e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            System.out.println("filter "+userDetails);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtTokenProvider.validateToken(bearerToken, userDetails)) {
                	System.out.println("validated");
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    System.out.println("usernamePasswordAuthenticationToken "+usernamePasswordAuthenticationToken);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    System.out.println("I am good");
                }
            } else {
                System.out.println("Person is not validated");
            }
        }
        filterChain.doFilter(request,response);
        System.out.println("I am good again");
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}


