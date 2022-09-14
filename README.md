
# Virtual Power Plant System

This is a virtual power plant system for aggregating distributed power sources into a single cloud based energy provider. 

This system has 2 APIs developed in Spring Boot as below:

1. An endpoint that accepts - in the HTTP request body - a list of batteries, each containing: name,
 postcode, and watt capacity. This data will be persisted in a H2 database.

2. An endpoint that receives a postcode range. The response body will contain a list of names of batteries
 that fall within the range, sorted alphabetically. Additionally, there will be some statistics included for the returned batteries, such as: total and average watt capacity.

Swagger : http://localhost:8080/swagger-ui.html