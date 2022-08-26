package com.example.demo.controller;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/new-product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody ProductDTO product){
        return productService.createProduct(product);
    }

    @PatchMapping("/product")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Product decreaseStock(@RequestParam Long id, @RequestParam int quantity){
        return productService.decreaseProductStock(id, quantity);
    }

    @GetMapping("/product-department")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Product> findProductsByDepartment(@RequestParam String department){
        return productService.findProductsByDepartment(department);
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product findProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProductByID(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
