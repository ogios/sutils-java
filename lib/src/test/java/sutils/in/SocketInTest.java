package sutils.in;

import org.junit.Test;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SocketInTest {
    @Test public void socketIn() throws Exception {
        System.out.println("running socket in test");
        ServerSocket server = new ServerSocket(15002);
        System.out.println("wating for connection");
        Socket client = server.accept();
        System.out.println("connection received");
        // byte[] b = client.getInputStream().readAllBytes();
        // System.out.println(Arrays.toString(b));
        SocketIn si = SocketIn.newSocketIn(client.getInputStream());
        System.out.println("next");
        int length = si.next();
        System.out.println("length: " + length);
        byte[] sec = si.getSec();
        System.out.println("sec: " + Arrays.toString(sec));
        client.close();
        server.close();
    }
}
