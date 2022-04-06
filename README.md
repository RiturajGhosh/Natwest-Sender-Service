# Natwest-Sender-Service

To Build this project after cloning from master branch
: maven clean
  maven install
 
then run as a spring boot app to start the service.

API end point : http://localhost:8000/api/v1/sendTransactionData/


samle request payload: 


{
    "Account Number" : "2340",
    
    
    "Type" : "Credit",
    
    
    "Amount" : "324",
    
    
    "Currency" : "INR",
    
    
    "AccountForm" : "234klj"
} 
