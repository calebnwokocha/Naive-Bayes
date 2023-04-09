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
                 String databaseAddress, int base)
    {
        super(databaseAddress, base);
        this.xSample = xSample;
        this.ySample = ySample;
        this.sampleSize = Math.min(xSample.size(), ySample.size());
    }

    public double bayes(byte y, byte x, int guessCoverage) {
        double priorY = this.probability(y, this.ySample);
        double priorX = this.probability(x, this.xSample);
        double likelihood = this.likelihood(x, y);
        double posterior = (priorX == 0) ? 0.0 : (priorY * likelihood) / priorX;
        posterior = (posterior == 0.0) ? this.guess(y, x, guessCoverage) : posterior;
        addProbability(y, x, posterior);
        return posterior;
    }

    private double likelihood (byte x, byte y) {
        double xyCount = 0.0;
        double yCount = 0.0;
        for (int i = 0; i < sampleSize; i++) {
            if (y == ySample.get(i)) { yCount += 1;
                if (x == xSample.get(i)) { xyCount += 1; }
            }
        } return (yCount == 0.0) ? 0.0 : xyCount / yCount;
    }

    private double probability(byte z, ArrayList<Byte> sample) {
        int count = 0;
        for (byte s : sample) { if (s == z) { count += 1; } }
        return (sample.size() == 0) ? 0.0 : (double) count / sample.size();
    }

    private double guess (byte y, byte x, int guessCoverage) {
        byte maxY = (byte) (y * (guessCoverage + 1));
        byte minY = (byte) (y / (guessCoverage + 1));
        double posterior;

        for (y = minY; y <= maxY; y++) {
            posterior = this.bayes(y, x, guessCoverage);
            if (posterior > 0.0) { return posterior; }
        } return 0.0;
    }
}
