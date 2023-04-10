/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class PMap extends FileHandler {
    private final Database db;
    private final Map<Double, Map<Double, Double>> map;

    protected PMap(String databaseAddress, double base) {
        super(base);
        this.db = new Database(databaseAddress);
        this.map = new HashMap<>();
    }

    public double getPosterior(double y, double x, int searchDepth, double searchRate) {
        Map<Double, Double> posteriorsMap = this.map.get(x);

        if (posteriorsMap == null) {
            posteriorsMap = db.loadMap().get(x);

            if (posteriorsMap == null) { return 0.0; }
            else { this.map.put(x, posteriorsMap); }
        }

        Double posterior = posteriorsMap.get(y);
        return Objects.requireNonNullElse(posterior, this.searchPosterior(y, x, searchDepth, searchRate));
    }

    protected void mapPosterior(double y, double x, double posterior) {
        Map<Double, Double> posteriorsMap = this.map.get(x);

        if (posteriorsMap == null) {
            posteriorsMap = db.loadMap().getOrDefault(x, new HashMap<>());
            this.map.put(x, posteriorsMap);
        }

        if (!posteriorsMap.containsKey(y) || !posteriorsMap.get(y).equals(posterior)) {
            if (posteriorsMap.containsKey(y) && !posteriorsMap.get(y).equals(posterior)) {
                posteriorsMap.put(y, posteriorsMap.get(y) + posterior);
            } else { posteriorsMap.put(y, posterior); }
            db.saveMap(this.map);
        }
    }

    private double searchPosterior (double y, double x, int searchDepth,
                                   double searchRate)
    {
        double maxY = y * searchDepth;
        double minY = y / searchDepth;
        double posterior;

        for (double i = minY; i <= maxY; i += searchRate) {
            Map<Double, Double> posteriorsMap = this.map.get(x);

            if (posteriorsMap == null) {
                posteriorsMap = db.loadMap().get(x);

                if (posteriorsMap == null) { return 0.0; }
                else { this.map.put(x, posteriorsMap); }
            }

            posterior = posteriorsMap.getOrDefault(i, 0.0);

            if (posterior > 0.0) {
                this.mapPosterior(y, x, posterior);
                return posterior;
            }
        } return 0.0;
    }
}
