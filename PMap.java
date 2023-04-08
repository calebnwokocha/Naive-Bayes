/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.sql.*;

public class PMap {
    private final String filename;

    public PMap(String filename) {
        this.filename = filename;

        try (Connection conn = getConnection();
             Statement stmt = Objects.requireNonNull(conn).createStatement()) {

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS probabilities (y TINYINT, x TINYINT, probability DOUBLE)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProbability(byte y, byte x, double probability) {
        Map<Byte, Map<Byte, Double>> probabilities = new HashMap<>();;
        Map<Byte, Double> yProbabilities = probabilities.getOrDefault(x, new HashMap<>());
        yProbabilities.put(y, probability);
        probabilities.put(x, yProbabilities);
        saveProbabilities(probabilities);
    }

    public double getProbability(byte y, byte x) {
        Map<Byte, Double> yProbabilities = Objects.requireNonNull(this.loadProbabilities()).get(x);
        if (yProbabilities != null) {
            Double probability = yProbabilities.get(y);
            if (probability != null) {
                return probability;
            }
        }
        return 0.0;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + filename);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveProbabilities(Map<Byte, Map<Byte, Double>> probabilities) {
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

    private Map<Byte, Map<Byte, Double>> loadProbabilities() {
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
