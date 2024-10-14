import axios from "axios";
import { UploadProductImageUrl, UploadCategoryImageUrl } from "../constant/FoodShoppingApiURL";
import { getCsrfTokenFromCookie } from "../service/Cookie";

export function uploadProductImage(file){
    const formData = new FormData();
    formData.append('file', file);
    
    return axios.post(UploadProductImageUrl, formData, {
        withCredentials: true,
        headers:{
            "Content-Type": "multipart/form-data",
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}
export function uploadCategoryImage(file){
    const formData = new FormData();
    formData.append('file', file);
    
    return axios.post(UploadCategoryImageUrl, formData, {
        withCredentials: true,
        headers:{
            "Content-Type": "multipart/form-data",
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}