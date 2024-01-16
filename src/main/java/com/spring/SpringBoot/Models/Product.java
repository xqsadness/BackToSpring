package com.spring.SpringBoot.Models;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

//POJO
@Entity
@Table(name="tblProduct")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //validate = constraint
    @Column(nullable = false, unique = true, length = 300)
    private String productName;
    private int productYear;
    private Double price;
    private String urlString;
    private boolean deleted = false;

    //calculated field = transient, not exist in mysql
    @Transient
    private int age; // age calculated from year
    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - productYear;
    }
     // Constructor without age
     public Product(Long id, String productName, int productYear, Double price, String urlString) {
        this.id = id;
        this.productName = productName;
        this.productYear = productYear;
        this.price = price;
        this.urlString = urlString;
    }
}
