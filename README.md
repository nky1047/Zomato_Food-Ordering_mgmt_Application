
Rest Endpoints

RESTAURANT - Create | Update | Delete
<img width="905" alt="image" src="https://github.com/user-attachments/assets/dd10f3af-30e6-43fe-9f2b-0b75065066d5" />

ORDER - Create | Update
<img width="927" alt="image" src="https://github.com/user-attachments/assets/c58cd6cc-41a6-4c50-8dfa-8efa964304fe" />
CUSTOMER - Create | FetchALLCustomers, 
<img width="986" alt="image" src="https://github.com/user-attachments/assets/fe048209-34d9-4b4a-a93f-4bec648a3468" />
REVIEW - Create | Delete | GetALL
<img width="818" alt="image" src="https://github.com/user-attachments/assets/a8a0d13a-ae0a-4dd2-82f9-946ed11beb95" />



FLOW :

1. Create a Customer, Create a Restaurant.
2. Create an Order with valid Customer Id, Restaurant Id, and Restaurant's Menu Items.
3. Check if Any Order already exists for the Customer for that Restaurant with Status as "PREPARING".
4. else Create Order with Status - "PREPARING", add Order to Customer's OrderList
5. Restaurant/USER can Update Order Status - "Processing" [NEED MORE VALIDATION ]
6. User add Review for Particular Order ID,which Updates Order Status - "COMPLETED"  [MORE VALIDATIONS TO BE ADDED]

Starting MongoDB in Docker
Commands:
* docker pull mongodb/mongodb-community-server:latest
* docker run --name mongodb -p 27017:27017 -d mongodb/mongodb-community-server:latest

For Running the Kafka:
* run the docker-compose.yml File by going into the directory as in Project.

Added Validations Such as: 

* Customer - Create - normal validation for Duplicate Customer with Name.
* Restaurant - Create - normal validation for Duplicate Restaurant with Name.
* REVIEW - Create - normal validation if Customer / Restaurant Present or Not
* Order - Create -->
*         * validate Duplicate Order for a Restaurant by a Customer.
*         * validate if Customer/ Restaurant ID / Menu Items Match with Details in DB
