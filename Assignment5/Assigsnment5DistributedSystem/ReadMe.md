# Distributed Calculation 

### **Overview of the Distributed System**

This system demonstrates a **distributed computing model** designed to efficiently divide and 
process a list of integers across multiple computing nodes. The primary objective is to perform
a **simple addition operation** in parallel while ensuring accuracy and fault tolerance through 
a **leader election and consensus mechanism**. The system follows a structured workflow where
tasks are distributed, computations are verified, and results are returned to the client only
when all nodes agree on the correctness of the calculations.

The process begins with the **client**, which provides an input list of integers and a delay time 
for processing. This data is sent to the **leader**, a node that is elected dynamically through
a **consensus algorithm** among all available nodes. The leader is responsible for dividing the 
input list into equal subsets and distributing them among the nodes for independent computation.
Each **node** then performs an **addition operation** on its assigned subset and returns the **partial 
sum** to the leader.

Once all partial sums are received, the leader initiates a **cross-verification process**, 
where each node is assigned the dataset and computed sum of another node. The nodes independently 
recompute the sums and return a **verification response** indicating whether the original result
was correct. If all nodes confirm the accuracy of the computations, the leader **aggregates the 
partial sums** to produce the **final sum**, which is then sent back to the client along with the 
computation time. However, if any node detects an inconsistency in the sum verification process, 
the leader **rejects the result** and informs the client that consensus was not achieved.

To maintain system reliability, the system implements **fault tolerance mechanisms**. Nodes 
regularly exchange **heartbeat messages** to check each other’s availability. If a node fails,
the leader can **redistribute tasks** to maintain execution. Additionally, if the **leader node fails**,
the remaining nodes initiate a **new leader election process**, ensuring continuous system operation
without manual intervention.

This system effectively demonstrates key principles of **distributed computing, parallel processing, 
leader election, and consensus validation**. By distributing computational tasks among multiple nodes,
it optimizes processing efficiency, reduces the time required for computation, and ensures **accuracy 
and fault resilience** through a collaborative verification mechanism.

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