package com.example.LoaningProject.customer;

import com.example.LoaningProject.loan.Loan;
import com.example.LoaningProject.manager.Manager;
import com.example.LoaningProject.statistics.Statistics;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    //This variable is used to determine the interval of time when system should look up stats
    //This was implemented for problem #3
    private int statisticsTime = 60;

    //A way to store all customers. Usually done in separate place, but I had decided to implement this
    //as local storage without implementing a database. Same goes for loans
    private List<Customer> customers = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    //A quick way to see which ids are already registered. Update list whenever new client is added.
    private List<String> customerIds = new ArrayList<>();

    //A way to store managers
    private List<Manager> managers = new ArrayList<>();

    //The similar thing as a list of customer ids, but for managers
    private List<String> managerUsernames = new ArrayList<>();
    private List<Loan> pendingLoanApprovalRequests = new ArrayList<>();

    public List<Customer> getAllCustomers() {
        return customers;
    }
    public List<String> getAllCustomerIds(){ return customerIds; }
    public List<Manager> getAllManagers(){ return managers; }
    public void addCustomer(Customer customer) {
        customers.add(customer);
        customerIds.add(customer.getId());
    }
    public void createLoanApprovalRequest(Loan loan) {
        pendingLoanApprovalRequests.add(loan);
    }
    public void registerManager(String username) {
        managers.add(new Manager(username));
        managerUsernames.add(username);
    }

    public List<Loan> getAllLoans() { return loans; }

    public List<String> getAllManagerUsernames(){ return managerUsernames; }

    //I suppose that basically some other script will output as a POST message with true or false
    //True will call for the update, false will do nothing
    public void makeDecision(String username, String customerId, Boolean decision) {
        if(decision == true) {
            for(Loan loan : pendingLoanApprovalRequests){
                if(loan.getCustomerId().equals(customerId)){
                    if(loan.getApprovers().contains(username)){
                        loans.add(loan);
                        giveLoanToId(loan, customerId);
                    } else {
                        System.out.println("Denied loan to customerId: " + customerId + " as manager with username " + username + " does not exist.");
                    }
                }
            }
        } else {
            System.out.println("Denied loan to customerId: " + customerId);
        }
    }

    //Basically assign a loan
    public void giveLoanToId(Loan loan, String customerId){
        for(Customer customer : customers){
            if(customer.getId().equals(customerId)){
                customer.setLoan(loan);
            }
        }
    }

    public Statistics getStatistics() {

        List<Loan> suitableLoans = new ArrayList<>();
        int currentTimeInSeconds = LocalDateTime.now().toLocalTime().toSecondOfDay();

        for(Loan loan: loans){
            if( currentTimeInSeconds - loan.getIssueTimeInSeconds() <= statisticsTime){
                suitableLoans.add(loan);
            }
        }

        int loanCount = loans.size();
        double loanSum = 0;
        double maxLoanValue = 0;
        double minLoanValue = -1;
        double averageLoanValue = 0;

        for(Loan loan : loans){

            double amount = loan.getLoanAmount();

            if(minLoanValue == -1 || minLoanValue > amount){
                minLoanValue = amount;
            }

            if(maxLoanValue < amount){
                maxLoanValue = amount;
            }

            loanSum += amount;

        }

        averageLoanValue = loanSum / loanCount;

        return new Statistics(loanCount, loanSum, averageLoanValue, maxLoanValue, minLoanValue);
    }

    public void setStatisticsTime(int time) {
        statisticsTime = time;
    }
}
