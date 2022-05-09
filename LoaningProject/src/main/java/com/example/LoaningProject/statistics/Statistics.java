package com.example.LoaningProject.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor

public class Statistics {

    private int count;
    private double sum;
    private double avg;
    private double max;
    private double min;

}
