/* Apache Kafka Producer for KM.ON Challenge
 * Date: 18-08-2020
 * John Naska
 */

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {

    public Producer() {

        // Object which stores the key-value-pair
        Properties properties = new Properties();

        // Adding properties
        // (Basic configs for kafka-client to get connected to kafka-server
        // including the different serialization values it needs to send to the kafka-broker)
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create and initialize instance of kafka-producer with properties specified above
        KafkaProducer kafkaProducerEvent = new KafkaProducer(properties);
        KafkaProducer kafkaProducerTelemetry = new KafkaProducer(properties);

        try {
            // creating some dummy events
            String[] dummyEvents = {"Start", "Status 1", "Status 2", "Status 3", "Fehler A", "Fehler B", "Stop"};

            // simulation of parallel creation of events and telemetry input (real world circumstances)
            for(int i = 0; i < dummyEvents.length; i++) {

                // Producer Record object to be able to send it to kafka-broker
                ProducerRecord prodEventRecord = new ProducerRecord("events", "name", dummyEvents[i]);

                for (int j = 0; j < 10 ; j++) {
                    ProducerRecord prodTelemetryRecord = new ProducerRecord("telemetry", "name", "" + ((int)(Math.random()*10000)) + "");

                    // Send data from kafka-producer to the kafka-server
                    kafkaProducerTelemetry.send(prodTelemetryRecord);
                }
                kafkaProducerEvent.send(prodEventRecord);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaProducerEvent.close();
            kafkaProducerTelemetry.close();
        }
    }
}