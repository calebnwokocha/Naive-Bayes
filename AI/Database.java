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
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS probabilities (y BYTE, x BYTE, probability DOUBLE)");
        }
    }

    protected void saveProbabilitiesMap(Map<Byte, Map<Byte, Double>> probabilitiesMap) {
        String sql = "INSERT INTO probabilities (y, x, probability) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (byte x : probabilitiesMap.keySet()) {
                Map<Byte, Double> yProbabilitiesMap = probabilitiesMap.get(x);
                for (byte y : yProbabilitiesMap.keySet()) {
                    double probability = yProbabilitiesMap.get(y);
                    stmt.setByte(1, y);
                    stmt.setByte(2, x);
                    stmt.setDouble(3, probability);
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Map<Byte, Map<Byte, Double>> loadProbabilitiesMap() {
        Map<Byte, Map<Byte, Double>> probabilitiesMap = new HashMap<>();

        String sql = "SELECT y, x, probability FROM probabilities";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                byte y = rs.getByte("y");
                byte x = rs.getByte("x");
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
