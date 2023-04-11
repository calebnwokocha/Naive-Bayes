/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

import AI.Bayes;

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
        ySample.add(21.0);
        ySample.add(25.0);
        ySample.add(20.0);
        ySample.add(26.0);
        ySample.add(20.0);

        Bayes bayes = new Bayes("database.sqlite", 0.2);
        bayes.train(ySample, xSample);
        //System.out.println(bayes.getPosterior(27, 1.0, 2.0, 1000000000.0));
        System.out.println(bayes.predict(1));


        try {
            bayes.read("database.sqlite");
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = bayes.getData();
        double reduceData = bayes.getReduceData();
        //System.out.println(Arrays.toString(data));
        //System.out.println(reduceData);
        try {
            bayes.convert(data, "image", ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

