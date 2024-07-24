package com.example.finance_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "_employer-details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String employerName;

    private int employerSalary;

    private int employerId;

    private String employerType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
