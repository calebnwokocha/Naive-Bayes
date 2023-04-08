/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

import AI.Naive;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Byte> xSample = new ArrayList<>();
        ArrayList<Byte> ySample = new ArrayList<>();

        xSample.add((byte) 1);
        xSample.add((byte) 1);
        xSample.add((byte) 1);
        xSample.add((byte) 1);
        xSample.add((byte) 1);
        xSample.add((byte) 1);

        ySample.add((byte) 4);
        ySample.add((byte) 5);
        ySample.add((byte) 5);
        ySample.add((byte) 0);
        ySample.add((byte) 4);
        ySample.add((byte) 5);

        Naive naive = new Naive(xSample, ySample, "database.sqlite");
        System.out.println(naive.bayes((byte) 5, (byte) 1));
        System.out.println(naive.getProbability((byte) 5, (byte) 1));
        System.out.println(naive.bayes((byte) 0, (byte) 1));
        System.out.println(naive.getProbability((byte) 0, (byte) 1));
        System.out.println(naive.bayes((byte) 6, (byte) 1));
        System.out.println(naive.getProbability((byte) 6, (byte) 1));
        System.out.println(naive.bayes((byte) 14, (byte) 1));
        System.out.println(naive.getProbability((byte) 14, (byte) 1));
    }
}

