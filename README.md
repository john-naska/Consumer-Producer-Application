# Apache Kafka Consumer-Producer Application for KM.ON Challenge

Challenge description see file named "consumer_producer_de.pdf" provided by KM.ON.

Below steps are provided to get started with the solution. 

## Prerequisites

- Docker and Docker-compose
- Running Kafka and Zookeeper instances/containers (instructions see below)
- Java SDK 8+
- Maven
- SQLite
- IDE

### Getting Started I

Git and Terminal


- Clone this repo to your local machine
- Cd into this repo locally (see path below)
```
/.../.../Consumer_Producer/kafka-installation
```
- Run below command to start the Kafka and Zookeeper container (running in the background)
```
docker-compose -f docker-compose.yml up -d
```
- Optional: check with below command whether containers are running (they should be running ;) 
```
docker ps
```

### Getting Started II

IDE

1. Open the project folder in your IDE
2. Import libraries if prompted
3. Open Runner.java class and execute 
```
/.../.../Consumer_Producer/src/main/java/Runner.java
```


### Understanding the behaviour

Source code, console and database

- Source code: Has been comprehensively commented for further reference
- Console output: First, slf4j-logs are printed to the console (in red)
```
[main] INFO org.apache.kafka.clients.producer.ProducerConfig - ProducerConfig values: 
	acks = 1
	batch.size = 16384
	bootstrap.servers = [localhost:9092]
	buffer.memory = 33554432
	...
```
- Console output: Second, event topics are printed by Kafka-Consumer A (in white)

```
Topic: events, Value: Start
Topic: events, Value: Status 1
Topic: events, Value: Status 2
...
```
- Console output: Third, telemetry topics are printed by Kafka-Consumer B as a optional feature (in white)
```
Topic: telemetry, Value: 4923
Topic: telemetry, Value: 7884
Topic: telemetry, Value: 5597
Topic: telemetry, Value: 4219
...
```
- Console output: Hint, logs and consumer outputs might get mixed up a little bit
- Database: Records from Consumer B are saved to local SQLite database with minimum data necessary
```
topicName | meteredValue |           Time_Stamp
__________|______________|____________________________
telemetry	5672	       2020-08-18T05:42:07.470
telemetry	6246	       2020-08-18T05:42:07.473
telemetry	3251	       2020-08-18T05:42:07.474 
...
```

### Important files

Files relevant for review purposes

1. Docker-Compose: 
```
/.../.../Consumer_Producer/kafka-installation/docker-compose.yml
```
2. App-Runner: 
```
/.../.../Consumer_Producer/src/main/java/Runner.java
```
3. Producer: 
```
/.../.../Consumer_Producer/src/main/java/Producer.java
```
4. Consumer A: 
```
/.../.../Consumer_Producer/src/main/java/ConsumerA.java
```
5. Consumer B:
 ```
 /.../.../Consumer_Producer/src/main/java/ConsumerB.java
 ```
6. Database-Agent: 
```
/.../.../Consumer_Producer/src/main/java/dbAgent.java
```
7. Maven-Project-Configs: 
```
/.../.../Consumer_Producer/pom.xml
``` 
8. Readme-Guide: 
```
/.../.../Consumer_Producer/README.md
```
9. SQLite-Database (will be created once main method has run): 
```
/.../.../Consumer_Producer/telemetryData.db 
```
10. Challenge-Description: 
```
/.../.../Consumer_Producer/consumer_producer_de.pdf
```

### Finishing

1. Main method (Application): No need to manually stop the app, as it's designed to terminate by itself, due to simplicity reasons
2. Stopping the Services: in Terminal run below command to kill the Kafka and Zookeeper containers
```
docker-compose down
```
