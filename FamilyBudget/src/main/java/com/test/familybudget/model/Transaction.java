package com.test.familybudget.model;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private double amount;
    private int categoryId;
    private String categoryName;
    private LocalDate date;
    private String description;

    public Transaction(int id, double amount,  int categoryId, String categoryName, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getCategoryId() {return categoryId;}

    public String getCategoryName() {return categoryName;}

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
} 