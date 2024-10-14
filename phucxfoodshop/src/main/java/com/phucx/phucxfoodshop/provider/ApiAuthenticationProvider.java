package com.phucx.phucxfoodshop.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.phucx.phucxfoodshop.model.User;
import com.phucx.phucxfoodshop.model.UsernamePassword;
import com.phucx.phucxfoodshop.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("ApiAuthenticationProvider");
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        Boolean check = userService.checkUserAuthentication(new UsernamePassword(username, password));
        if(!check){
            throw new BadCredentialsException ("Invalid username or password!");
        }
        // check enable of an account
        User user = userService.getUser(username);
        if(!user.getEnabled()){
            throw new DisabledException("Your account " + username + " has been disabled");
        }
        // get user role
        List<String> role = userService.getUserRoles(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            new UsernamePasswordAuthenticationToken(username, null, this.getUserRoles(role));
        return usernamePasswordAuthenticationToken;
	}

    private List<GrantedAuthority> getUserRoles(List<String> roles){
        return roles.stream().map(role -> "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

	@Override
	public boolean supports(Class<?> authentication) {
        log.info("authenticatoin class: {}", authentication.getName());
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}
    
}
