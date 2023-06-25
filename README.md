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

### inventory-service
- this service talks to an imaginary DB (in this case just in memory data structures)
- it provides endpoints to update inventory in case a purchase or return happens
- an inventory line item is one product (like its id, price and in stock count etc)

### transaction-service
- this connects to purchase DB (again imaginary)
- and provides API to keep record of transactions
- like when a customer makes a purchase or returns items
- a customer can purchase many counts of many inventory line items or return

### shop-service
- this is the service that coordinates and works with all other services
- when a customer makes a purchase or return this would call all the 3 services
- and determine if the transaction would go thru or not

### customer-service (optional)
- we dont have a service for this, but can create one to hold customer specific details
- expose API to show what and when a customer purchased/returned

```
                 ---> inventory-service --> inventory-DB
                |
shop-service --- --> customer-service --> cust-DB
                |
                 ---> transaction-service --> txn-DB
```
```
all of the above are internal services working in isolation
these services interact with each other and with "shop-service"
using DTOs via REST endpoints 
```

### footnote
- of course for demo purposes this is not an exhaustive feature full list
- neither does it handle exception scenarios or proper exception handling
