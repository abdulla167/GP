package com.server.mothercare.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "User")
@Data
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @NotNull
    @Column(name = "first_name")
    String firstName;

    @NotNull
    @Column(name = "last_name")
    String lastName;

    @NotNull
    @Column(name = "password")
    String password;

    @NotNull
    @Column(name = "username", unique = true)
    String username;

    @Column(name = "BOD")
    Date birthOfDate;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;


    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.birthOfDate = user.getBirthOfDate();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }

    public User(String firstName, String lastName, String encodedPassword, String username,String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = encodedPassword;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public User(String firstName, String lastName, String encodedPassword, String username, Date birthOfDate, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = encodedPassword;
        this.username = username;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
    }





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
