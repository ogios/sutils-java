package sutils.out;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import sutils.out.SocketOut;
import java.util.Arrays;

public class SocketOutTest {
    @Test public void socketOut() throws Exception {
        System.out.println("running socket in test");
        System.out.println("connected");
        Socket socket = new Socket("localhost", 15002);
        SocketOut si = SocketOut.newSocketOut();
        // byte[] bs = "焚烧法那萨芬爱睡觉的撒娇的阿萨德吉萨的及阿森纳的撒娇看到那数据库能打上尽可能的及扩散到尽可能撒娇看到那数据库鼻内镜卡萨诺大家卡萨诺库的撒娇看到那撒娇库能打卡斯基宁的空间阿森纳及看到那撒娇看到编辑昂首阔步登记卡说不定金卡上半年的及卡萨诺空间的那撒娇库不能的空间撒贝宁的金卡上半年空间代表那撒娇看到包括节哀顺变金卡上".getBytes("UTF-8");
        // System.out.println(bs.length);
        // ByteArrayInputStream bo = new ByteArrayInputStream(bs);
        si.addBytes(new byte[0]);
        si.writeTo(socket.getOutputStream());
        socket.close();
    }
}
