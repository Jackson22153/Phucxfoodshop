package com.phucx.phucxfoodshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phucx.phucxfoodshop.constant.WebConstant;
import com.phucx.phucxfoodshop.model.CurrentProduct;
import com.phucx.phucxfoodshop.service.product.ProductService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/shop/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Search products by name", tags = {"tutorials", "get", "public"})
    @GetMapping("products")
    public ResponseEntity<Page<CurrentProduct>> searchProductsByName(
        @RequestParam(name = "l") String letters,
        @RequestParam(name = "page", required = false) Integer pageNumber
    ) {
        if(letters.length()>2){
            pageNumber = pageNumber!=null?pageNumber:0;
            var productsPageable = productService.searchCurrentProducts(letters, pageNumber, WebConstant.PAGE_SIZE);
            
            return ResponseEntity.ok().body(productsPageable);
        }
        return ResponseEntity.badRequest().build();
    }    

    @Operation(summary = "Get recommended products by category", tags = {"tutorials", "get", "public"})
    @GetMapping("recommended/{categoryName}")
    public ResponseEntity<Page<CurrentProduct>> getRecommendedProductByCategory(
        @PathVariable(name = "categoryName") String categoryName,
        @RequestParam(name = "productID") Integer productID,
        @RequestParam(name = "page", required = false) Integer pageNumber
    ){
        pageNumber = pageNumber!=null?pageNumber:0;
        Page<CurrentProduct> products = productService.getRecommendedProductsByCategory(
            productID, categoryName, pageNumber, WebConstant.RECOMMENDED_PAGE_SIZE);

        return ResponseEntity.ok().body(products);
    }

}
