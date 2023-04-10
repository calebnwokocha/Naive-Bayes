/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.util.ArrayList;

public class Naive extends PMap {
    private final ArrayList<Double> xSample, ySample;
    private final int sampleSize;

    public Naive(ArrayList<Double> xSample, ArrayList<Double> ySample,
                 String databaseAddress, double base)
    {
        super(databaseAddress, base);
        this.xSample = xSample;
        this.ySample = ySample;
        this.sampleSize = Math.min(xSample.size(), ySample.size());
    }

    public double bayes(double y, double x) {
        double prior = this.probability(y, this.ySample);
        double evidence = this.probability(x, this.xSample);
        double likelihood = this.likelihood(x, y);
        double posterior = (evidence == 0) ? 0.0 : (prior * likelihood) / evidence;
        savePosterior(y, x, posterior);
        return posterior;
    }

    private double likelihood (double x, double y) {
        double xyCount = 0.0;
        double yCount = 0.0;
        for (int i = 0; i < sampleSize; i++) {
            if (y == ySample.get(i)) { yCount += 1;
                if (x == xSample.get(i)) { xyCount += 1; }
            }
        } return (yCount == 0.0) ? 0.0 : xyCount / yCount;
    }

    private double probability(double z, ArrayList<Double> sample) {
        int count = 0;
        for (double s : sample) { if (s == z) { count += 1; } }
        return (sample.size() == 0) ? 0.0 : (double) count / sample.size();
    }
}
