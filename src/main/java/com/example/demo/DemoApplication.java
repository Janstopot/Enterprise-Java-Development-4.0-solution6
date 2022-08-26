package com.example.demo;

import com.example.demo.model.Department;
import com.example.demo.model.Product;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Department department1 = new Department("tools");
		Department department2 = new Department("edible plants");
		Department department3 = new Department("non-edible plants");
		Department department4 = new Department("edible seeds");
		Department department5 = new Department("non-edible seeds");
		Department department6 = new Department("miscellaneous");
		departmentRepository.saveAll(List.of(department1,department2,department3,department4,department5,department6));

		Product product1 = new Product(department1, "small shovel", 50);
		Product product2 = new Product(department1, "large shovel", 150);
		Product product3 = new Product(department2, "apple tree sapling", 10);
		Product product4 = new Product(department4, "assorted root vegetable seed packet", 2000);
		Product product5 = new Product(department5, "geranium seed packet", 1000);
		Product product6 = new Product(department2, "sprouted carrots", 200);
		Product product7 = new Product(department6, "large brim gardening hat", 25);
		productRepository.saveAll(List.of(product1,product2,product3,product4,product5,product6));

	}
}
