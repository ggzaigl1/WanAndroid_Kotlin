package com.ggz.baselibrary.utils;

import android.os.Build;

/**
 * 获取设备信息 工具类
 * Created by fangs on 2017/3/22.
 */
public class DeviceUtils {

    private DeviceUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取 手机 版本号
     *
     * @return
     */
    public static String getDeviceVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceMake() {
        return Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
//        return null;
//    }

    /**
     * 打印设备内存信息
     */
    public static void getDevices(){
        //应用程序最大可用内存
        int maxMemory = ((int) Runtime.getRuntime().maxMemory())/1024/1024;
        //应用程序已获得内存
        long totalMemory = ((int) Runtime.getRuntime().totalMemory())/1024/1024;
        //应用程序已获得内存中未使用内存
        long freeMemory = ((int) Runtime.getRuntime().freeMemory())/1024/1024;
        System.out.println("---> maxMemory="+maxMemory+"M,totalMemory="+totalMemory+"M,freeMemory="+freeMemory+"M");

        LogUtils.e("maxMemory" + maxMemory + "totalMemory" + totalMemory + "freeMemory" + freeMemory);
    }
}
