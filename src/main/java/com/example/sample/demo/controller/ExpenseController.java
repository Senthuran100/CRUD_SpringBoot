package com.example.sample.demo.controller;


import com.example.sample.demo.Expense;
import com.example.sample.demo.exception.ResourceNotFoundException;
import com.example.sample.demo.repository.CategoryRepo;
import com.example.sample.demo.repository.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseRepo expenseRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/expenses")
    public String getExpense(Model model){
        model.addAttribute("expenses",expenseRepo.findAll());
        return "viewExpense";
    }

    @GetMapping("/addexpense")
    public String addExpense(Expense expense,Model model){
        model.addAttribute("categories",categoryRepo.findAll());
        return "addExpense";
    }

    @GetMapping("/category/{categoryid}/expense")
    public List<Expense> getAllExpenseByCategory(@PathVariable(value = "categoryid") Long categoryid) {
        return expenseRepo.findByCategoryId(categoryid);
    }

    @PostMapping("/category")
    public String createExpense( @RequestParam(name = "category.id") Long categoryid,@Valid  Expense expense, BindingResult result) {

         categoryRepo.findById(categoryid).map(expense1 -> {
            expense.setCategory(expense1);
            return expenseRepo.save(expense);
        });
         return "redirect:expenses";

    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("categories",categoryRepo.findAll());
        model.addAttribute("expense", expense);
        return "updateExpense";
    }

    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable(value = "id") Long id, @Valid  Expense expense, BindingResult result, Model model) {

        if (result.hasErrors()) {
            expense.setId(id);
            return "updateExpense";
        }
        expenseRepo.save(expense);
        model.addAttribute("expenses",expenseRepo.findAll());
        return "viewExpense";
    }

    @PutMapping("/category/{categoryid}/expense/{expenseid}")
    public Expense updateExpense(@PathVariable(value = "categoryid") Long categoryid, @PathVariable(value = "expenseid") Long expenseid, @Valid @RequestBody Expense expense) {

        return expenseRepo.findById(expenseid).map(upexpense -> {
            upexpense.setDescription(expense.getDescription());
            upexpense.setName(expense.getName());
            upexpense.setAmount(expense.getAmount());

            return expenseRepo.save(upexpense);
        }).orElseThrow(() -> new ResourceNotFoundException("Income", "id", expenseid));
    }


    @PostMapping("/delete")
    public String deleteExpense(@RequestParam Long id,Model model) {
        Expense note = expenseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));
        expenseRepo.delete(note);
        return "redirect:expenses";
    }

    @GetMapping("/showchart")
    public String getAmountByCategory(Model modelMap){

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Set-Cookie", "HttpOnly;Secure;SameSite=Strict");
        return "chart";
    }

}

