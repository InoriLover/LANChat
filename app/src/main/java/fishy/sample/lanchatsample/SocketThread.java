package fishy.sample.lanchatsample;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nanheng on 2017/8/23.
 */

public class SocketThread extends Thread {
    final String strSuccess = "receive Msg Success";
    boolean flag = true;
    Socket client;

    SocketThread(Socket client) {
        this.client = client;
        flag = true;
    }

    @Override
    public void run() {
        try {
            while (flag) {
                boolean isSendSuccess = receiveMsg(client);
                if (isSendSuccess) {
                    sendMsg(client, "");
                }
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {

        }
    }

    void sendMsg(Socket socket, String msg) {
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(strSuccess.getBytes());
            bos.flush();
            //update UI
            EventBus.getDefault().post(new MsgEvent("sendMsg-->" + strSuccess));
        } catch (IOException e) {

        }
    }

    boolean receiveMsg(Socket socket) {
        InputStream is = null;
        try {
            is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            int count = bis.available();
            byte[] bytes = new byte[count];
            if (bis.read(bytes) > 0) {
                String str = new String(bytes, Charset.forName("UTF-8"));
                //update UI
                EventBus.getDefault().post(new MsgEvent("receiveMsg-->" + str));
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
