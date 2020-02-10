package com.colin.unittest;

import android.os.Environment;
import android.util.Log;

/**
 * Created by colin 2020-02-06.
 */
public class FileUtil {
    public static final String ROOT_DIR = Environment.getRootDirectory().getAbsolutePath();

    public static boolean DEBUG;

    public static void init(boolean isDebug) {
        Log.d("FileUtil", "init: ");
        //伪代码
        DEBUG = isDebug;
    }
}
