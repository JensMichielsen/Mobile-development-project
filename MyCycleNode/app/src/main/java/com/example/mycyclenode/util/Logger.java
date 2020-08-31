package com.example.mycyclenode.util;

import android.util.Log;

public class Logger {
    public static void infoLog(String tag, String event) {
        Log.i(tag, "Event: " + event);
    }

    public static void infoLog(String tag, String event, Object object) {
        Log.i(tag, "Event: " + event + " - " + object.toString());
    }
}
