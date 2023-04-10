/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

import AI.Naive;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Double> xSample = new ArrayList<>();
        ArrayList<Double> ySample = new ArrayList<>();

        xSample.add(1.0);
        xSample.add(1.0);
        xSample.add(1.0);
        xSample.add(1.0);
        xSample.add(1.0);
        xSample.add(1.0);

        ySample.add(25.0);
        ySample.add(25.0);
        ySample.add(25.0);
        ySample.add(25.0);
        ySample.add(25.0);
        ySample.add(25.0);

        Naive naive = new Naive("database.sqlite", 0.2);
        naive.train(xSample, ySample);
        System.out.println(naive.getPosterior(26.0, 1.0, 3, 0.001));

        try {
            naive.read("database.sqlite");
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = naive.getData();
        double reduceData = naive.getReduceData();
        //System.out.println(Arrays.toString(data));
        System.out.println(reduceData);
        try {
            naive.convert(data, "image", ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

