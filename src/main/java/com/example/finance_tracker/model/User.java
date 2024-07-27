package com.example.finance_tracker.model;

import com.example.finance_tracker.model.billReminder.BillReminder;
import com.example.finance_tracker.model.employerDetails.EmployerDetails;
import com.example.finance_tracker.model.expensesTracker.ExpensesTracker;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.FetchType.*;

@Entity(name = "_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;

    @Column(unique = true)
    @NotBlank
    private String username;

    @NotNull
    private LocalDate dob;

    @NotBlank
    private String password;

    private boolean deleted = false;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private Collection<ExpensesTracker> expensesTracker;

    @OneToOne(mappedBy = "user")
    private EmployerDetails employerDetails;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Collection<BillReminder> billReminder;
}

