/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.util.ArrayList;

public class Naive extends PMap {
    private final ArrayList<Byte> xSample, ySample;

    public Naive(ArrayList<Byte> xSample, ArrayList<Byte> ySample, String databaseAddress) {
        super(databaseAddress);
        this.xSample = xSample;
        this.ySample = ySample;
    }

    public double bayes (byte y, byte x) {
        double priorY = this.probability(y, this.ySample);
        double priorX = this.probability(x, this.xSample);
        double likelihood = this.likelihood(x, y);
        double posterior;
        if (priorX == 0) { posterior = 0.0; }
        else { posterior = (priorY * likelihood) / priorX; }
        addProbability(y, x, posterior);
        return posterior;
    }

    private double likelihood (byte x, byte y) {
        double xyCount = 0.0;
        double yCount = 0.0;

        for (int i = 0; i < xSample.size() && i < ySample.size(); i++) {
            if (y == ySample.get(i)) { yCount += 1;
                if (x == xSample.get(i)) { xyCount += 1; }
            }
        }

        if (yCount == 0.0) { return 0.0; }
        return xyCount / yCount;
    }

    private double probability (byte z, ArrayList<Byte> sample) {
        double count = 0.0;
        for (byte s : sample) { if (s == z) { count += 1; } }
        if (sample.size() == 0) { return 0.0; }
        return count / sample.size();
    }
}


