package com.phucx.phucxfoodshop.service.customer.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phucx.phucxfoodshop.constant.NotificationStatus;
import com.phucx.phucxfoodshop.constant.NotificationTitle;
import com.phucx.phucxfoodshop.constant.NotificationTopic;
import com.phucx.phucxfoodshop.exceptions.CustomerNotFoundException;
import com.phucx.phucxfoodshop.model.CustomerAdminDetails;
import com.phucx.phucxfoodshop.model.CustomerAdminDetailsBuilder;
import com.phucx.phucxfoodshop.model.CustomerDetail;
import com.phucx.phucxfoodshop.model.User;
import com.phucx.phucxfoodshop.model.UserNotificationDTO;
import com.phucx.phucxfoodshop.model.UserVerification;
import com.phucx.phucxfoodshop.model.VerificationInfo;
import com.phucx.phucxfoodshop.repository.CustomerDetailRepository;
import com.phucx.phucxfoodshop.service.customer.CustomerAdminService;
import com.phucx.phucxfoodshop.service.image.CustomerImageService;
import com.phucx.phucxfoodshop.service.notification.SendUserNotificationService;
import com.phucx.phucxfoodshop.service.user.UserProfileService;
import com.phucx.phucxfoodshop.service.user.UserService;
import com.phucx.phucxfoodshop.utils.ImageUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerAdminServiceImp implements CustomerAdminService{
    @Autowired
    private CustomerDetailRepository customerDetailRepository;
    @Autowired
    private CustomerImageService customerImageService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserService userService;
    @Autowired
    private SendUserNotificationService sendUserNotificationService;

    @Override
    public CustomerAdminDetails updateAdminCustomerInfo(CustomerAdminDetails customer) {
        log.info("updateAdminCustomerInfo({})", customer);
        CustomerDetail fetchedCustomer = customerDetailRepository.findById(customer.getCustomerID())
            .orElseThrow(
                ()-> new CustomerNotFoundException("Customer " + customer.getCustomerID() + " does not found")
            );
            // get image
        String picture = ImageUtils.getImageName(customer.getPicture());
        // update Customer's information
        Boolean status = customerDetailRepository.updateAdminCustomerInfo(
            customer.getCustomerID(), customer.getContactName(), customer.getAddress(),
            customer.getCity(), customer.getDistrict(), customer.getWard(),
            customer.getPhone(), picture, customer.getEnabled()
        );
        // set Customer picture
        String customerPicture = picture!=null?customerImageService.getImageUrl(picture):null;
        customer.setPicture(customerPicture);
        // create a notification
        UserNotificationDTO notification = new UserNotificationDTO();
        notification.setTitle(NotificationTitle.USER_INFO_UPDATE);
        notification.setTopic(NotificationTopic.Account);
        notification.setUserID(fetchedCustomer.getUserID());
        notification.setPicture(customer.getPicture());
        if(status){
            notification.setMessage("Your information has been updated by Admin");
            notification.setStatus(NotificationStatus.SUCCESSFUL);
            notification.setReceiverID(fetchedCustomer.getUserID());
        }else{
            notification.setMessage("Error: Your information can not be updated by Admin");
            notification.setStatus(NotificationStatus.ERROR);
            notification.setReceiverID(fetchedCustomer.getUserID());
        }
        // send notification
        this.sendUserNotificationService.sendCustomerNotification(notification);
        if(!status) throw new RuntimeException("Customer " + customer.getCustomerID() + " can not be updated!");

        return customer;
    }

    @Override
    public CustomerAdminDetails getCustomerAdminDetails(String userID) {
        log.info("getCustomerAdminDetails(userID={})", userID);
        // get customer details
        CustomerDetail customer =customerDetailRepository.findByUserID(userID).orElseThrow(
            ()-> new CustomerNotFoundException("Customer with userId: " + userID + " does not found")
        );
        // get user verification
        UserVerification userVerification = userProfileService.getUserVerification(userID);
        VerificationInfo verificationInfo = new VerificationInfo(
            userVerification.getPhoneVerification(), 
            userVerification.getProfileVerification());
        // fetch user
        User fetchedUser = userService.getUserById(userID);

        String picture = customer.getPicture()!=null?
            customerImageService.getImageUrl(customer.getPicture()):null;

        return new CustomerAdminDetailsBuilder()
            .withCustomerID(customer.getCustomerID())
            .withContactName(customer.getContactName())
            .withAddress(customer.getAddress())
            .withCity(customer.getCity())
            .withDistrict(customer.getDistrict())
            .withWard(customer.getWard())
            .withPhone(customer.getPhone())
            .withPicture(picture)
            .withUserID(fetchedUser.getUserID())
            .withUsername(fetchedUser.getUsername())
            .withFirstName(fetchedUser.getFirstName())
            .withLastName(fetchedUser.getLastName())
            .withEmail(fetchedUser.getEmail())
            .withPhoneVerified(verificationInfo.getPhoneVerified())
            .withProfileVerified(verificationInfo.getProfileVerified())
            .withEmailVerified(fetchedUser.getEmailVerified())
            .withEnabled(fetchedUser.getEnabled())
            .build();
    }
    
}
