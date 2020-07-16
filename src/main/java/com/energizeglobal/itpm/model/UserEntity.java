package com.energizeglobal.itpm.model;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @CreationTimestamp
    @Column(name = "registration_time", updatable = false)
    private LocalDate registrationDate;

    @Column(name = "email", unique = true,updatable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "publisher")
    private List<ProjectEntity> items = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive = false;

    //TODO add roles lastly
}
