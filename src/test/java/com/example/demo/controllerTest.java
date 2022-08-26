package com.example.demo;

import com.example.demo.dtos.ProductDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class controllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Product product;
    Gson gson = new Gson();

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.findAndRegisterModules();
    }



    @Test
    void create_test() throws Exception {
        ProductDTO product = new ProductDTO("New product", 69, 1L);
        String body = gson.toJson(product);
        MvcResult mvcResult = mockMvc.perform(post("/new-product")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("New product"));
    }

    @Test
    void decrease_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch("/product?id=1&quantity=5"))
                .andExpect(status().isAccepted()).andReturn();
        System.out.println("RESULT  " + mvcResult.getResponse().getContentAsString());

        assertTrue(mvcResult.getResponse().getContentAsString().contains("45") );
    }

    @Test
    void descrease_maxStock_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch("/product?id=1&quantity=600"))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println("RESULT  " + mvcResult.getResolvedException());

        assertTrue(mvcResult.getResolvedException().getMessage().contains("There is not enough stock"));
    }

    @Test
    void get_products_department_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/product-department?department=tools"))
                .andExpect(status().isAccepted()).andReturn();
        List<Object> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Object>>(){});

        assertEquals(2, list.size());

    }

    @Test
    void get_product_byId_test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("small shovel"));
    }

    @Test
    void create_department_test() throws Exception {
        Department department = new Department("New department");
        String body = gson.toJson(department);
        MvcResult mvcResult = mockMvc.perform(post("/new-department")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("New department"));
    }

    @Test
    void delete_products_test() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(delete("/product/1"))
                .andExpect(status().isAccepted()).andReturn();
        MvcResult mvcResult2 = mockMvc.perform(delete("/product/1"))
                .andExpect(status().isNotFound()).andReturn();

        assertTrue(mvcResult2.getResolvedException().getMessage().contains("The product does not exist"));
    }


}
