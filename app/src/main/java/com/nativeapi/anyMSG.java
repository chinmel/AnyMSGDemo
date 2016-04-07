package com.nativeapi;

import com.anymsgdemo.MainActivity;

/**
 * Created by chinmel on 16/4/5.
 */

public class AnyMSG {
    static {
        System.loadLibrary("anyMSGNativeAPI");
    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnMSGLoginListener {
        void onMSGLogin();
    }

    private static OnMSGLoginListener mOnMSGLoginListener;//注意使用静态

    public void setOnMSGLoginListener(OnMSGLoginListener l) {
        mOnMSGLoginListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnMSGLogin() {
        //()V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnMSGLoginListener.onMSGLogin();
            }
        });

    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnMSGWelcomeListener {
        void onMSGWelcome(String uid, String cid);
    }

    private static OnMSGWelcomeListener mOnMSGWelcomeListener;//注意使用静态

    public void setOnMSGWelcomeListener(OnMSGWelcomeListener l) {
        mOnMSGWelcomeListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnMSGWelcome(final String uid, final String cid) {
//        (Ljava/lang/String;Ljava/lang/String;)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnMSGWelcomeListener.onMSGWelcome(uid, cid);
            }
        });

    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnMSGHeartBeatListener {
        void onMSGHeartBeat(String uid, String cid);
    }

    private static OnMSGHeartBeatListener mOnMSGHeartBeatListener;//注意使用静态

    public void setOnMSGHeartBeatListener(OnMSGHeartBeatListener l) {
        mOnMSGHeartBeatListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnMSGHeartBeat(final String uid, final String cid) {
//        (Ljava/lang/String;Ljava/lang/String;)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnMSGHeartBeatListener.onMSGHeartBeat(uid, cid);
            }
        });

    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnMSGHeartBeatReplyListener {
        void onMSGHeartBeatReply(String uid, String cid);
    }

    private static OnMSGHeartBeatReplyListener mOnMSGHeartBeatReplyListener;//注意使用静态

    public void setOnMSGHeartBeatReplyListener(OnMSGHeartBeatReplyListener l) {
        mOnMSGHeartBeatReplyListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnMSGHeartBeatReply(final String uid, final String cid) {
//        (Ljava/lang/String;Ljava/lang/String;)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnMSGHeartBeatReplyListener.onMSGHeartBeatReply(uid, cid);
            }
        });

    }
    //===========================================================

    public native static String login(String jsonString);

    public native static void call();
}