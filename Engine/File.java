package Engine;

/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
    private byte[] data;
    private final double base;

    public File(double base) { this.base = base; }

    public void read (String fileName) throws IOException {
        Path path = Paths.get(fileName);
        this.data = Files.readAllBytes(path);
    }

    public byte getData () { return reduce(this.data); }

    private byte reduce (byte[] vector) {
        byte sum = 0;
        for (int i = 1; i <= vector.length; i++) {
            sum += vector[i - 1] * Math.pow(this.base, vector.length - i);
        } return sum;
    }
}