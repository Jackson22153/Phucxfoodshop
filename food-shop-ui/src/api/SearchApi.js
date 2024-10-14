import axios from "axios";
import { 
    CategoriesIDUrl, CategoriesNameUrl, CategoriesUrl, ProductsByCategory, 
    ProductsByIdUrl, ProductsByNameUrl, ProductsUrl, RecommendedProductsByCategoryUrl, 
    RecommendedProductsUrl, SearchProductsUrl
} from '../constant/FoodShoppingApiURL';
import { convertNameForUrl } from "../service/Convert";

// categories
// get
export async function getCategories(pageNumber){
    return axios.get(`${CategoriesUrl}?page=${pageNumber}`, {
        'Content-Type': 'application/json'
    });
}
export async function getCategory(categoryName){
    return axios.get(`${CategoriesNameUrl}/${categoryName}`, {
        'Content-Type': 'application/json'
    });
}
export async function getCategoryByID(categoryID){
    return axios.get(`${CategoriesIDUrl}/${categoryID}`, {
        'Content-Type': 'application/json'
    });
}

// products
// get
export async function searchProducts(productName, page){
    const url = SearchProductsUrl;
    return axios.get(`${url}?l=${productName}&page=${page}`,{
        'Content-Type': 'application/json'
    });
}
export async function getProductsByCategory(categoryName, pageNumber){
    const url = ProductsByCategory(categoryName);
    return axios.get(`${url}?page=${pageNumber}`,{
        'Content-Type': 'application/json'
    });
}
export async function getProducts(page){
    return axios.get(`${ProductsUrl}?page=${page}`, {
        'Content-Type': 'application/json'
    })
}
export async function getProductsByProductName(productName, page){
    return axios.get(`${ProductsByNameUrl}?l=${productName}&page=${page}`,{
        'Content-Type': 'application/json'
    })
}
export async function getProductByID(productID){
    return axios.get(`${ProductsByIdUrl}/${productID}`,{
        'Content-Type': 'application/json'
    })
}
export async function getRecommendedProduct(){
    return axios.get(`${RecommendedProductsUrl}`,{
        'Content-Type': 'application/json',
    })
}
export async function getRecommendedProductsByCategory(categoryName, productID, page){
    const convertedCategory = convertNameForUrl(categoryName)
    return axios.get(`${RecommendedProductsByCategoryUrl}/${convertedCategory}?page=${page}&productID=${productID}`,{
        'Content-Type': 'application/json',
    })
}
