package recstation.lkk.com.recstation.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lkk on 2019/1/15.
 */
public class Logger {

    public static boolean openLog = true;
    public static String defaultTag = "kangshen.default.log";


    public static void d(String msg) {
        d(null, msg);
    }

    public static void d(String tag, String msg) {
        if (openLog) {
            dealMsg(LogType.d, dealTag(tag), msg);
        }
    }


    public static void d(String msg, Throwable tr) {
        d(null, msg, tr);

    }

    public static void d(String tag, String msg, Throwable tr) {
        if (openLog) {
            Log.d(dealTag(tag), msg, tr);
        }
    }


    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        if (openLog) {
            dealMsg(LogType.e, dealTag(tag), msg);
        }

    }
    
    public static void e(Throwable tr){
    	e(null,"",tr);
    }

    public static void e(String msg, Throwable tr) {
        e(null, msg, tr);

    }

    public static void e(String tag, String msg, Throwable tr) {
        if (openLog) {
            Log.e(dealTag(tag), msg, tr);
        }
    }


    public static void i(String msg) {
        i(null, msg);
    }

    public static void i(String tag, String msg) {
        if (openLog) {
            dealMsg(LogType.i, dealTag(tag), msg);
        }

    }

    public static void i(String msg, Throwable tr) {
        i(null, msg, tr);

    }

    public static void i(String tag, String msg, Throwable tr) {
        if (openLog) {
            Log.i(dealTag(tag), msg, tr);
        }
    }


    public static void v(String msg) {
        v(null, msg);
    }

    public static void v(String tag, String msg) {
        if (openLog) {
            dealMsg(LogType.v, dealTag(tag), msg);
        }

    }

    public static void v(String msg, Throwable tr) {
        v(null, msg, tr);

    }

    public static void v(String tag, String msg, Throwable tr) {
        if (openLog) {
            Log.v(dealTag(tag), msg, tr);
        }
    }


    public static void w(String msg) {
        w(null, msg);
    }

    public static void w(String tag, String msg) {
        if (openLog) {

            dealMsg(LogType.w, dealTag(tag), msg);
        }

    }

    public static void w(String msg, Throwable tr) {
        w(null, msg, tr);

    }

    public static void w(String tag, String msg, Throwable tr) {
        if (openLog) {
            Log.w(dealTag(tag), msg, tr);
        }
    }


    public static void wtf(String msg) {
        wtf(null, msg);
    }

    public static void wtf(String tag, String msg) {
        if (openLog) {
            dealMsg(LogType.wtf, dealTag(tag), msg);
        }

    }

    public static void wtf(String msg, Throwable tr) {
        wtf(null, msg, tr);

    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (openLog) {
            Log.wtf(dealTag(tag), msg, tr);
        }
    }


    /**
     * 解决报文过长，打印不全的问题！
     *
     * @param logType
     * @param tag
     * @param msg
     */
    private static void dealMsg(LogType logType, String tag, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            int length = msg.length();
            int offset = 3000;
            if (length > offset) {
                int n = 0;
                for (int i = 0; i < length; i += offset) {
                    n += offset;
                    if (n > length) n = length;
                    printMsg(logType, tag, msg.substring(i, n));
                }
            } else {
                printMsg(logType, tag, msg);
            }
        }
    }

    private static String dealTag(String tag) {
        return (tag == null || "".equals(tag)) ? defaultTag : tag;

    }

    private static void printMsg(LogType logType, String tag, String msg) {
        switch (logType) {
            case d:
                Log.d(tag, msg);
                break;
            case i:
                Log.i(tag, msg);
                break;
            case e:
                Log.e(tag, msg);
                break;
            case v:
                Log.v(tag, msg);
                break;
            case w:
                Log.w(tag, msg);
                break;
            case wtf:
                Log.wtf(tag, msg);
                break;

        }

    }


    private enum LogType {
        d, i, e, v, w, wtf
    }

}
