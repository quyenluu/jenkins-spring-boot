package com.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblProduct")
public class Product {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO) //auto-increment
    @SequenceGenerator(
        name = "product_sequence",
        sequenceName = "product_sequence",
        allocationSize = 1 //mỗi lần tăng 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "product_sequence"
    )
    private Long id;

    //validate = constraint
    @Column(nullable = false, unique = true, length = 3000)
    private String productName;
    
    private int years;
    private Double price;
    private String url; 

    //default construstor
    public Product() {}

    public Product(String productName, int years, Double price, String url) {
        this.productName = productName;
        this.years = years;
        this.price = price;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", productName=" + productName + ", year=" + years + ", price=" + price
                + ", url=" + url + "]";
    }

}
