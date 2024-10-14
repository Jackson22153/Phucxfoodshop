package com.phucx.phucxfoodshop.service.employee;

import com.phucx.phucxfoodshop.model.EmployeeAdminDetails;

public interface EmployeeAdminService {
    // update employee information for admin
    public EmployeeAdminDetails updateAdminEmployeeInfo(EmployeeAdminDetails employee);
    // get employee
    public EmployeeAdminDetails getEmployeeAdminDetails(String userID);
}
