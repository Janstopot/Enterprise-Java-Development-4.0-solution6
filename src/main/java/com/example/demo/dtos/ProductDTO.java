package com.example.demo.dtos;

public class ProductDTO {

    private String name;
    private int quantity;
    private Long departmentId;

    public ProductDTO(String name, int quantity, Long departmentId) {
        this.name = name;
        this.quantity = quantity;
        this.departmentId = departmentId;
    }

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
