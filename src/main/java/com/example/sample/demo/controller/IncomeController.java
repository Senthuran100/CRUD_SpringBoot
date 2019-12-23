package com.example.sample.demo.controller;
import com.example.sample.demo.Income;
import com.example.sample.demo.exception.ResourceNotFoundException;
import com.example.sample.demo.repository.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    IncomeRepo incomeRepo;

    @GetMapping("/addIncome")
    public String addCategory(Income income){
        return "addIncome";
    }

    @GetMapping("/incomes")
    public String getAllIncome(Model model) {
        model.addAttribute("incomes",incomeRepo.findAll());
        return "viewIncome";
    }

    @PostMapping("/incomes")
    public String createIncome(@Valid  Income income, Model model) {
         incomeRepo.save(income);
         return "redirect:incomes";
    }

    @GetMapping("/incomes/{id}")
    public Income getIncomeById(@PathVariable(value = "id") Long id) {
        return incomeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable(value = "id") Long id, @Valid  Income income, BindingResult result, Model model) {

        if (result.hasErrors()) {
            income.setId(id);
            return "updateCategory";
        }
        incomeRepo.save(income);
        model.addAttribute("incomes",incomeRepo.findAll());
        return "viewIncome";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Income income = incomeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("income", income);
        return "updateIncome";
    }

    @PostMapping("/delete")
    public String deleteIncome(@RequestParam Long id) {
        Income note = incomeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));
        incomeRepo.delete(note);
        return "redirect:incomes";
    }

    @GetMapping("/incomes/amount")
    public Double getTotalIncome() {
        return incomeRepo.getTotalAmount();
    }

    @GetMapping("/showchart")
    public String showChart(Income income){
        return "chart";
    }

}
