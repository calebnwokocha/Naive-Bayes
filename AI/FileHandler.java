/*------------------------------------------------------------------------------
 Author: Caleb Princewill Nwokocha
 Emails: calebnwokocha@gmail.com, nwokochc@myumanitoba.ca
---------------------------------------------------------------------------- */

package AI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileOutputStream;

class FileHandler {
    private byte[] data;
    private final double base;

    protected FileHandler(double base) { this.base = base; }

    public void read (String fileName) throws IOException {
        Path path = Paths.get(fileName);
        this.data = Files.readAllBytes(path);
    }

    public void convert(byte[] data, String fileName, String fileExtension)
            throws IOException, IllegalArgumentException, NullPointerException
    {
        File file = new File(fileName + fileExtension);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(data);
            outputStream.flush();
        }
    }

    public byte[] getData() { return this.data; }

    public byte getReduceData() { return reduce(this.data); }

    private byte reduce (byte[] vector) {
        byte sum = 0;
        for (int i = 1; i <= vector.length; i++) {
            sum += vector[i - 1] * Math.pow(this.base, vector.length - i);
        } return sum;
    }
}
