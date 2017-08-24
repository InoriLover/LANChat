package fishy.sample.lanchatsample;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by nanheng on 2017/8/22.
 */

public class ConnectConfig {
    public static final int port = 8090;
    public static String tempIp = "";


    public static String getLocalIp() {
        WifiManager wm = (WifiManager) SimpleApplication.getContext().getSystemService(Context.WIFI_SERVICE);
        //检查Wifi状态
        if (wm.isWifiEnabled()) {
            WifiInfo wi = wm.getConnectionInfo();
            if (wi == null) {
                return null;
            } else {
                //获取32位整型IP地址
                int ipAdd = wi.getIpAddress();
                //把整型地址转换成“*.*.*.*”地址
                String ip = intToIp(ipAdd);
                return ip;
            }
        } else {
            return "";
        }
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
