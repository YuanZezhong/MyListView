package yuanyi.com.mylistview.util;

import android.content.Context;

/**
 * Created by admin on 2016/6/6.
 */
public class DensityUtils {
    private DensityUtils() {}

    public static int dp2px(Context context, float dpVal) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * density + 0.5F);
    }

    public static int px2dp(Context context, float pxVal) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxVal / density + 0.5F);
    }

    public static int sp2px(Context context, float spVal) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * scaleDensity + 0.5);
    }

    public static int px2sp(Context context, float pxVal) {
        float scaleDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxVal / scaleDensity + 0.5f);
    }
}
