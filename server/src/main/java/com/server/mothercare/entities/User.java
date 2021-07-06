package com.server.mothercare.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.server.mothercare.entities.kit.MonitoringDevice;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity(name = "User")
@Table(uniqueConstraints = {@UniqueConstraint(name = "user_username", columnNames = {"username"})})
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "username")
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

    @Column(name = "relation_status")
    private boolean married;

    @Column(name = "status")
    private boolean pregnant;

    @Column(name = "height")
    private float height;

    @Column(name = "weight")
    private float weight;

    @Column(name = "bloodType")
    private String bloodType;

    @Column(name = "last_period")
    private Date lastPeriod;

    @Lob
    @Column(name = "image")
    private byte[] profileImg;

    @Column(name = "confirmed")
    public boolean confirmed;


    @ElementCollection
    @CollectionTable(
            name = "event",
            joinColumns = @JoinColumn(name = "userId")
    )
    private List<Event> events;

    @OneToMany(mappedBy = "user")
    private List<MonitoringDevice> devices;

    public User(User user) {
        this.userId = user.userId;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.username = user.username;
        this.password = user.password;
        this.gender = user.gender;
        this.birthOfDate = user.birthOfDate;
        this.email = user.email;
        this.phone = user.phone;
        this.married = user.married;
        this.pregnant = user.pregnant;
        this.height = user.height;
        this.weight = user.weight;
        this.bloodType = user.bloodType;
        this.lastPeriod = user.lastPeriod;
        this.profileImg = user.profileImg;
        this.confirmed = user.confirmed;
    }

    public User(String firstName, String lastName, String encodedPassword, String username, String email, String phone) {
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
}
