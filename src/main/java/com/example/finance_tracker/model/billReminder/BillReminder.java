package com.example.finance_tracker.model.billReminder;

import com.example.finance_tracker.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity(name = "_bill_reminder")
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class BillReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private int amount;

    @NotNull
    private LocalDate dueDate;

    private String notes;

    @NotNull
    private int occurrence;

    @NotNull
    private int term;

    private boolean isPaid = false;

    private boolean isInstallment = false;

    private int paidCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
