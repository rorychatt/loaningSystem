package com.example.LoaningProject.loan;

import lombok.Data;

import java.util.List;

@Data

public class LoanApprovalRequest
{
    private String customerId;
    private float loanAmount;
    private List<String> managers;
}
