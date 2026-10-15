package com.example.expensetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String category;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(nullable = false)
    private LocalDate expenseDate;

    @Size(max = 500)
    private String description;

    protected Expense() {
    }

    public Expense(String title, String category, BigDecimal amount,
                   LocalDate expenseDate, String description) {
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public String getDescription() { return description; }

    public void setTitle(String title) { this.title = title; }
    public void setCategory(String category) { this.category = category; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }
    public void setDescription(String description) { this.description = description; }
}
