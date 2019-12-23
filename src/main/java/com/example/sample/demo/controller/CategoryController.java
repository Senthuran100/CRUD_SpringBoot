package com.example.sample.demo.controller;

import com.example.sample.demo.Category;
import com.example.sample.demo.exception.ResourceNotFoundException;
import com.example.sample.demo.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/catagory")
public class CategoryController {
    @Autowired
    CategoryRepo categoryRepo;


    @GetMapping("/categories")
    public String getAllCategory(Model model) {
        model.addAttribute("categories",categoryRepo.findAll());
        return "index";
    }

    @PostMapping("/categories")
    public String createCategory(@Valid Category category, BindingResult result, Model model) {

        categoryRepo.save(category);
        return "redirect:categories";
    }

    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable(value = "id") Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catgegory", "id", id));
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable(value = "id") Long id, @Valid  Category category,BindingResult result,Model model) {

        if (result.hasErrors()) {
            category.setId(id);
            return "updateCategory";
        }
       categoryRepo.save(category);
       model.addAttribute("categories",categoryRepo.findAll());
       return "index";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("category", category);
        return "updateCategory";
    }

    @PostMapping ("/delete")
    public String deleteCategory(@RequestParam Long id,Model model) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catgegory", "id", id));
        categoryRepo.delete(category);
        return "redirect:categories";
    }

    @GetMapping("/addCategory")
    public String addCategory(Category category){
        return "addCategory";
    }

}
