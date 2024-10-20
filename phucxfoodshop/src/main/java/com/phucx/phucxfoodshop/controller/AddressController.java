package com.phucx.phucxfoodshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phucx.phucxfoodshop.exceptions.NotFoundException;
import com.phucx.phucxfoodshop.model.District;
import com.phucx.phucxfoodshop.model.Location;
import com.phucx.phucxfoodshop.model.Province;
import com.phucx.phucxfoodshop.model.Ward;
import com.phucx.phucxfoodshop.service.shipper.ShippingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {
    @Autowired
    private ShippingService shippingService;

    @GetMapping("/provinces")
    @Operation(summary = "Get provinces", tags = {"user", "address"})
    public ResponseEntity<List<Province>> getProvinces() {
        List<Province> result = shippingService.getProvinces();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/districts")
    @Operation(summary = "Get districts", tags = {"user", "address"})
    public ResponseEntity<List<District>> getDistrict(@RequestParam(name = "provinceId") Integer provinceId) {
        List<District> result = shippingService.getDistricts(provinceId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/wards")
    @Operation(summary = "Get wards", tags = {"user", "address"})
    public ResponseEntity<List<Ward>> getWards(@RequestParam(name = "districtId") Integer districtId) {
        List<Ward> result = shippingService.getWards(districtId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/store")
    @Operation(summary = "Get store location", tags = {"user", "address"})
    public ResponseEntity<Location> getStoreLocation() throws NotFoundException{
        Location location = shippingService.getStoreLocation();
        return ResponseEntity.ok().body(location);
    }

}
