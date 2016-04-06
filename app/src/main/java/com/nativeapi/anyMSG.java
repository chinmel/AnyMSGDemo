package com.nativeapi;

/**
 * Created by chinmel on 16/4/5.
 */
public class AnyMSG {
    static {
        System.loadLibrary("anyMSGNativeAPI");
    }

    public AnyMSG() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                login(" ");
//            }
//        }).start();

    }
    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    private static OnMSGLoginListener mOnMSGLoginListener;//注意使用静态
    public void setOnMSGLoginListener(OnMSGLoginListener l) {
        mOnMSGLoginListener = l;
    }
    public interface OnMSGLoginListener {
        int onMSGlogin();
    }
    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static int mOnMSGlogin() {
        return (mOnMSGLoginListener.onMSGlogin());
    }

    //===========================================================


    public native static String login(String jsonString);
    public native static void call();
}