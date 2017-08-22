package fishy.sample.lanchatsample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nanheng on 2017/8/22.
 */

public class ServerThread extends Thread {
    public ServerThread() {

    }

    @Override
    public void run() {
        super.run();
        try {
            ServerSocket serverSocket=new ServerSocket(ConnectConfig.port);
            Socket client=serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
