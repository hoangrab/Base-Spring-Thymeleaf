package com.example.controller.api;

import com.example.entity.User;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiUserController {

    Logger logger = LoggerFactory.getLogger(ApiUserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/checkem")
    public boolean checkemail(@RequestParam("em") String em) {
        logger.error("checkemail daduoc goi");
        return userService.checkemail(em);
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        return userService.listAll();
    }
}
