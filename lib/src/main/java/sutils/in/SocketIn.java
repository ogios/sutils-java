package sutils.in;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SocketIn {

	public InputStream raw;
	public int readed;
	public int CurrSecLength;

    private SocketIn() {}

    public static SocketIn newSocketIn(InputStream in) {
        SocketIn si = new SocketIn();
        si.raw = in;
        return si;
    }

    private int main(int index, int t) throws Exception {
        byte[] b = raw.readNBytes(1);
        if (b.length != 1) {
            throw new Exception("readNBytes fails to work" + Arrays.toString(b));
        }
        int current = Byte.toUnsignedInt(b[0]);
        if (current != 255) {
            t = this.main(index+1, t);
            double feat = Math.pow(255, index);
            int addon = current*(int)feat;
            t += addon;
        }
        return t;

    }

    public int next() throws Exception{
        if (this.CurrSecLength < this.readed) {
            throw new Exception("please read all of current section");
        }
        int total = this.main(0, 0);
        this.CurrSecLength = total;
        this.readed = 0;
        return total;
    }

    public byte[] getSec() throws Exception {
        if (this.readed < this.CurrSecLength) {
            byte[] bs = new byte[this.CurrSecLength-this.readed];
            int readed = this.raw.read(bs);
            this.readed += readed;
            return bs;
        } else {
            int length = this.next();
            byte[] temp = new byte[length];
            int readlength = this.raw.read(temp);
            if (readlength != length) {
            }
            return temp;
        }
    }

    public int read(byte[] buf) throws Exception {
        if (this.CurrSecLength == this.readed) {
            throw new Exception("no more bytes for current section");
        }
        if (buf.length <= this.CurrSecLength-this.readed) {
            int i = this.raw.read(buf);
            this.readed += i;
            return i;
        } else {
            byte[] temp = new byte[this.CurrSecLength-this.readed];
            int length = this.raw.read(temp);
            this.readed += length;
            System.arraycopy(temp, 0, buf, 0, length);
            return length;
        }
    }
    



  
}
