package com.example.LoaningProject.customer;

import com.example.LoaningProject.exception.ApiRequestException;
import com.example.LoaningProject.loan.Loan;
import com.example.LoaningProject.loan.LoanApprovalRequest;
import com.example.LoaningProject.manager.Manager;
import com.example.LoaningProject.statistics.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){this.customerService = customerService;}

    //All the methods in here speak for themselves

    @RequestMapping(value= "/get_all_customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers(){return customerService.getAllCustomers();}


    //JSON input
    @RequestMapping(value="/register_customer", method = RequestMethod.POST)
    public void registerCustomer(@RequestBody Customer customer) {
        String customerId = customer.getId();
        if(customerId.length() == 11 && customerId.charAt(2) == '-' && customerId.charAt(7) == '-'){
            customerService.addCustomer(customer);
        } else {
            throw new ApiRequestException("Can not add customer with custom exception: ");
        }
    }

    //JSON input
    @RequestMapping(value="/create_loan_approval_request", method = RequestMethod.POST)
    public void createLoanApprovalRequest(@RequestBody LoanApprovalRequest loanApprovalRequest){

        String customerId = loanApprovalRequest.getCustomerId();
        double loanAmount = loanApprovalRequest.getLoanAmount();
        List<String> managers = loanApprovalRequest.getManagers();
        boolean isBusy = customerService.getAllCustomerIds().contains(customerId);

        if( !isBusy && customerId.length() == 11 && customerId.charAt(2) == '-' &&
                customerId.charAt(7) == '-' && managers.size() <= 3 && loanAmount > 0)
        {
            customerService.createLoanApprovalRequest(new Loan(customerId, loanAmount, managers));
        } else {
            throw new ApiRequestException("Can not create loan approval request: ");
        }
    }

    @RequestMapping(value="/register_manager", method = RequestMethod.POST)
    public void registerManager(@RequestParam() String username){
        customerService.registerManager(username);
    }

    @RequestMapping(value="/get_all_managers", method = RequestMethod.GET)
    public List<Manager> getAllManagers(){ return customerService.getAllManagers(); }

    @RequestMapping(value="/get_all_loans", method = RequestMethod.GET)
    public List<Loan> getAllLoans(){return customerService.getAllLoans();}

    @RequestMapping(value="/make_decision", method = RequestMethod.POST)
    public void makeDecision(@RequestParam() String username,
                             @RequestParam() String customerId,
                             @RequestParam() Boolean decision){
        if(customerService.getAllManagerUsernames().contains(username) && customerService.getAllCustomerIds().contains(customerId)){
            customerService.makeDecision(username, customerId, decision);
        }
    }

    @RequestMapping(value="/get_statistics", method = RequestMethod.GET)
    public Statistics getStatistics(){ return customerService.getStatistics(); }

    @RequestMapping(value="/set_statistics_time", method = RequestMethod.POST)
    public void setStatisticsTime(@RequestParam() int time){
        customerService.setStatisticsTime(time);
    }
}
