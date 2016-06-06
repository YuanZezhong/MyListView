package yuanyi.com.mylistview.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by admin on 2016/6/6.
 */
public class BaseApp extends Application{
    public static Context appContext;
    private static boolean isLog = true;
    private static final String TAG = "tag";

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static void log(String log) {
        if (isLog) {
            Log.i(TAG, log);
        }
    }
}
