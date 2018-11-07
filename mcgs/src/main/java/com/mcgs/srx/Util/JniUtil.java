package com.mcgs.srx.Util;

public class JniUtil {

    private static JniUtil mInstance;

    public static JniUtil getInstance(){
        if(mInstance == null){
            mInstance = new JniUtil();
        }
        return mInstance;
    }

    static {
        System.loadLibrary("mcgs_jni");
    }

    //USB mode set
    public native int getUsbMode();

    public native int setUsbMode(int mode);

    //Bee mode set
    public native int setBeeTime(int time);

    //LCD mode set
    public native int getLcdStatus();

    public native int setLcdStatus(int status);

    public native int getBacklightbrightness();

    public native int setBacklightbrightness(int value);


    //RTC set
    public native int getRtcStatus();

    public native int getRtcTime();

    public native int setRtcTime(int time);

}
