/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

import AI.Naive;

public class Test {
    public static void main(String[] args) {
        byte[] xSample = new byte[] {5, 1, 1, 1, 1, 1};
        byte[] ySample = new byte[] {5, 6, 0, 5, 4, 4};
        Naive naive = new Naive(xSample, ySample, "database.sqlite");
        System.out.println(naive.bayes((byte) 5, (byte) 1));
        System.out.println(naive.getProbability((byte) 5, (byte) 1));
        System.out.println(naive.bayes((byte) 0, (byte) 1));
        System.out.println(naive.getProbability((byte) 0, (byte) 1));
        System.out.println(naive.bayes((byte) 6, (byte) 1));
        System.out.println(naive.getProbability((byte) 6, (byte) 1));
        System.out.println(naive.bayes((byte) 4, (byte) 1));
        System.out.println(naive.getProbability((byte) 4, (byte) 1));
    }
}

