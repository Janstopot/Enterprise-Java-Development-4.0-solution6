package com.example.demo.service;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Product;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public Product createProduct(ProductDTO productDTO){
        if(departmentRepository.findById(productDTO.getDepartmentId()).isPresent()){
            Department department = departmentRepository.findById(productDTO.getDepartmentId()).get();
            Product product = new Product(department, productDTO.getName(), productDTO.getQuantity());
            return productRepository.save(product);

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The department provided does not exist");
    }

    public Product decreaseProductStock(Long id, int quantity){
        if(productRepository.findById(id).isPresent()){
            Product product = productRepository.findById(id).get();
            if(product.getQuantity() < quantity) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is not enough stock");
            else {
                product.setQuantity(product.getQuantity() - quantity);
                return productRepository.save(product);

            }

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product does not exist");
    }

    public List<Product> findProductsByDepartment(String departmentName){
        if(departmentRepository.findByName(departmentName).isPresent()){
            Department department = departmentRepository.findByName(departmentName).get();
            return productRepository.findByDepartment(department);
            
        } else if (departmentName.equals(null) || departmentName.equals("")) {
            return productRepository.findAll();
            
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The department specified does not exist");
    }

    public Product findProductById(Long id){
        if(productRepository.findById(id).isPresent()){
            return productRepository.findById(id).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product does not exist");
    }

    public void deleteProduct(Long id){
        if(productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product does not exist");
        }
    }
}
