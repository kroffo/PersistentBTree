import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.FileOutputStream;
import java.io.File;

public class ByteWriter {

    private FileOutputStream stream;
    private int offset;

    public ByteWriter(File file) throws FileNotFoundException {
        stream = new FileOutputStream(file);
        offset = 0;
    }

    /* inserts double into the file
     *
     * returns the insertion's position offset
     *
     */
    public int writeDouble(double value) {
        byte[] bytes = toByteArray(value);
        try {
            stream.write(bytes);
            int off = offset;
            offset += bytes.length;
            return off;
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

    private byte[] toByteArray(double value) {
        byte[] bytes = new byte[Double.BYTES];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

}
