package com.example.finance_tracker.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "_expenses_tracker")
@Data
public class ExpensesTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "expenses_id")
    private int id;

    @Column(name = "expenses_description")
    private String description;

    @Column(name = "expenses_category")
    private String category;

    @Column(name = "expenses_date")
    private Date date;

    @Column(name = "expenses_amount")
    private double amount;

    @Column(name = "expenses_payment_method")
    private String paymentMethod;

    @Column(name = "expenses_remarks")
    private String remarks;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public String getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }
}

