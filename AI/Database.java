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
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS posteriors (y DOUBLE, x DOUBLE, posterior DOUBLE, PRIMARY KEY (x, y))");
        }
    }

    protected void saveMap(Map<Double, Map<Double, Double>> map) {
        String sql = "INSERT OR REPLACE INTO posteriors (y, x, posterior) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (double x : map.keySet()) {
                Map<Double, Double> posteriorsMap = map.get(x);
                for (double y : posteriorsMap.keySet()) {
                    double posterior = posteriorsMap.get(y);
                    stmt.setDouble(1, y);
                    stmt.setDouble(2, x);
                    stmt.setDouble(3, posterior);
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Map<Double, Map<Double, Double>> loadMap() {
        Map<Double, Map<Double, Double>> map = new HashMap<>();

        String sql = "SELECT y, x, posterior FROM posteriors ORDER BY x, y";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                double y = rs.getDouble("y");
                double x = rs.getDouble("x");
                double posterior = rs.getDouble("posterior");

                map.computeIfAbsent(x, k -> new HashMap<>()).put(y, posterior);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return map;
    }

    public void close() {
        try { if (connection != null) { connection.close(); } }
        catch (SQLException e) { e.printStackTrace(); }
    }
}
