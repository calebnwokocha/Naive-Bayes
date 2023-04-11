/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.util.ArrayList;
import java.util.Map;

public class Bayes extends PMap {
    public Bayes(String databaseAddress, double base) { super(databaseAddress, base); }

    public void train(ArrayList<Double> ySample, ArrayList<Double> xSample) {
        int sampleSize = Math.min(xSample.size(), ySample.size());
        for (int i = 0; i < sampleSize; i++) {
            double prior = this.probability(ySample.get(i), ySample);
            double evidence = this.probability(xSample.get(i), xSample);
            double likelihood = this.likelihood(xSample.get(i), ySample.get(i), xSample, ySample);
            double posterior = (evidence == 0) ? 0.0 : (prior * likelihood) / evidence;
            mapPosterior(ySample.get(i), xSample.get(i), posterior);
        }
    }

    private double likelihood (double x, double y, ArrayList<Double> xSample,
                               ArrayList<Double> ySample)
    {
        double xyCount = 0.0;
        double yCount = 0.0;
        int sampleSize = Math.min(xSample.size(), ySample.size());
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
