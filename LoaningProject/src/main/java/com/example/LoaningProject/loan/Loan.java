package com.example.LoaningProject.loan;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor

public class Loan {
    private String customerId;
    private double loanAmount;
    private List<String> approvers;

    private int issueTimeInSeconds;

    public Loan(String customerId, double loanAmount, List<String> approvers) {
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.approvers = approvers;
        this.issueTimeInSeconds = LocalDateTime.now().toLocalTime().toSecondOfDay();
    }
}
