package sutils.out;
import java.util.ArrayList;





public class SocketOut {
    
    public static int OUT_TYPE_BYTES = 1;
    public static int OUT_TYPE_READER = 2;
    public static int SLICE_CAPACITY = 6;
    public static int ContentLengthMax = 8;

	public ArrayList<Object> raw;
	public ArrayList<Integer> types;
    public int p;

    public static SocketOut newSocketIn() {
        SocketOut si = new SocketOut();
        return si;
    }

    private void add(Object raw, int t, byte[] lenb) {
        this.raw.add(lenb);
        this.raw.add(raw);
    }

    private 
}
