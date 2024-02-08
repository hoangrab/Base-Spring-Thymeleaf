package com.example.service;

import com.example.entity.Category;
import com.example.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    public List<Category> listAll() {
        return categoryRepo.findAll();
    }
}
