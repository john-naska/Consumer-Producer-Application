/* Apache Kafka Runner (main method) for KM.ON Challenge
 * Date: 18-08-2020
 * John Naska
 */

public class Runner {
    public static void main(String[] args) {

        // all 3 objects are created, initialized and run in a compact way
        Producer producer = new Producer();
        ConsumerA consumerA = new ConsumerA();
        ConsumerB consumerB = new ConsumerB();
    }
}
