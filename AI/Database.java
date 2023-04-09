/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class Database {
    private Connection connection;

    protected Database(String databaseAddress) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseAddress);
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS probabilities (y DOUBLE, x DOUBLE, probability DOUBLE)");
        }
    }

    protected void saveProbabilitiesMap(Map<Double, Map<Double, Double>> probabilitiesMap) {
        String sql = "INSERT INTO probabilities (y, x, probability) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (double x : probabilitiesMap.keySet()) {
                Map<Double, Double> yProbabilitiesMap = probabilitiesMap.get(x);
                for (double y : yProbabilitiesMap.keySet()) {
                    double probability = yProbabilitiesMap.get(y);
                    stmt.setDouble(1, y);
                    stmt.setDouble(2, x);
                    stmt.setDouble(3, probability);
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Map<Double, Map<Double, Double>> loadProbabilitiesMap() {
        Map<Double, Map<Double, Double>> probabilitiesMap = new HashMap<>();

        String sql = "SELECT y, x, probability FROM probabilities";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                double y = rs.getDouble("y");
                double x = rs.getDouble("x");
                double probability = rs.getDouble("probability");

                probabilitiesMap.computeIfAbsent(x, k -> new HashMap<>()).put(y, probability);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return probabilitiesMap;
    }

    public void close() {
        try { if (connection != null) { connection.close(); } }
        catch (SQLException e) { e.printStackTrace(); }
    }
}
