##### Author: Instructor team SE, ASU Polytechnic, CIDSE, SE


##### Purpose
This program shows a very simple client server implementation. The server
has 3 services, echo, add, addmany. Basic error handling on the server side
is implemented. Client does not have error handling and only has hard coded
calls to the server.

* Please run `gradle Server` and `gradle Client` together.
* Program runs on localhost
* Port is hard coded

## Protocol: ##

### Echo: ###

Request: 

    {
        "type" : "echo", -- type of request
        "data" : <String>  -- String to be echoed 
    }

General response:

    {
        "type" : "echo", -- echoes the initial response
        "ok" : <bool>, -- true or false depending on request
        "echo" : <String>,  -- echoed String if ok true
        "message" : <String>,  -- error message if ok false
    }

Success response:

    {
        "type" : "echo",
        "ok" : true,
        "echo" : <String> -- the echoed string
    }


Error response:

    {
        "type" : "echo",
        "ok" : false,
        "message" : <String> -- what went wrong
    }


### Add: ### 
Request:

    {
        "type" : "add",
        "num1" : <String>, -- first number -- String needs to be an int number e.g. "3"
        "num2" : <String> -- second number -- String needs to be an int number e.g. "4" 
    }

General response

    {
        "type" : "add", -- echoes the initial request
        "ok" : <bool>, -- true or false depending on request
        "result" : <int>,  -- result if ok true
        "message" : <String>,  -- error message if ok false
    }


Success response:

    {
        "type" : "add",
        "ok" : true,
        "result" : <int> -- the result of add
    }

Error response:

    {
        "type" : "add",
        "ok" : false,
        "message" : <String> - error message about what went wrong
    }


### CharCount: ###
This one will count the number of characters in a given string, with the option to search for a specific character in the string. 
If the user specifies a character to search for, alter the request to include the character and return the number of times that character is found in the string.
If the user does not specify a character to search for, return the number of characters in the string.

Request:

    {
        "type" : "charcount",
        "findchar" : false, -- value is false to denote general character counting
        "count" : <String> -- String to search through e.g. "sally sold seashells down by the seashore"
    }

Request for searching for specific char:

    {
        "type" : "charcount",
        "findchar" : true, -- value is true to denote specific character search
        "find" : <String>, -- if findchar is true -- find is a one letter String to search for e.g. "s" 
        "count" : <String> -- String to search through e.g. "sally sold seashells down by the seashore"
    }

General response

    {
        "type" : "charcount", -- echoes the initial request
        "ok" : <bool>, -- true or false depending on request
        "result" : <int>,  -- result if ok true - number of the given character or overall characters in the String 
        "message" : <String>  -- error message if ok false
    }


Success response:

    {
        "type" : "charcount",
        "ok" : true,
        "result" : <int> -- number of the given character or overall characters in the String 
    }

Error response:

    {
        "type" : "charcount",
        "ok" : false,
        "message" : <String> -- error message about what went wrong
    }


### Inventory: ###
This one will be to add, view or take something from an inventory. 

The server stores a list of products and their quantity and through the client one can add a new product with a quantity. Stock up on the items by using providing a product that is already on the list. It is also possible to just view the inventory or buy something. 
If you want to do it well, then store the inventory list persistently (not required).

Request to add to the inventory:

    {
        "type" : "inventory",
        "task": "add",
        "productName" : <String>,
        "quantity" : <int>
    }

Request to view the inventory:

    {
        "type" : "inventory",
        "task" : "view"
    }

Request to buy from the inventory:

    {
        "type" : "inventory",
        "task" : "buy",
        "productName" : <String>,
        "quantity" : <int>
    }

General response

    {
        "type" : "inventory", -- echoes the initial request
        "ok" : <bool>, -- true or false depending on request
        "inventory" : [{"product": <String>, "quantity": int}],  -- result if ok true - returns the current inventory as list of JSon objects
        "message" : <String>  -- error message if ok false
    }

Success response:

    {
        "type" : "inventory",
        "ok" : true,
        "inventory" : [{"product": <String>, "quantity": int}],  --  e.g. [{"product": "Road bike", "quantity": 5},   {"product": "helmet", "quantity": 10}]
    }


Error response:

    {
        "type" : "inventory",
        "ok" : false,
        "message" : <String> -- error message about what went wrong
    }

Error response for buying a product that is not available in quantity:

    {
        "type" : "inventory",
        "ok" : false,
        "message" : Product <X> not available in quantity <Y>
    }

Error response for buying a product that is not in inventory at all:

    {
        "type" : "inventory",
        "ok" : false,
        "message" : Product <X> not in inventory
    }

### General error responses: ###
These are used for all requests.

Error response: When a required field "key" is not in request

    {
        "ok" : false
        "message" : "Field <key> does not exist in request" 
    }

Error response: When a required field "key" is not of correct "type"

    {
        "ok" : false
        "message" : "Field <key> needs to be of type: <type>"
    }

Error response: When the "type" is not supported, so an unsupported request

    {
        "ok" : false
        "message" : "Type <type> is not supported."
    }


Error response: When the "type" is not supported, so an unsupported request

    {
        "ok" : false
        "message" : "req not JSON"
    }

### Completion of All test Written by you and me 

###### server is running on amazon AWS no need to run local 
> [ec2-user@ip-172-31-21-82 Starter3-1]$ gradle Server -Pport=8888
Starting a Gradle Daemon (subsequent builds will be faster)

> Task :Server
Server ready for connections
Server waiting for a connection
Client connected
Echo request: {"data":"Hello","type":"echo"}
Add request: {"num1":"4","type":"add","num2":"5"}
Add many request: {"type":"addmany","nums":["444544545","","","5","4"]}
Add many request: {"type":"addmany","nums":["4","4","4","4","4","4"]}
Character count request: {"findchar":false,"count":"String inpust","type":"charcount"}
Character count request: {"findchar":false,"count":"Input","type":"charcount"
Character count request: {"findchar":true,"find":"i","count":"String","type":"charcount"}
Client disconnect
Server waiting for a connection
Client connected
Add many request: {"type":"addmany","nums":["12","15","111","42"]}
Add many request: {"type":"addmany","nums":["two","15","111","42"]}
Client disconnect
Server waiting for a connection
Client connected
Add request: {"num1":"1","type":"add","num2":"2"}
Add request: {"num1":"1","type":"add"}
Add request: {"type":"add","num2":"1"}
Add request: {"type":"add"}
Add request: {"num1":"hello","type":"add","num2":"2"}
Client disconnect
Server waiting for a connection
Client connected
Echo request: {"data":"gimme this back!","type":"echo"}
Echo request: {"type":"echo"}
Client disconnect
Server waiting for a connection
Client connected
Add request: {"num1":"1","type":"add","num2":"2"}
Add request: {"num1":"1","type":"add"}
Add request: {"type":"add","num2":"1"}
Add request: {"type":"add"}
Add request: {"num1":"hello","type":"add","num2":"2"}
Client disconnect
Server waiting for a connection