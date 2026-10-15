package com.example.expensetracker.service;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public Expense findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Expense not found: " + id));
    }

    public Expense create(Expense expense) {
        return repository.save(expense);
    }

    public Expense update(Long id, Expense input) {
        Expense existing = findById(id);
        existing.setTitle(input.getTitle());
        existing.setCategory(input.getCategory());
        existing.setAmount(input.getAmount());
        existing.setExpenseDate(input.getExpenseDate());
        existing.setDescription(input.getDescription());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public List<Expense> findByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    public BigDecimal monthlyTotal(int year, int month) {
        YearMonth period;
        try {
            period = YearMonth.of(year, month);
        } catch (java.time.DateTimeException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid year or month");
        }

        return repository.findByExpenseDateBetween(
                        period.atDay(1), period.atEndOfMonth())
                .stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
