package fishy.sample.lanchatsample;

import android.app.Application;
import android.content.Context;

/**
 * Created by nanheng on 2017/8/23.
 */

public class SimpleApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
