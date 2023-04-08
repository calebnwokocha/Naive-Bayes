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
    Map<Byte, Map<Byte, Double>> probabilities;
    boolean dbEmpty;

    public PMap(String databaseAddress) {
        this.db = new Database(databaseAddress);
        this.dbEmpty = this.db.loadProbabilities().isEmpty();
        if (!dbEmpty) { this.probabilities = this.db.loadProbabilities(); }
        else { this.probabilities = new HashMap<>(); }
    }

    protected void addProbability(byte y, byte x, double probability) {
        Map<Byte, Double> yProbabilities;

        if (!dbEmpty) { yProbabilities = Objects.requireNonNull(this.probabilities).get(x); }
        else { yProbabilities = this.probabilities.getOrDefault(x, new HashMap<>()); }

        yProbabilities.put(y, probability);
        this.probabilities.put(x, yProbabilities);
        db.saveProbabilities(this.probabilities);
    }

    public double getProbability(byte y, byte x) {
        Map<Byte, Double> yProbabilities = Objects.requireNonNull(this.db.loadProbabilities()).get(x);
        if (yProbabilities.get(y) != null) { return yProbabilities.get(y); }
        return 0.0;
    }
}
