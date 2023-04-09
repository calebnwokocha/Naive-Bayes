/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.util.ArrayList;

public class Naive extends PMap {
    private final ArrayList<Byte> xSample, ySample;
    private final int sampleSize;

    public Naive(ArrayList<Byte> xSample, ArrayList<Byte> ySample,
                 String databaseAddress, double base)
    {
        super(databaseAddress, base);
        this.xSample = xSample;
        this.ySample = ySample;
        this.sampleSize = Math.min(xSample.size(), ySample.size());
    }

    public double bayes(byte y, byte x) {
        double priorY = this.probability(y, this.ySample);
        double priorX = this.probability(x, this.xSample);
        double likelihood = this.likelihood(x, y);
        double posterior = (priorX == 0) ? 0.0 : (priorY * likelihood) / priorX;
        addProbability(y, x, posterior);
        return posterior;
    }

    private double likelihood(byte x, byte y) {
        double xyCount = 0.0;
        double yCount = 0.0;
        for (int i = 0; i < sampleSize; i++) {
            byte yVal = ySample.get(i);
            if (y == yVal) { yCount += 1;
                if (x == xSample.get(i)) { xyCount += 1; }
            }
        }
        return (yCount == 0.0) ? 0.0 : xyCount / yCount;
    }

    private double probability(byte z, ArrayList<Byte> sample) {
        int count = 0;
        for (byte s : sample) { if (s == z) { count += 1; } }
        return (sample.size() == 0) ? 0.0 : (double) count / sample.size();
    }
}
