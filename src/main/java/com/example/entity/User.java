package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 45)
    private String photo;

    @Column(length = 45)
    private String firstName;
    @Column(length = 45)
    private String lastName;

    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    List<Role> roleLists= new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public List<String> layRole() {
        List<String> list = new ArrayList<>();
        for (Role role : this.roleLists) {
            list.add(role.getName());
        }
        return list;
    }

    public void addRole(Role role) {
        this.roleLists.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roleLists +
                '}';
    }

    @Transient
    public String getPathPhoto() {
        return (this.photo != null ? "upload-photos/"+this.id+"/"+this.photo : "img/default.png");
    }
}
