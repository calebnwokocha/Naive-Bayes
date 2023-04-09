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
    private final Map<Double, Map<Double, Double>> probabilitiesMap;

    protected PMap(String databaseAddress, double base) {
        super(base);
        this.db = new Database(databaseAddress);
        this.probabilitiesMap = new HashMap<>();
    }

    public double getProbability(double y, double x, int searchDepth, double searchRate) {
        Map<Double, Double> yProbabilitiesMap = this.probabilitiesMap.get(x);

        if (yProbabilitiesMap == null) {
            yProbabilitiesMap = db.loadProbabilitiesMap().get(x);

            if (yProbabilitiesMap == null) { return 0.0; }
            else { this.probabilitiesMap.put(x, yProbabilitiesMap); }
        }

        Double probability = yProbabilitiesMap.get(y);
        return Objects.requireNonNullElse(probability, this.searchProbability(y, x, searchDepth, searchRate));
    }

    protected void addProbability(double y, double x, double probability) {
        Map<Double, Double> yProbabilitiesMap = this.probabilitiesMap.get(x);

        if (yProbabilitiesMap == null) {
            yProbabilitiesMap = db.loadProbabilitiesMap().getOrDefault(x, new HashMap<>());
            this.probabilitiesMap.put(x, yProbabilitiesMap);
        }

        if (!yProbabilitiesMap.containsKey(y) || !yProbabilitiesMap.get(y).equals(probability)) {
            yProbabilitiesMap.put(y, probability);
            db.saveProbabilitiesMap(this.probabilitiesMap);
        }
    }

    private double searchProbability (double y, double x, int searchDepth,
                                      double searchRate)
    {
        double maxY = y * (searchDepth + 1);
        double minY = y / (searchDepth + 1);
        double probability;

        for (double i = minY; i <= maxY; i += searchRate) {
            Map<Double, Double> yProbabilitiesMap = this.probabilitiesMap.get(x);

            if (yProbabilitiesMap == null) {
                yProbabilitiesMap = db.loadProbabilitiesMap().get(x);

                if (yProbabilitiesMap == null) { return 0.0; }
                else { this.probabilitiesMap.put(x, yProbabilitiesMap); }
            }

            probability = yProbabilitiesMap.getOrDefault(i, 0.0);

            if (probability > 0.0) {
                this.addProbability(y, x, probability);
                return probability;
            }
        } return 0.0;
    }
}
