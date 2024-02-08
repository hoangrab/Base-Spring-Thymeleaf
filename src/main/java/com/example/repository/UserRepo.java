package com.example.repository;

import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u where u.email = :email")
    User checkemai(@Param("email") String email);

    @Query("SELECT u from User u where concat(u.id,' ',u.email,' ',u.lastName, ' ', u.firstName) like %?1%")
    Page findAll(String key,Pageable pageable);
}
