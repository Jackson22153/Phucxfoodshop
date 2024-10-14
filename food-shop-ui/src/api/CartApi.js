import axios from "axios";
import { CartOrderUrl, CartProductUrl, CartProductsUrl, CartUrl } from "../constant/FoodShoppingApiURL";
import { getCsrfTokenFromCookie } from "../service/Cookie";

// update product in cart
export function updateProductInCart(products){
    return axios.post(CartUrl, JSON.stringify(products), {
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}
// add product in cart
export function addProductToCart(products){
    return axios.post(CartProductUrl, JSON.stringify(products), {
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}
// remove product in cart
export function deleteCartProduct(productID){
    return axios.delete(`${CartProductUrl}/${productID}`, {
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}
// remove some products in cart
export function deleteCartProducts(productIDs){
    return axios.post(CartProductsUrl, JSON.stringify(productIDs), {
        withCredentials: true,
        headers:{
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    })
}
// remove all products in cart
export function deleteAllCartProducts(){
    return axios.delete(`${CartUrl}`, {
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfTokenFromCookie()
        }
    });
}
// get number of product in cart
export function getNumberOfCartProducts(){
    return axios.get(`${CartUrl}/${productID}`, {
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
        }
    });
}
// get products from cart
export function getProductsFromCart(){
    return axios.get(CartProductsUrl, {
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
        }
    });
}
// get order of cart for user to checkout
export function getOrder(){
    return axios.get(CartOrderUrl, {
        withCredentials: true,
        headers: {
            'Content-Type': 'application/json',
        }
    });
}