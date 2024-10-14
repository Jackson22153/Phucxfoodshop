package com.phucx.phucxfoodshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phucx.phucxfoodshop.exceptions.UserNotFoundException;
import com.phucx.phucxfoodshop.model.CustomerAdminDetails;
import com.phucx.phucxfoodshop.model.CustomerDetail;
import com.phucx.phucxfoodshop.model.EmployeeAdminDetails;
import com.phucx.phucxfoodshop.model.EmployeeDetail;
import com.phucx.phucxfoodshop.model.ResponseFormat;
import com.phucx.phucxfoodshop.model.UserDetails;
import com.phucx.phucxfoodshop.service.customer.CustomerAdminService;
import com.phucx.phucxfoodshop.service.customer.CustomerService;
import com.phucx.phucxfoodshop.service.employee.EmployeeAdminService;
import com.phucx.phucxfoodshop.service.employee.EmployeeService;
import com.phucx.phucxfoodshop.service.user.UserPasswordService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(value = "/account/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeAdminService employeeAdminService;
    @Autowired
    private CustomerAdminService customerAdminService;
    @Autowired
    private UserPasswordService userPasswordService;

    @Operation(summary = "Check user role", 
        tags = {"get", "tutorials", "admin"},
        description = "Check whether a user is admin or not")
    @GetMapping(value = "/isAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseFormat> isAdmin(){
        ResponseFormat format = new ResponseFormat();
        format.setStatus(true);
        return ResponseEntity.ok().body(format);
    }

    @Operation(summary = "Get user by CustomerID", 
        tags = {"get", "tutorials", "admin"})
    @GetMapping(value = "/customers/{customerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDetail> getUserByCustomerID(
        @PathVariable(name = "customerID") String customerID
    ) throws UserNotFoundException{
        CustomerDetail customer = customerService.getCustomerByID(customerID);
        return ResponseEntity.ok().body(customer);
    }

    @Operation(summary = "Get employee by EmployeeID", 
        tags = {"get", "tutorials", "admin"})
    @GetMapping(value = "/employees/{employeeID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDetail> getEmployeeDetail(
        @PathVariable(name = "employeeID") String employeeID
    ) throws UserNotFoundException{
        EmployeeDetail employee = employeeService.getEmployee(employeeID);
        return ResponseEntity.ok().body(employee);
    }
    

    @Operation(summary = "Update employee information", tags = {"post", "tutorials", "admin"})
    @PostMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseFormat> updateEmployeeDetail(
        @RequestBody EmployeeAdminDetails employee
    ){
        EmployeeAdminDetails updatedEmployee = employeeAdminService.updateAdminEmployeeInfo(employee);
        Boolean status = updatedEmployee!=null?true:false;
        return ResponseEntity.ok().body(new ResponseFormat(status));
    }

    @Operation(summary = "Update customer information", tags = {"post", "tutorials", "admin"})
    @PostMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseFormat> updateCustomerDetails(
        @RequestBody CustomerAdminDetails customer
    ){
        CustomerAdminDetails updatedCustomer = customerAdminService.updateAdminCustomerInfo(customer);
        Boolean status = updatedCustomer!=null?true:false;
        return ResponseEntity.ok().body(new ResponseFormat(status));
    }


    @Operation(summary = "Get employees", tags = {"get", "admin"})
    @GetMapping("/employees")
    public ResponseEntity<Page<UserDetails>> getEmployees(
        @RequestParam(name = "page", required = false) Integer pagenumber
    ){
        if(pagenumber==null) pagenumber = 0;
        Page<UserDetails> users = employeeService.getUsers(pagenumber);
        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Get customers", tags = {"get", "admin"})
    @GetMapping("/customers")
    public ResponseEntity<Page<UserDetails>> getCusmoters(
        @RequestParam(name = "page", required = false) Integer pagenumber
    ){
        if(pagenumber==null) pagenumber = 0;
        Page<UserDetails> users = customerService.getUsers(pagenumber);
        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Get customer user by userId", tags = {"get", "tutorials", "admin"})
    @GetMapping(value = "/customers/user/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerAdminDetails> getCustomerUserByUserID(@PathVariable(name = "userID") String userID){
        CustomerAdminDetails customer = customerAdminService.getCustomerAdminDetails(userID);
        return ResponseEntity.ok().body(customer);
    }

    @Operation(summary = "Get employee user by userId", tags = {"get", "tutorials", "admin"})
    @GetMapping(value = "/employees/user/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAdminDetails> getEmployeeUserByUserID(@PathVariable(name = "userID") String userID){
        EmployeeAdminDetails employee = employeeAdminService.getEmployeeAdminDetails(userID);
        return ResponseEntity.ok().body(employee);
    }

    @Operation(summary = "Reset user password", tags = {"post", "tutorials", "admin"})
    @PostMapping(value = "/user/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseFormat> resetUserPassword(@PathVariable(name = "userID") String userID){
        Boolean result = userPasswordService.resetUserPasswordRandom(userID);
        ResponseFormat responseFormat = new ResponseFormat(result);
        return ResponseEntity.ok().body(responseFormat);
    }

}
