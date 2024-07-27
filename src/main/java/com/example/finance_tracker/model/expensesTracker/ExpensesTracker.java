package com.example.finance_tracker.model.expensesTracker;


import com.example.finance_tracker.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "_expenses_tracker")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    @NotNull
    private Date date;

    @NotNull
    private double amount;

    @NotBlank
    private String paymentMethod;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}

