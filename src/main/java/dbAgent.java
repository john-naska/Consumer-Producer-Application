/* Apache Kafka Database Agent for KM.ON Challenge
 * Date: 18-08-2020
 * John Naska
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class dbAgent {

    // dbAgent Constructor with all the relevant logic, so it can be triggered from ConsumerB class
    public dbAgent(ArrayList<String> queries) {

        // Creating table (if not exists) while using relative path for saving the database
        String dbName = "/telemetryData.db";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + dbName);
            Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS telemetry (topicName TEXT, meteredValue INTEGER, Time_Stamp TEXT)");

            // Running prior generated queries from ConsumerB class
            for (String query : queries) {
                statement.execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
