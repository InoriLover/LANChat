package fishy.sample.lanchatsample;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nanheng on 2017/8/23.
 */

public class TempIpInfo {
    static String fileName = "tempIpInfo";
    static String keyIp = "key_ip";

    static void recordRecentIp(String newIp) {
        SharedPreferences.Editor editor = SimpleApplication.getContext()
                .getSharedPreferences(fileName, Context.MODE_PRIVATE)
                .edit();
        editor.putString(keyIp, newIp);
        editor.apply();
        //相当于一个全局缓存
        ConnectConfig.tempIp = newIp;
    }

    static String readRecentIp() {
        SharedPreferences preferences = SimpleApplication.getContext()
                .getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String recentIp = preferences.getString(keyIp, "");
        return recentIp;
    }
}
