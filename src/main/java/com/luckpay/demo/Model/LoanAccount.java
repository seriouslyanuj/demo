package com.luckpay.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class LoanAccount {
    @Id
    private String loanAccountNumber;
    private LocalDate dueDate;
    private double emiAmount;
}

