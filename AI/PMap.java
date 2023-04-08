/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class PMap {
    private final Database db;
    public PMap(String databaseAddress) { this.db = new Database(databaseAddress); }

    protected void addProbability(byte y, byte x, double probability) {
        Map<Byte, Map<Byte, Double>> probabilities = new HashMap<>();;
        Map<Byte, Double> yProbabilities = probabilities.getOrDefault(x, new HashMap<>());
        yProbabilities.put(y, probability);
        probabilities.put(x, yProbabilities);
        db.saveProbabilities(probabilities);
    }

    public double getProbability(byte y, byte x) {
        Map<Byte, Double> yProbabilities = Objects.requireNonNull(db.loadProbabilities()).get(x);
        if (yProbabilities != null) {
            Double probability = yProbabilities.get(y);
            if (probability != null) {
                return probability;
            }
        }
        return 0.0;
    }
}
