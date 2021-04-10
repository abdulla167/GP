package com.server.mothercare.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "User")
@Data
@RequiredArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class User implements UserDetails , Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender = "female";

    @Column(name = "BOD")
    private Date birthOfDate;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    String phone;

    @Lob
    @Column(name = "image")
    private byte[] profileImg;

    @Column(name = "confirmed")
    boolean confirmed;


    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.birthOfDate = user.getBirthOfDate();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.confirmed = false;
    }

    public User(String firstName, String lastName, String encodedPassword, String username,String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = encodedPassword;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.confirmed = false;
    }

    public User(String firstName, String lastName, String encodedPassword, String username, Date birthOfDate, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = encodedPassword;
        this.username = username;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
        this.confirmed = false;
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
        return this.confirmed;
    }
}
