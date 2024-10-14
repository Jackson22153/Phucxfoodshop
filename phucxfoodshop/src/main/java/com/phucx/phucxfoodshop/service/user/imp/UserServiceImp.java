package com.phucx.phucxfoodshop.service.user.imp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phucx.phucxfoodshop.constant.WebConstant;
import com.phucx.phucxfoodshop.exceptions.CustomerNotFoundException;
import com.phucx.phucxfoodshop.exceptions.EmployeeNotFoundException;
import com.phucx.phucxfoodshop.exceptions.UserAuthenticationException;
import com.phucx.phucxfoodshop.exceptions.UserNotFoundException;
import com.phucx.phucxfoodshop.model.User;
import com.phucx.phucxfoodshop.model.UserDetails;
import com.phucx.phucxfoodshop.model.UserRegisterInfo;
import com.phucx.phucxfoodshop.model.UsernamePassword;
import com.phucx.phucxfoodshop.repository.UserDetailsRepository;
import com.phucx.phucxfoodshop.repository.UserRepository;
import com.phucx.phucxfoodshop.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserRepository userRepository;

	@Override
	public Boolean checkUserAuthentication(UsernamePassword usernamePassword) {
        log.info("checkUserAuthentication({})", usernamePassword);

        User fetchedUser = userRepository.findByUsername(usernamePassword.getUsername()).orElseThrow(
            ()-> new BadCredentialsException("Invalid username or password!")
        );
        return passwordEncoder.matches(usernamePassword.getPassword(), fetchedUser.getPassword());
	}
	@Override
	public List<String> getUserRoles(String username) {
        log.info("getUserRoles(username={})", username);
		return userRepository.getUserRoles(username);
	}
	@Override
	public User getUser(String username) throws UserNotFoundException {
        log.info("getUser(username={})", username);
        return userRepository.findByUsername(username)
            .orElseThrow(()-> new UserNotFoundException("User " + username + " does not found!"));
	}
	@Override
	public Boolean registerCustomer(UserRegisterInfo userRegisterInfo) throws UserAuthenticationException {
        log.info("registerCustomer({})", userRegisterInfo);
        String password = userRegisterInfo.getPassword();
        String confirmPassword = userRegisterInfo.getConfirmPassword();
        String username = userRegisterInfo.getUsername();
        String firstname = userRegisterInfo.getFirstname();
        String lastname = userRegisterInfo.getLastname();
        String email = userRegisterInfo.getEmail();
        if(!confirmPassword.equals(password)){
            throw new UserAuthenticationException("Password and confirm password do match!");
        }
        Optional<User> optional = this.userRepository.findByUsername(userRegisterInfo.getUsername());
        if(optional.isPresent()){
            throw new UserAuthenticationException("User " + username + " already exists!");
        }
        Optional<User> emailOptional = this.userRepository.findByEmail(userRegisterInfo.getEmail());
        if(emailOptional.isPresent()){
            throw new UserAuthenticationException("Email " + userRegisterInfo.getEmail() + " already exists!");
        }
        String userID = UUID.randomUUID().toString();
        String customerID = UUID.randomUUID().toString();
        String profileID = UUID.randomUUID().toString();
        String hashedPassword = this.passwordEncoder.encode(password);
        Boolean status = this.userRepository.createCustomerUser(
            userID, customerID, profileID, firstname, lastname, 
            email, username, hashedPassword);
        return status;
	}
    @Override
    public User getUserById(String userId) throws UserNotFoundException {
        log.info("getUserById(userId={})", userId);
        return userRepository.findById(userId).orElseThrow(
            ()-> new UserNotFoundException("User " + userId + " does not found!")
        );
    }

    @Override
    public Boolean updateEmailVerification(String username, Boolean status) {
        log.info("updateEmailVerification(username={}, status={})", username, status);
        return userRepository.updateEmailVerification(username, status);
    }
    @Override
    public Page<UserDetails> getUsersByRole(String rolename, Integer pageNumber) {
        log.info("getUsersByRole(rolename={})", rolename);
        Pageable pageable = PageRequest.of(pageNumber, WebConstant.PAGE_SIZE);
        return userDetailsRepository.findByRoleName(rolename, pageable);
    }
    @Override
    public Boolean updateUserPassword(String userID, String password) {
        log.info("updateUserPassword(userID={})", userID);
        String hashedPassword = passwordEncoder.encode(password);
        return userRepository.updateUserPassword(userID, hashedPassword);
    }
    @Override
    public Boolean checkPassword(String hashedPassword, String rawPassword) {
        log.info("checkPassword()");
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
    @Override
    public User getUserByEmail(String email) {
        log.info("getUserByEmail(email={})", email);
        return  userRepository.findByEmail(email).orElseThrow(
            ()-> new UserNotFoundException("Email " + email + " does not found")
        );
    }
    
    @Override
    public User getUserByCustomerID(String customerID) {
        log.info("getUserByCustomerID(customerID={})", customerID);
        return userRepository.findByCustomerID(customerID).orElseThrow(
            ()-> new CustomerNotFoundException("Customer " + customerID + " does not found!")
        );
    }

    @Override
    public User getUserByEmployeeID(String employeeID) {
        log.info("getUserByEmployeeID(employeeID={})", employeeID);
        return userRepository.findByEmployeeID(employeeID).orElseThrow(
            ()-> new EmployeeNotFoundException("Employee " + employeeID + " does not found!")
        );
    }
}
