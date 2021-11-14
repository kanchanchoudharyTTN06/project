package com.ttn.bootcamp.controller;

import com.google.gson.Gson;
import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldDto;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.service.AdminService;
import com.ttn.bootcamp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    private ProductService productService;

    @Autowired
    public AdminController(AdminService adminService, ProductService productService) {
        this.adminService = adminService;
        this.productService = productService;
    }

    @GetMapping("/all/customers")
    public ResponseEntity<Object> getAllCustomers() throws GenericException {
        List<Customer> customer = adminService.getAllCustomers();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/all/sellers")
    public ResponseEntity<Object> getAllSellers() throws GenericException {
        List<Seller> seller = adminService.getAllSellers();
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PatchMapping("/activate/seller")
    public ResponseEntity<Object> activateSellerAccount(@RequestBody Map<String, String> request) throws GenericException {
        String response = adminService.activateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/activate/customer")
    public ResponseEntity<Object> activateCustomerAccount(@RequestBody Map<String, String> request) throws GenericException {
        String response = adminService.activateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/deactivate/seller")
    public ResponseEntity<Object> deActivateSellerAccount(@RequestBody Map<String, String> request) throws GenericException {
        String response = adminService.deActivateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/deactivate/customer")
    public ResponseEntity<Object> deActivateCustomerAccount(@RequestBody Map<String, String> request) throws GenericException {
        String response = adminService.deActivateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add/categorymetadatafield")
    public ResponseEntity<Object> addNewCategoryMetadataField(@Valid @RequestBody CategoryMetadataFieldDto categoryMetadataFieldDto) throws GenericException {
        CategoryMetadataFieldDto categoryMetadataFieldDtos = adminService.addCategoryMetadataField(categoryMetadataFieldDto);
        return new ResponseEntity<>(new Gson().toJson(categoryMetadataFieldDtos), HttpStatus.OK);
    }

    @GetMapping("/all/categorymetadatafields")
    public ResponseEntity<Object> getAllMetadataFields() throws GenericException {
        List<CategoryMetadataField> categoryMetadataFields = adminService.getAllCategoryMetadataFields();
        return new ResponseEntity<>(categoryMetadataFields, HttpStatus.OK);
    }

    @PostMapping("/add/category")
    public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryDto categoryDto) throws GenericException {
        CategoryDto categoryDtos = adminService.addCategory(categoryDto);
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/all/categories")
    public ResponseEntity<Object> getAllCategory() throws GenericException {
        List<CategoryDto> categories = adminService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable("id") long id) throws GenericException {
        CategoryDto category = adminService.getCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/update/category")
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDto categoryDto) throws GenericException {
        CategoryDto categoryDtos = adminService.updateCategory(categoryDto);
        return new ResponseEntity<>(new Gson().toJson(categoryDtos), HttpStatus.OK);
    }

    @PostMapping("/add/category/metadata/fieldvalues")
    public ResponseEntity<Object> addCategoryMetadataFieldValues(@RequestBody CategoryMetadataFieldValuesDto categoryMetadataFieldValuesDto) throws GenericException {
        CategoryMetadataFieldValuesDto response = adminService.addOrUpdateCategoryMetadataFieldValues(categoryMetadataFieldValuesDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/category/metadata/fieldvalues")
    public ResponseEntity<Object> updateCategoryMetadataFieldValues(@RequestBody CategoryMetadataFieldValuesDto categoryMetadataFieldValuesDto) throws GenericException {
        CategoryMetadataFieldValuesDto response = adminService.addOrUpdateCategoryMetadataFieldValues(categoryMetadataFieldValuesDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/activate/product/{id}")
    public ResponseEntity<Object> activateProduct(@PathVariable("id") long id) throws GenericException {
        String response = productService.activateProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
