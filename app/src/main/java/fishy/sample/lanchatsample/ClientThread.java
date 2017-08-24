package fishy.sample.lanchatsample;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by nanheng on 2017/8/22.
 */

public class ClientThread extends Thread {
    String test = "my name is fishy!";
    Socket socket;
    boolean flag;
    boolean runFlag;

    public ClientThread() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("size", "003000");
            jsonObject.put("cmd", "1");
            jsonObject.put("id", "00000001");
            jsonObject.put("content", "wiggle wiggle little fish!");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        test = jsonObject.toString() + "\n\n";
        runFlag=true;
        flag = true;
    }

    public void sendMsg() {
        flag = true;
    }

    @Override
    public void run() {
        try {
//            if (socket == null) {
            socket = new Socket(ConnectConfig.tempIp, ConnectConfig.port);
//            }
            while (runFlag) {
                while (flag) {
                    sendMsg(socket, "");
                    while (!receiveMsg(socket)) {
                        Thread.sleep(200);
                    }
                    flag = false;
                }
            }
            //todo thread.sleep is not used in most scenes
//            receiveMsg(socket);
//            socket.shutdownInput();
//            socket.shutdownOutput();
//            socket.close();
        } catch (IOException e) {

        } catch (InterruptedException e) {

        }
    }

    void sendMsg(Socket socket, String msg) {
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(test.getBytes());
            bos.flush();
            //update UI
            EventBus.getDefault().post(new MsgEvent("sendMsg-->" + test));
        } catch (IOException e) {
            e.printStackTrace();
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

    public void disConnect() {
        runFlag = false;
    }
}
