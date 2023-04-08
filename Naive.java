/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

public class Naive {
    // Private instance variables of byte arrays xSample and ySample, and double variables priorX and priorY
    private byte[] xSample, ySample;
    private double priorX, priorY;

    // Constructor method that sets the xSample and ySample arrays
    public Naive(byte[] xSample, byte[] ySample) {
        this.xSample = xSample;
        this.ySample = ySample;
    }

    // Method that calculates the Bayesian probability of y given x
    public double bayes (byte y, byte x) {
        // Compute the prior probability of y and x
        this.priorY = this.probability(y, this.ySample);
        this.priorX = this.probability(x, this.xSample);

        // Compute the likelihood of x given y
        double likelihood = this.likelihood(x, y);

        // Check for the case where priorX = 0, return 0.0 to avoid division by zero error
        if (this.priorX == 0) { return 0.0; }

        // Compute and return the Bayesian probability of y given x
        return (this.priorY * likelihood) / this.priorX;
    }

    // Method that computes the likelihood of x given y
    private double likelihood (byte x, byte y) {
        // Initialize variables to keep track of the number of times x and (x, y) appear in the sample
        double xyCount = 0.0;
        double yCount = 0.0;

        // Iterate over the samples and count the occurrences of y and (x, y)
        for (int i = 0; i < xSample.length && i < ySample.length; i++) {
            if (y == ySample[i]) {
                yCount += 1;
                if (x == xSample[i]) {
                    xyCount += 1;
                }
            }
        }

        // Check for the case where yCount = 0.0, return 0.0 to avoid division by zero error
        if (yCount == 0.0) { return 0.0; }

        // Compute and return the likelihood of x given y
        return xyCount / yCount;
    }

    // Method that computes the probability of z occurring in the sample
    private double probability (byte z, byte[] sample) {
        // Initialize variable to keep track of the number of times z appears in the sample
        double count = 0.0;

        // Iterate over the sample and count the occurrences of z
        for (byte s : sample) {
            if (s == z) {
                count += 1;
            }
        }

        // Check for the case where the length of the sample is 0, return 0.0 to avoid division by zero error
        if (sample.length == 0) { return 0.0; }

        // Compute and return the probability of z occurring in the sample
        return count / sample.length;
    }
}

