package com.phucx.phucxfoodshop.service.user;

import java.util.List;
import org.springframework.data.domain.Page;

import com.phucx.phucxfoodshop.exceptions.UserAuthenticationException;
import com.phucx.phucxfoodshop.exceptions.UserNotFoundException;
import com.phucx.phucxfoodshop.model.User;
import com.phucx.phucxfoodshop.model.UserDetails;
import com.phucx.phucxfoodshop.model.UserRegisterInfo;
import com.phucx.phucxfoodshop.model.UsernamePassword;

public interface UserService {
    // get user
    public User getUserByCustomerID(String customerID);
    public User getUserByEmployeeID(String employeeID);
    public User getUser(String username) throws UserNotFoundException;
    public User getUserById(String userId) throws UserNotFoundException;
    public User getUserByEmail(String email);

    public Page<UserDetails> getUsersByRole(String rolename, Integer pageNumber);
    public Page<UserDetails> getUsersByRoleAndUsernameLike(String rolename, String username, Integer pageNumber);
    public Page<UserDetails> getUsersByRoleAndFirstNameLike(String rolename, String firstName, Integer pageNumber);
    public Page<UserDetails> getUsersByRoleAndLastNameLike(String rolename, String lastName, Integer pageNumber);
    public Page<UserDetails> getUsersByRoleAndEmailLike(String rolename, String email, Integer pageNumber);

    public List<String> getUserRoles(String username);
    // check authentication
    public Boolean checkPassword(String hashedPassword, String rawPassword);
    public Boolean checkUserAuthentication(UsernamePassword usernamePassword);
    // update information
    public Boolean updateEmailVerification(String username, Boolean status);
    public Boolean updateUserPassword(String userID, String password);
    // register 
    public Boolean registerCustomer(UserRegisterInfo userRegisterInfo) throws UserAuthenticationException;
    public Boolean registerEmployee(UserRegisterInfo userRegisterInfo) throws UserAuthenticationException;

}
