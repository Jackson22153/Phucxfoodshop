package com.phucx.phucxfoodshop.service.employee.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phucx.phucxfoodshop.constant.NotificationStatus;
import com.phucx.phucxfoodshop.constant.NotificationTitle;
import com.phucx.phucxfoodshop.constant.NotificationTopic;
import com.phucx.phucxfoodshop.exceptions.EmployeeNotFoundException;
import com.phucx.phucxfoodshop.model.EmployeeAdminDetails;
import com.phucx.phucxfoodshop.model.EmployeeAdminDetailsBuilder;
import com.phucx.phucxfoodshop.model.EmployeeDetail;
import com.phucx.phucxfoodshop.model.User;
import com.phucx.phucxfoodshop.model.UserNotificationDTO;
import com.phucx.phucxfoodshop.repository.EmployeeDetailRepostiory;
import com.phucx.phucxfoodshop.service.employee.EmployeeAdminService;
import com.phucx.phucxfoodshop.service.image.EmployeeImageService;
import com.phucx.phucxfoodshop.service.notification.SendUserNotificationService;
import com.phucx.phucxfoodshop.service.user.UserService;
import com.phucx.phucxfoodshop.utils.ImageUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeAdminServiceImp implements EmployeeAdminService{
    @Autowired
    private EmployeeDetailRepostiory employeeDetailRepostiory;
    @Autowired
    private EmployeeImageService employeeImageService;
    @Autowired
    private SendUserNotificationService sendUserNotificationService;
    @Autowired
    private UserService userService;

    @Override
    public EmployeeAdminDetails updateAdminEmployeeInfo(EmployeeAdminDetails employee) {
        log.info("updateAdminEmployeeInfo({})", employee.toString());
        EmployeeDetail fetchedEmployee = employeeDetailRepostiory.findById(employee.getEmployeeID())
            .orElseThrow(
                ()-> new EmployeeNotFoundException("Employee " + employee.getEmployeeID() + " does not found")
            );
        // get image
        String picture = ImageUtils.getImageName(employee.getPicture());
        // update employee's information
        Boolean status = employeeDetailRepostiory.updateAdminEmployeeInfo(
            fetchedEmployee.getEmployeeID(), employee.getHireDate(),
            employee.getBirthDate(), employee.getAddress(), employee.getCity(),
            employee.getDistrict(), employee.getWard(), employee.getPhone(), 
            employee.getTitle(), picture, employee.getNotes(), employee.getEnabled());
        // set employee picture
        employee.setPicture(picture);
        String employeepicture = picture!=null?employeeImageService.getImageUrl(picture):null;
        employee.setPicture(employeepicture);
        // create a notification
        UserNotificationDTO notification = new UserNotificationDTO();
        notification.setTitle(NotificationTitle.USER_INFO_UPDATE);
        notification.setTopic(NotificationTopic.Account);
        notification.setUserID(fetchedEmployee.getUserID());
        notification.setPicture(employee.getPicture());
        if(status){
            notification.setMessage("Your information has been updated by Admin");
            notification.setStatus(NotificationStatus.SUCCESSFUL);
            notification.setReceiverID(fetchedEmployee.getUserID());
        }else{
            notification.setMessage("Error: Your information can not be updated by Admin");
            notification.setStatus(NotificationStatus.ERROR);
            notification.setReceiverID(fetchedEmployee.getUserID());
        }
        // send notification
        this.sendUserNotificationService.sendEmployeeNotification(notification);
        if(!status) throw new RuntimeException("Employee " + employee.getEmployeeID() + " can not be updated!");

        return employee;
    }

    @Override
    public EmployeeAdminDetails getEmployeeAdminDetails(String userID) {
        log.info("getEmployeeAdminDetails(userID={})", userID);
        User user = userService.getUserById(userID);
        EmployeeDetail employeeDetail = employeeDetailRepostiory.findByUserID(userID).orElseThrow(
            ()-> new EmployeeNotFoundException("Employee with userId " + userID + " does not found!")   
        );
        String picture = employeeDetail.getPicture()!=null?
            employeeImageService.getImageUrl(employeeDetail.getPicture()):null;
        return new EmployeeAdminDetailsBuilder()
            .withEmployeeID(employeeDetail.getEmployeeID())
            .withBirthDate(employeeDetail.getBirthDate())
            .withHireDate(employeeDetail.getHireDate())
            .withPhone(employeeDetail.getPhone())
            .withPicture(picture)
            .withTitle(employeeDetail.getTitle())
            .withAddress(employeeDetail.getAddress())
            .withCity(employeeDetail.getCity())
            .withDistrict(employeeDetail.getDistrict())
            .withWard(employeeDetail.getWard())
            .withNotes(employeeDetail.getNotes())
            .withUserID(user.getUserID())
            .withUsername(user.getUsername())
            .withFirstName(user.getFirstName())
            .withLastName(user.getLastName())
            .withEmail(user.getEmail())
            .withEnabled(user.getEnabled())
            .withEmailVerified(user.getEmailVerified())
            .build();
    }
}
