package com.example.fakeshop;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.RoleRepo;
import com.example.repository.UserRepo;
import com.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserTest {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;




    @Test
    void taouser() {
        Role r1 = new Role(3);
        Role r2 = new Role(5);
        User user1 = new User("hoanch2@gmail.com","123233");
        user1.addRole(r1);
        user1.addRole(r2);
        userRepo.save(user1);
        Assertions.assertTrue(user1.getId() > 0);
    }

    @Test
    void taoRole() {
        Role role1 = Role.builder()
                .name("Admin")
                .description("manage everything")
                .build();
        Role role2 = Role.builder()
                .name("Salesperson")
                .description("manage product price, customers, shipping, orders and sales report")
                .build();
        Role role3 = Role.builder()
                .name("Editor")
                .description("manage categories, brands, products, articles and menus")
                .build();

        Role role4 = Role.builder()
                .name("Shipper")
                .description("view products, view orders and update order status")
                .build();
        Role role5 = Role.builder()
                .name("Assistant")
                .description("manage questions and reviews")
                .build();
        roleRepo.saveAll(List.of(role1,role2,role3,role4,role5));
        Assertions.assertTrue(role1.getId() > 0 && role2.getId() > 0);
    }

    @Test
    void layUser() {
        List<User> list = userRepo.findAll();
//        list.forEach(e -> System.out.println(e.getRoles()));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    void Paginationvip() {
        int numberPage = 0;
        int sizePage = 4;

        Pageable pageable = PageRequest.of(numberPage,sizePage);

        Page page= userRepo.findAll("Nam",pageable);


        List<User> list = page.getContent();

        list.forEach(e -> {
            System.out.println(e.getId() + "\t" + e.getEmail());
        });



        Assertions.assertTrue(list.size() > 0);

    }
}
