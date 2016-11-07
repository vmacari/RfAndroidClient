package demo.sensors.md.shouse_sensors.util;

import android.util.Log;

/**
 * Created by elisita on 9/5/16.
 */

public class Logger {

    public static void d(String message) {
        if (Utils.isInDebug()) {
            Log.d(Const.RELAX_LOG_TAG, message);
        }
    }

    public static void e(String message) {
        if (Utils.isInDebug()) {
            Log.e(Const.RELAX_LOG_TAG, message);
        }
    }

    public static void v(String message) {
        if (Utils.isInDebug()) {
            Log.v(Const.RELAX_LOG_TAG, message);
        }
    }

    public static void i(String message) {
        if (Utils.isInDebug()) {
            Log.i(Const.RELAX_LOG_TAG, message);
        }
    }
}
