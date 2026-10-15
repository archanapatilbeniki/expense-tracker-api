package com.example.expensetracker.controller;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Expense> list(
            @RequestParam(required = false) String category) {
        return category == null || category.isBlank()
                ? service.findAll()
                : service.findByCategory(category);
    }

    @GetMapping("/{id}")
    public Expense get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense create(@Valid @RequestBody Expense expense) {
        return service.create(expense);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id,
                          @Valid @RequestBody Expense expense) {
        return service.update(id, expense);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/summary/monthly")
    public Map<String, Object> monthlySummary(
            @RequestParam int year, @RequestParam int month) {
        BigDecimal total = service.monthlyTotal(year, month);
        return Map.of("year", year, "month", month, "total", total);
    }
}
