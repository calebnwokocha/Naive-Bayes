/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

import AI.Naive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        ySample.add((byte) 14);
        ySample.add((byte) 0);
        ySample.add((byte) 4);
        ySample.add((byte) 5);

        Naive naive = new Naive(xSample, ySample, "database.sqlite", 10);
        System.out.println(naive.bayes((byte) 5, (byte) 1));
        System.out.println(naive.getProbability((byte) 5, (byte) 1));
        System.out.println(naive.bayes((byte) 0, (byte) 1));
        System.out.println(naive.getProbability((byte) 0, (byte) 1));
        System.out.println(naive.bayes((byte) 6, (byte) 1));
        System.out.println(naive.getProbability((byte) 6, (byte) 1));
        System.out.println(naive.bayes((byte) 14, (byte) 1));
        System.out.println(naive.getProbability((byte) 14, (byte) 1));

        try {
            naive.read("C:\\Users\\Caleb P. Nwokocha\\Documents\\Naive-Bayes\\CamScanner 01-02-2023 13.30 (1).jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = naive.getData();
        byte reduceData = naive.getReduceData();
        //System.out.println(Arrays.toString(data));
        System.out.println(reduceData);
        try {
            naive.convert(data, "image", ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

