LOCALHOST FOR ALL SERVERS:
1. ACCOUNT-SERVICE: http://localhost:8000
2. CUSTOMER-SERVICE: http://localhost:9000
3. API-GATEWAY: http://localhost:8080
4. EUREKA-SERVER: http://localhost:8761

Customer API:
1. POST:Add-Customer -> Adding customer details without the necessity of creating an account.
   http://localhost:8080/customer-service/add-customer
   * DOB/MOB length should be 10 else it wil throw an error message ""DOB/MOB LENGTH SHOULD BE 10".
JSON Request Body:
{
    <!-- "custId":1, --> //customer-ID is not mandatory, it will automatically increment the value by 1.
    "name":"Sarthak Arya",
    "gender":"Male",
    "dob":"27-09-1999",
    "city":"Jaipur",
    "mobileNo":"0909090909",
    "accType":"S"
}

2. PUT:Update-Customer -> Customer-ID is mandatory.
   http://localhost:8080/customer-service/update-customer
   * If the customer ID does not match, it will throw a message "CUSTOMER NOT AVAILABLE WITH THIS ID:1".
   * If custID is 0, it will throw a message "PLEASE ENTER VALID CUSTOMER_ID".
   * DOB/MOB length should be 10 else it wil throw an error message ""DOB/MOB LENGTH SHOULD BE 10".
JSON Request Body:
{
    "custId":1,
    "name":"Sarthak Arya",
    "gender":"Male",
    "dob":"01-01-0001",
    "city":"Rajasthan",
    "mobileNo":"9024903309",
    "accType":"S"
}

3. GET:All-Customers
   http://localhost:8080/customer-service/all-customers

4. GET:Customer-By-ID: 
   http://localhost:8080/customer-service/get-customer?custId=1
   * A message will be thrown if the customer does not exist with custId "CUSTOMER NOT AVAILABLE WITH THIS ID:1".
   * A message will be thrown if the customer ID is 0 "PLEASE ENTER VALID CUSTOMER_ID".

5. DELETE:Delete-Customer-ByID
   http://localhost:8080/customer-service/delete-customer?custId=1
   * A message will be thrown if the customer does not exist with custId "CUSTOMER NOT AVAILABLE WITH THIS ID:1".

   
ACCOUNT API :
1. POST:Add-Account -> Account-ID is mandatory.
   http://localhost:8080/account-service/add-account
   * A message will be thrown if the account ID is 0 and details are null. "PLEASE ENTER ALL ACCOUNT DETAILS ALONG WITH ID".
   * A message will be thrown if an account with the same account ID already exists. "ACCOUNT_ALREADY_EXISTS WITH ACC_ID:=1".
   * If Customer not exist with accID, it will throw a message "CUSTOMER DETAILS ARE NOT PRESENT IN SYSTEM. PLEASE ADD CUSTOMER WITH CUST_ID:=1"
JSON Request Body
{
    "accId":1,
    "accType":"Saving",
    "ifsc":"ABC00001",
    "balance":10000
}

2. GET:Account-Detail
   http://localhost:8080/account-service/get-account?accId=1
   * A message will be thrown if the account does not exist with accId "ACCOUNT DOES NOT EXISTS WITH ACC_ID:=1".

3. PUT:Credit-amount
   http://localhost:8080/account-service/add-amount?accId=1&amount=100
   * A message will be thrown if the account ID is 0 "PLEASE ENTER VALID ACC_ID".
   * A message will be thrown if the account does not exist with accId "ACCOUNT DOES NOT EXISTS WITH ACC_ID:=1".

4. Withdraw Balance
   http://localhost:8080/account-service/withdraw-amount?accId=1&amount=1000
   * A message will be thrown if the account ID is 0 "PLEASE ENTER VALID ACC_ID".
   * A message will be thrown if the account does not exist with accId "ACCOUNT DOES NOT EXISTS WITH ACC_ID:=1".

5. Delete Account
   http://localhost:8080/account/deleteCust/{id}
   * A message will be thrown if the account ID is 0 "PLEASE ENTER VALID ACC_ID".
   * A message will be thrown if the account does not exist with accId "ACCOUNT DOES NOT EXISTS WITH ACC_ID:=1".

Confi-Server Properties : https://github.com/adityabhatnagar29/microservice-assignment-configuration

#SWAGGER URL
1. ACCOUNT : http://localhost:8000/account-service/swagger-ui/index.html
2. CUSTOMER : http://localhost:9000/customer-service/swagger-ui/index.html