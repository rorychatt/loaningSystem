Hello!

examplePOST.txt file contains example GET and POST methods for interacting with server

LoaningProject-0.0.1-SNAPSHOT.jar is main executable

I included also the IntelliJ project for ease of access.

General idea:

--- Customer folder contains all neccessary code for running the server
--- Exception folder contains code for handling exceptions caused by wrong input in queries
--- Pretty much all other folder speak for themselves.

I included commentary in the code at places that are somewhat odd in the IntelliJ project folder (not in .jar)
In general, CustomerController handles POST and GET calls, CustomerService handles the main logic and interacts
with inmemory declaired lists for temporary memory storage

Before loan is assigned to a customer, it is created as a loanApprovalRequest. Once it is accepted by a manager,
that is.

For docker: 

docker build . -t project/loaningproject

docker run -d -p  8080:80 project/loaningproject





