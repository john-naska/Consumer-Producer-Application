/* Apache Kafka Database Consumer B for KM.ON Challenge
 * Date: 18-08-2020
 * John Naska
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConsumerB {

    // ConsumerB Constructor with all the relevant logic, so it can be triggered from Runner class
    public ConsumerB() {

        // Object which stores the key-value-pair
        Properties properties = new Properties();

        // Adding properties
        // (Basic configs for kafka-client to get connected to kafka-server
        // including the different deserialization values it needs to receive from the kafka-broker)
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-telemetry");

        // Create and initialize instance of kafka-consumer with properties specified above
        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);

        // Handling the relevant topics
        List topics = new ArrayList();
        topics.add("telemetry");
        kafkaConsumer.subscribe(topics);

        // counter needed to break out of the infinite while-loop
        int counter;

        // initializing an ArrayList of queries to store them while iterating through the topics (as they are needed later for the SQL statements)
        ArrayList<String> queries = new ArrayList<String>();

        try {

            // infinite while-loop to fetch all records from broker
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));

                // iterating through the records (if they are > 0) and creating sql query to be able to save the topic name and the value (key value pair) to the database later
                if (records.count() != 0) {
                    counter = records.count();

                    for (ConsumerRecord record : records){
                        System.out.println(String.format("Topic: %s, Value: %s", record.topic(), record.value()));
                        String current_timestamp = LocalDateTime.now().toString();
                        queries.add("INSERT INTO telemetry (topicName, meteredValue, Time_Stamp) VALUES (" + "\'" + record.topic() + "\', " + record.value() + ", \"" + current_timestamp + "\")");
                        counter--;
                    }

                    if (counter == 0) {
                        break;
                    }
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            kafkaConsumer.close();
        }

        // saving records in database through generated queries above
        dbAgent dbAgent = new dbAgent(queries);

    }
}
