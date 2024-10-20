import axios from "axios";
import { CustomerCreditCardUrl, CustomerInfoUrl, CustomerNotificationsUrl, GenerateOTPPhoneUrl, GetShippingServicesUrl, IsCustomerUrl, 
    SendCustomerEmailVerificationUrl, 
    UpdateUserPasswordUrl, UploadCustomerImageUrl, UploadEmployeeImageUrl, VerifyOTPPhoneUrl
} from "../constant/FoodShoppingApiURL";
import { getCsrfTokenFromCookie } from "../service/Cookie";

export function isCustomer(){
    return axios.get(IsCustomerUrl,{
        withCredentials: true,
        headers:{
            "Content-Type": 'application/json',
        }
    });
}
// get user info
export async function updateUserInfo(userInfo){
    return axios.post(CustomerInfoUrl, JSON.stringify(userInfo), {
        withCredentials: true,
        headers:{
            "Content-Type": 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}

// upload user's image
export function uploadUserImage(file){
    const formData = new FormData();
    formData.append('file', file);
    
    return axios.post(UploadCustomerImageUrl, formData, {
        withCredentials: true,
        headers:{
            "Content-Type": "multipart/form-data",
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}

// upload employee's image
export function uploadEmployeeImage(file){
    const formData = new FormData();
    formData.append('file', file);
    
    return axios.post(UploadEmployeeImageUrl, formData, {
        withCredentials: true,
        headers:{
            "Content-Type": "multipart/form-data",
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}

// customer info
export async function getCustomerInfo(){
    return axios.get(CustomerInfoUrl, {
        withCredentials: true,
        headers:{
            "Content-Type": 'application/json',
        }
    })
}

// shipping services
export function getShippingServices(data){
    return axios.post(GetShippingServicesUrl, JSON.stringify(data), {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}
// credit card
// shipping services
export function getCreditCard(){
    return axios.get(CustomerCreditCardUrl, {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        }
    })
}
export function updateCreditCard(data){
    return axios.post(CustomerCreditCardUrl, JSON.stringify(data), {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}
// notifications
export async function getCustomerNotifications(pageNumber){
    return axios.get(`${CustomerNotificationsUrl}?page=${pageNumber}`, {
        withCredentials: true,
        headers:{
            "Content-Type": 'application/json',
        }
    })
}
export async function turnOffCustomerNotification(data){
    return axios.post(`${CustomerNotificationsUrl}`, JSON.stringify(data), {
        withCredentials: true,
        headers:{
            "Content-Type": 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}

// change password
export function UpdateUserPassword(data){
    return axios.post(`${UpdateUserPasswordUrl}`, JSON.stringify(data), {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}
// generate phone otp
export function generateOTPPhoneForUser(phone){
    return axios.post(`${GenerateOTPPhoneUrl}?phone=${phone}`, "", {
        withCredentials: true,
        headers: {
            "Content-Type": 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}

// verify phone otp
export function verifyOTPPhoneForUser(otp, phone){
    return axios.post(`${VerifyOTPPhoneUrl}?otp=${otp}&phone=${phone}`, "", {
        withCredentials: true,
        headers: {
            "Content-Type": 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}

export function sendCustomerEmailVerification(){
    return axios.post(`${SendCustomerEmailVerificationUrl}`, "", {
        withCredentials: true,
        headers: {
            "Content-Type": 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}