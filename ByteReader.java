import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteReader {

    private FileInputStream stream;
    private int offset;

    public ByteReader(File file) throws FileNotFoundException {
        stream = new FileInputStream(file);
        offset = 0;
    }

    /* reads double from the file
     *
     */
    public double readDouble(int offset) {
        byte[] bytes = new byte[Double.BYTES];
        try {
            stream.skip(offset);
            stream.read(bytes, 0, bytes.length);
            return toDouble(bytes);
        } catch(IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void close() {
        try {
            stream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
