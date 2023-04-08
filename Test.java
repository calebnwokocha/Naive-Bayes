/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

public class Test {
    public static void main(String[] args) {
        byte[] xSample = new byte[] {1, 1, 10, 1, 1};
        byte[] ySample = new byte[] {5, 5, 9, 7, 8};
        Naive naive = new Naive(xSample, ySample);
        System.out.println(naive.bayes((byte) 5, (byte) 1));
    }
}
