/* Apache Kafka Console Consumer A for KM.ON Challenge
 * (Not the default 'Out of the Box' - Console-Consumer by Apache Kafka)
 * Date: 18-08-2020
 * John Naska
 */

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConsumerA {

    // ConsumerA Constructor with all the relevant logic, so it can be triggered from Runner class
    public ConsumerA() {

        // Object which stores the key-value-pair
        Properties properties = new Properties();

        // Adding properties
        // (Basic configs for kafka-client to get connected to kafka-server
        // including the different deserialization values it needs to receive from the kafka-broker)
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-events");

        // Create and initialize instance of kafka-consumer with properties specified above
        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);

        // Handling the relevant topics
        List topics = new ArrayList();
        topics.add("events");
        kafkaConsumer.subscribe(topics);

        // counter needed to break out of the infinite while-loop
        int counter;

        try {

            // infinite while-loop to fetch all records from broker
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10));

                // iterating through the records (if they are > 0) and printing the topic name and the value (key value pair) to the console
                if (records.count() != 0) {
                    counter = records.count();

                    for (ConsumerRecord record : records){
                        System.out.println(String.format("Topic: %s, Value: %s", record.topic(), record.value()));
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
    }
}

