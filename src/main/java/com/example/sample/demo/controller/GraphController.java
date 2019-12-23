package com.example.sample.demo.controller;

import com.example.sample.demo.Category;
import com.example.sample.demo.repository.CategoryRepo;
import com.example.sample.demo.repository.ExpenseRepo;
import com.example.sample.demo.repository.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class GraphController {

    @Autowired
    IncomeRepo incomeRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ExpenseRepo expenseRepo;

    @GetMapping("/category")
    public List<Category> getCategory(){
        return categoryRepo.findAll();
    }

    @GetMapping("/showchart")
    public List<Map<Object,Object>> getAmountByCategory(){
        List<Map<Object,Object>> graphdata=expenseRepo.getAmountByCategory();
        return graphdata;
    }

    @GetMapping("/showAmount")
    public List<Map> getTotalAmount() {
        Map<String, String> map = new HashMap<String, String>();
        List<Map> list=new ArrayList<>();
        map.put("Expense", expenseRepo.getTotalAmount().toString());
        map.put("Income", incomeRepo.getTotalAmount().toString());
        map.put("Category", categoryRepo.getTotalAmount().toString());
        list.add(map);
        return list;
    }


}