package com.example.finance_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.FetchType.*;

@Entity(name = "_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @Column(unique = true)
    private String username;

    private LocalDate dob;

    private String password;

    private boolean deleted = false;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

//    @OneToMany
//    @JoinColumn(name = "user_id")
//    private Collection<ExpensesTracker> expensesTracker;

    @OneToOne(mappedBy = "user")
    private EmployerDetails employerDetails;
}

