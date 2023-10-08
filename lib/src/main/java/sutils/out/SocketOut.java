package sutils.out;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;





public class SocketOut {
    
    public final static int OUT_TYPE_BYTES = 1;
    public final static int OUT_TYPE_READER = 2;
    public final static int SLICE_CAPACITY = 6;
    public final static int ContentLengthMax = 8;

	public ArrayList<Object> raw;
	public ArrayList<Integer> types;
    public int p;

    private SocketOut(){}

    public static SocketOut newSocketOut() {
        SocketOut si = new SocketOut();
        si.raw = new ArrayList<>();
        si.types = new ArrayList<>();
        return si;
    }

    private void add(Object raw, int t, byte[] lenb) {
        this.raw.add(lenb);
        this.raw.add(raw);
        this.types.add(t);
    }

    private byte[] main(int last, int index) {
        byte[] bytes;
        if (last >= 255) {
            int current = last % 255;
            bytes = this.main(last/255, index+1);
            bytes[index] = (byte)current;
        } else {
            bytes = new byte[index+2];
            bytes[index] = (byte)last;
            bytes[index+1] = (byte)255;
        }
        return bytes;
    }

    private byte[] getLength(int length) {
        return main(length, 0);
    }

    public void addBytes(byte[] raw) throws Exception {
        int length = raw.length;
        byte[] content_length = this.getLength(length);
        this.add(raw, OUT_TYPE_BYTES, content_length);
    }

    public void addReader(InputStream raw, int length) throws Exception {
        byte[] content_length = this.getLength(length);
        this.add(raw, OUT_TYPE_READER, content_length);
    }

    public void writeTo(OutputStream output) throws Exception {
        DataOutputStream writer = new DataOutputStream(new  BufferedOutputStream(output));

        for (int i=0;i < this.raw.size(); i++) {
            Object input = this.raw.get(i);
            if (input == null) continue;
            if (i%2 == 0) {
                writer.write((byte[])input);
            } else {
                int t = this.types.get((i-1)/2);
                switch (t) {
                    case SocketOut.OUT_TYPE_BYTES:
                        writer.write((byte[])input);
                        writer.flush();
                        break;
                    case SocketOut.OUT_TYPE_READER:
                        DataInputStream reader = new DataInputStream(new BufferedInputStream((InputStream)input));
                        byte[] temp = new byte[1024];
                        int read;
                        while (true) {
                            read = reader.read(temp);
                            if (read == -1) {
                                break;
                            }
                            writer.write(temp, 0, read);
                            writer.flush();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        writer.flush();
    }
}
