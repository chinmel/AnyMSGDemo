package com.nativeapi;

/**
 * Created by chinmel on 16/4/6.
 */
import android.os.Handler;

public class anyMSG {
    public static native String login(String jsonString);
    private Handler mainUI;
    static {
        System.loadLibrary("anyMSGNativeAPI");
    }

    public anyMSG(Handler uiHandler){
        mainUI = uiHandler;
    }
    //=========================================================


}