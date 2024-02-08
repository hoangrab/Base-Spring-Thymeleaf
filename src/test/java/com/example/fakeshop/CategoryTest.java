package com.example.fakeshop;

import com.example.entity.Category;
import com.example.repository.CategoryRepo;
import com.example.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryTest {

    @Autowired
    private CategoryRepo categoryRepo;


    @Mock
    private CategoryRepo repo;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void CategoryCreate() {
        Category category = new Category(4);
        Category u1 = new Category("Dell Vostro5502",category);
        categoryRepo.save(u1);
        Assertions.assertTrue(u1.getId() > 0);
    }

    @Test
    void Categoryin() {
        String s = "";
        List<Category> list = categoryRepo.fillByParentNull();
        list.forEach(e -> {
            if(e.getCategoryList() != null) printCate(e,0);
        });
        Assertions.assertTrue(list.size() > 0);
    }

    private void printCate(Category cate, int level) {
        String s = "";
        for(int i = 0; i < level; i++) s += "--";
        System.out.println(s + cate.getName());
        if(cate.getCategoryList().size() > 0) {
            cate.getCategoryList().forEach(e1 -> {
                printCate(e1,level+1);
            });
        }
    }


    @Test
    void LayAll() {
        Mockito.when(repo.findAll()).thenReturn(categoryRepo.findAll());

        List<Category> list = categoryService.listAll();
        System.out.println(list.size());
        Assertions.assertTrue(list.size() > 0);
    }
}
