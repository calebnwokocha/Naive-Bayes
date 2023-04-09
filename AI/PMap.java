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
    private final Map<Byte, Map<Byte, Double>> probabilitiesMap;

    protected PMap(String databaseAddress, int base) {
        super(base);
        this.db = new Database(databaseAddress);
        this.probabilitiesMap = new HashMap<>();
    }

    public double getProbability(byte y, byte x) {
        Map<Byte, Double> yProbabilitiesMap = this.probabilitiesMap.get(x);

        if (yProbabilitiesMap == null) {
            yProbabilitiesMap = db.loadProbabilitiesMap().get(x);

            if (yProbabilitiesMap == null) { return 0.0; }
            else { this.probabilitiesMap.put(x, yProbabilitiesMap); }
        }

        Double probability = yProbabilitiesMap.get(y);
        return Objects.requireNonNullElse(probability, 0.0);
    }

    protected void addProbability(byte y, byte x, double probability) {
        Map<Byte, Double> yProbabilitiesMap = this.probabilitiesMap.get(x);

        if (yProbabilitiesMap == null) {
            yProbabilitiesMap = db.loadProbabilitiesMap().getOrDefault(x, new HashMap<>());
            this.probabilitiesMap.put(x, yProbabilitiesMap);
        }

        if (!yProbabilitiesMap.containsKey(y) || !yProbabilitiesMap.get(y).equals(probability)) {
            yProbabilitiesMap.put(y, probability);
            db.saveProbabilitiesMap(this.probabilitiesMap);
        }
    }
}
