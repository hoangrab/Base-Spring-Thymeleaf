package com.example.service;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.repository.RoleRepo;
import com.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public static final int sizePage = 4;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    public List<User> listAll() {
        return userRepo.findAll();
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }

    public Page listByPage(int numberPage, String sortField, String sortDirec,String keyword) {
        Sort sort = (sortDirec.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        Pageable page = PageRequest.of(numberPage,sizePage,sort);
        if(keyword != null) return userRepo.findAll(keyword,page);
        return userRepo.findAll(page);
    }

    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

    public boolean checkemail(String em) {
        return userRepo.checkemai(em) != null;
    }

    public User findById(int id) {
        Optional<User> op = userRepo.findById(id);
        return op.orElse(null);
    }

}
