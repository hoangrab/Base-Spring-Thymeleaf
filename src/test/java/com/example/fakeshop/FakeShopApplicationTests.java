package com.example.fakeshop;

import com.example.entity.User;
import com.example.repository.UserRepo;
import com.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class FakeShopApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;
    @Test
     void createuser() {
        User user1 = User.builder()
                .id(1)
                .email("hoankui@gmail.com")
                .firstName("hoan")
                .lastName("tran").build();
        userRepo.save(user1);
        Assertions.assertTrue(user1.getId() > 0);
    }

    @Test
    void layUser() {
        List<User> list = userService.listAll();
//        list.forEach(e -> System.out.println(e.getRoles()));
        Assertions.assertTrue(list.size() > 0);
    }

}
