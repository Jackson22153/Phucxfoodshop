package com.phucx.phucxfoodshop.service.customer;

import com.phucx.phucxfoodshop.model.CustomerAdminDetails;

public interface CustomerAdminService {
    // update employee information for admin
    public CustomerAdminDetails updateAdminCustomerInfo(CustomerAdminDetails customer);
    // get employee
    public CustomerAdminDetails getCustomerAdminDetails(String userID);
}
