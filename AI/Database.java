/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.sql.*;

class Database {
    private final String databaseAddress;

    Database (String databaseAddress) {
        this.databaseAddress = databaseAddress;
        try (Connection conn = getConnection();
             Statement stmt = Objects.requireNonNull(conn).createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS probabilities (y TINYINT, x TINYINT, probability DOUBLE)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + this.databaseAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void saveProbabilities(Map<Byte, Map<Byte, Double>> probabilities) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = Objects.requireNonNull(conn).
                     prepareStatement("INSERT INTO probabilities (y, x, probability) VALUES (?, ?, ?)")) {

            for (byte x : probabilities.keySet()) {
                Map<Byte, Double> yProbabilities = probabilities.get(x);
                for (byte y : yProbabilities.keySet()) {
                    double probability = yProbabilities.get(y);
                    stmt.setByte(1, y);
                    stmt.setByte(2, x);
                    stmt.setDouble(3, probability);
                    stmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Map<Byte, Map<Byte, Double>> loadProbabilities() {
        Map<Byte, Map<Byte, Double>> probabilities = new HashMap<>();

        try (Connection conn = getConnection();
             Statement stmt = Objects.requireNonNull(conn).createStatement();
             ResultSet rs = stmt.executeQuery("SELECT y, x, probability FROM probabilities")) {

            while (rs.next()) {
                byte y = rs.getByte("y");
                byte x = rs.getByte("x");
                double probability = rs.getDouble("probability");

                Map<Byte, Double> yProbabilities = probabilities.getOrDefault(x, new HashMap<>());
                yProbabilities.put(y, probability);
                probabilities.put(x, yProbabilities);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return probabilities;
    }
}
