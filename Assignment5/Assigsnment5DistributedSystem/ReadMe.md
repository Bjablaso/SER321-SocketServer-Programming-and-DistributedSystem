# Distributed Sum 

### **Overview of the Distributed Sum**

This project demonstrates a distributed computing model designed to efficiently divide 
and process a list of integers across multiple computing nodes. The primary objective 
is to perform parallel addition operations while ensuring accuracy and fault tolerance 
through task distribution and consensus validation.

The system follows a structured workflow where:

- Tasks are distributed among multiple computing nodes.
- Computations are verified independently by each node.
- Results are only returned to the client when all nodes agree on correctness.

#### System Components & Workflow

1) Client: Provides an input list of integers and a delay time for processing. 
This data is sent to the Leader

##### Leader:

- Receives the list and divides it into equal subsets.
- Distributes these subsets among nodes for parallel computation.
- Performs a cross-verification process after collecting results.

##### Nodes:

- Perform addition operations on assigned subsets.
- Return partial sums to the leader.
- Recompute sums from another node to verify correctness.

##### Reliability & Fault Tolerance

- Heartbeat messages: Nodes periodically check each other’s availability.
- Task redistribution: If a node fails, the leader reassigns tasks.

This system effectively demonstrates key principles of distributed computing, 
parallel processing, and consensus validation. By distributing computational 
tasks among multiple nodes, it optimizes processing efficiency, reduces computation 
time, and ensures accuracy and fault resilience through a collaborative verification 
mechanism.


### **How This Works in the System**

- Client → Leader (Sends Task)
> explain what happens 
```json
 {
  "operationType": "DATA",
  "senderId": "Client_1",
  "numbers": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
  "delay": 50
}
```

- Leader → Nodes (Assigns Calculation Task)
````json
{
  "operationType": "CALCULATE",
  "senderId": "Leader_1",
  "numbers": [1, 2, 3, 4, 5],
  "delay": 50
}

````
- Nodes → Leader (Return Partial Sum)
````json
{
  "responseType": "PARTIAL_SUM",
  "senderId": "Node_1",
  "partialSum": 15
}

````
- Nodes → Leader (Agree or Disagree with Sum)

✅  If correct:
````json
{
  "responseType": "VERIFY_RESULT",
  "senderId": "Node_2",
  "verificationStatus": true
}

````
❌ If incorrect:

````json
{
  "responseType": "VERIFY_RESULT",
  "senderId": "Node_3",
  "verificationStatus": false
}

````

- Leader → Client

✅  If all Nodes agree (Consensus Passed):
````json
{
  "responseType": "FINAL_SUM",
  "senderId": "Leader_1",
  "finalSum": 55,
  "timeTaken": 5000,
  "consensusPassed": true
}

````

❌ If even one Node disagrees:

````json
{
  "responseType": "ERROR",
  "senderId": "Leader_1",
  "errorMessage": "Consensus failed: Nodes did not agree on the sum."
}


````