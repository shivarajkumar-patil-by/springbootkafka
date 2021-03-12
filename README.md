**Business Use Case:**
Process the employee information received from in a kafka topic where validate if all required fields persisted (i.e., employee name, employee id, address, organization, role) once validated post the message to a different kafka topic

**To publish employee details to kafka employee topic**
POST :  http://localhost:9000/kafka/publish
Request body :
{
    "name" : "Employee5",
    "id" : "5",
    "address" : "JP Nagar",
    "organization" : "Yantriks",
    "role" : "Dev"
}