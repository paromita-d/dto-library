# dto-library
the objective of this project is
- to show that certain DTOs are common to multiple microservices
- these are the DTOs that work at the boundary of these services and talk to another service
- duplicating these is extra work and every time a change is needed it happnes in multiple places
- we can extract these classes to an external jar (like a dto-core) and version them
- versioning them and making them backward compatible helps reduce congnitive overhead when making changes

## structure:

### initial and complete
this repo showcases the purpose and usefulness of a shared dto-library
- the ```initial``` directory shows how the code looks like when there is no shared library
- the ```complete``` cirectpry shows how the code looks like **AFTER** the shared library has been creted

### maven and gradle
- the different services is a mix of maven and gradle projects
- this showcases that the concept can be applied across different build platforms

## services:
```
                 ---> customer-service --> cust-DB
                |
shop-service --- ---> inventory-service --> inventory-DB
                |           ^
                |           |
                 ---> transaction-service --> txn-DB
```

### inventory-service [maven] (http://localhost:8081/inventory/)
- this service talks to an imaginary DB (in this case in memory data structures)
- it provides endpoints to update inventory in case a purchase or return happens
- an inventory line item is one product (like its id, price and in item count etc)
- http://localhost:8081/inventory/all
    - shows all the items in inventory
- http://localhost:8081/inventory/details/2
    - shows details of item ID 2
- http://localhost:8081/inventory/eligibility/purchase/1/99
    - shows if there is enough stock to buy 99 counts of item 1
- http://localhost:8081/inventory/refund/2/15
    - performs a return of 15 counts of item ID 2 to inventory
    - note here that the purchase from which this return happens is handled by transaction service
    - this call just updates the inventory

### transaction-service [gradle] (http://localhost:8082/transaction/)
- this connects to purchase DB (again imaginary)
- and provides API to keep record of transactions
- like when a customer makes a purchase or returns items
- a customer can purchase many counts of many inventory line items or return
- http://localhost:8082/transaction/purchase?1=10&2=3
    - creates a transaction for a customer to purchase 10 counts of item 1 and 3 of item 2
- http://localhost:8082/transaction/refund/10?1=5
    - creates a refund of 5 counts of item 1 for transaction ID 10

### shop-service [maven] (http://localhost:8083/shop/)
- this is the service that coordinates and works with all other services
- when a customer makes a purchase or return this would call all the 3 services
    - calls customer service to validate if customer is signed up
    - calls inventory service to check if the requested item for purchase is in stock (or return eligible)
    - if eligible, then call transaction service to actually make the purchase/return
- http://localhost:8083/shop/purchase?1=10&2=3
    - checks inventory for enough stock
    - then makes a transaction for the purchase

### customer-service (optional)
- we don't have a service for this, but can create one to hold customer specific details
- expose API to show what and when a customer purchased/returned

### dto-core (final state)
- we see the DTOs are reused (and redefined) in all 3 services 
- these files will be moved to this new module and used as a dependency in all services

```
all of the above are internal services working in isolation
these services interact with each other and with "shop-service"
using DTOs via REST endpoints 
```

### footnote
- of course for demo purposes this is not an exhaustive feature full list
- neither does it handle exception scenarios or proper exception handling
