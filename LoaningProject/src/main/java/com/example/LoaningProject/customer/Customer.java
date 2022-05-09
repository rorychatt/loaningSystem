package com.example.LoaningProject.customer;

import com.example.LoaningProject.loan.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Customer {

    private String id;
    private Loan loan;

    public Customer(String id) {
        this.id = id;
    }
}
