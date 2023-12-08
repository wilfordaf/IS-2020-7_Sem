# Tiny event sourcing library demo
This project demonstrates how easily you can build your event-driven, event sourcing based application POC in 15 minutes
using [Tiny Event Sourcing library](https://github.com/andrsuh/tiny-event-sourcing)

### Run MongoDb
This example uses MongoDb as an implementation of the Event store.
Thus, you have to run MongoDb in order to test this example. We have `docker-compose` file in the root.
 Run following command to start the database:

```
docker-compose up
```

### Run the application
To make the application run you can start the main class `TaskManagerApplication`.

### Test the endpoints
There is a Swagger UI on [this](http://localhost:8081/swagger-ui/) endpoint
