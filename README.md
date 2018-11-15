# IGT
Übungsaufgaben für die Vorlesung Integrationstechnologien WS18/19 der Hochschule Mannheim


## Api Doku

### Endpoints
  localhost:\<port\>/api
    
| Database 	| Port 	| Type            	|
|----------	|------	|-----------------	|
| MySQL    	| 6000 	| SQL Database    	|
| MongoDB  	| 6001 	| Document Store  	|
| Ehcache  	| 6002 	| Cache           	|
| Neo4j    	| 6003 	| Graph Database  	|
| Redis    	| 6004 	| Key-value Store 	|
  
**Example:** http://localhost:6001/api for the MongoDB instance


### List all customers
*GET* \<endpoint\>/customers

**response:**
```json
  {
    "customers":
    [
      {
        "customerId": 5,
        "name" : "Max Musterman",
        "phones": ["00491234567", "0038839284"]
      },
      {
        "customerId": 6,
        "name" : "Max Musterman",
        "phones": ["00491234567", "0038839284"]
      },
      
      ...
    ]
  }
```


### Get one customer
*GET* \<endpoint\>/customer/\<id\>

**response:**
```json
      {
        "customerId": 5,
        "name" : "Max Musterman",
        "phones": ["00491234567", "0038839284"]
      }
```

### Update a customer
*POST* \<endpoint\>/customer/\<id\>
If a field is not specified than it wont get updated.

**request:** the fields to update
```json
     {
        "name" : "Max Musterman",
        "phones": ["00491234567", "0038839284"]
      }
```


**response:** the changed customer with its id
```json
      {
        "customerId": 5,
        "name" : "Max Musterman",
        "phones": ["00491234567", "0038839284"]
      }
```

### Create a new customer
*POST* \<endpoint\>/customer/new

**request:** the fields to update
```json
      {
        "name" : "Max Musterman",
        "phones": ["00491234567", "0038839284"]
      }
```


**response:** the created customer with its id
```json
     {
        "customerId": 5,
        "name" : "Max Musterman",
        "phones": ["00491234567", "0038839284"]
      }
```

### Create an airport
*POST* \<endpoint\>/airport/new

**request:** the fields to update
```json
      {
        "name": "Some Airport",
        "short": "SAP"
      }
```


**response:** the created airport with its id
```json
     {
        "airportId": 5,
        "name": "Some Airport",
        "short": "SAP"
      }
```

### Get all airports
*GET* \<endpoint\>/airports

**response:** 
```json
    { 
       "airports": [
           {
              "airportId": 5,
              "name": "Some Airport",
              "short": "SAP"
            },
             {
              "airportId": 6,
              "name": "Another Airport",
              "short": "AAP"
            },
            ...
        ]
    }
```

### Create a Flight
*POST* \<endpoint\>/flight/new

List all airports in the correct sequence.

**request:**
```json
     {
        "miles": 500,
        "startTime": "Tue Nov 13 2018 23:32:48 GMT+0100",
        "arrivalTime": "Wen Nov 14 2018 23:32:48 GMT+0500",
        "airportsList": [5, 6, 8]
      }
```


**response:** the created flight with its id
```json
     {
        "flightId": 5,
        "miles": 500,
        "startTime": "Tue Nov 13 2018 23:32:48 GMT+0100",
        "arrivalTime": "Wen Nov 14 2018 23:32:48 GMT+0500",
        "airportsList": [5, 6, 8]
      }
```

### Book a Flight
*POST* \<endpoint\>/booking/new

List all airports in the correct sequence.

**request:**
```json
      {
        "customerId": 5,
        "flightId": 6
      }
```


**response:** the booking id
```json
     {
        "bookingId": 5
      }
```

### Cancel a Booking
*POST* \<endpoint\>/booking/cancel

List all airports in the correct sequence.

**request:**
```json
      {
        "bookingId": 5
      }
```

### clear all

*GET* \<endpoint\>/dropdb
dont do this





