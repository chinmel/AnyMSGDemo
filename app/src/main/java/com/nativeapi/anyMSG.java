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
    public interface OnLoginListener {
        void onLogin();
    }

    private static OnLoginListener mOnLoginListener;//注意使用静态

    public void setOnLoginListener(OnLoginListener l) {
        mOnLoginListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnLogin() {
        //()V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnLoginListener.onLogin();
            }
        });
    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnWelcomeListener {
        void onWelcome(String uid, String cid);
    }

    private static OnWelcomeListener mOnWelcomeListener;//注意使用静态

    public void setOnWelcomeListener(OnWelcomeListener l) {
        mOnWelcomeListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnWelcome(final String uid, final String cid) {
//        (Ljava/lang/String;Ljava/lang/String;)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnWelcomeListener.onWelcome(uid, cid);
            }
        });
    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnHeartBeatReceivedListener {
        void onHeartBeatReceived(String uid, String cid);
    }

    private static OnHeartBeatReceivedListener mOnHeartBeatReceivedListener;//注意使用静态

    public void setOnHeartBeatReceivedListener(OnHeartBeatReceivedListener l) {
        mOnHeartBeatReceivedListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnHeartBeatReceived(final String uid, final String cid) {
//        (Ljava/lang/String;Ljava/lang/String;)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnHeartBeatReceivedListener.onHeartBeatReceived(uid, cid);
            }
        });
    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnHeartBeatSentListener {
        void onHeartBeatSent(String uid, String cid);
    }

    private static OnHeartBeatSentListener mOnHeartBeatSentListener;//注意使用静态

    public void setOnHeartBeatSentListener(OnHeartBeatSentListener l) {
        mOnHeartBeatSentListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnHeartBeatSent(final String uid, final String cid) {
//        (Ljava/lang/String;Ljava/lang/String;)V

        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnHeartBeatSentListener.onHeartBeatSent(uid, cid);
            }
        });
    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnMessageReceivedListener {
        void onMessageReceived(String cid, byte[] messageByteArray);
    }

    private static OnMessageReceivedListener mOnMessageReceivedListener;//注意使用静态

    public void setOnMessageReceivedListener(OnMessageReceivedListener l) {
        mOnMessageReceivedListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnMessageReceived(final String cid, final byte[] messageByteArray) {
//        (Ljava/lang/String;[B)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnMessageReceivedListener.onMessageReceived(cid, messageByteArray);
            }
        });

    }

    //===========================================================
    //以下是消息事件的回调
    //===========================================================
    public interface OnMessageSentListener {
        void onMessageSent(String cid, String verifyStringReceived);
    }

    private static OnMessageSentListener mOnMessageSentListener;//注意使用静态

    public void setOnMessageSentListener(OnMessageSentListener l) {
        mOnMessageSentListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnMessageSent(final String cid, final String verifyStringReceived) {
//        (Ljava/lang/String;Ljava/lang/String;)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnMessageSentListener.onMessageSent(cid, verifyStringReceived);
            }
        });

    }

    //===========================================================
//以下是消息事件的回调
//===========================================================
    public interface OnMessageSendingListener {
        void onMessageSending(String cid, String verifyStringReceived);
    }

    private static OnMessageSendingListener mOnMessageSendingListener;//注意使用静态

    public void setOnMessageSendingListener(OnMessageSendingListener l) {
        mOnMessageSendingListener = l;
    }

    //NDK回调方法
    //使用static方法，NDK调用时不再需要动态定位
    //将来的版本可能会取消该方法
    private static void mOnMessageSending(final String cid, final String verifyStringReceived) {
//        (Ljava/lang/String;Ljava/lang/String;)V
        MainActivity.wv.post(new Runnable() {
            @Override
            public void run() {
                mOnMessageSendingListener.onMessageSending(cid, verifyStringReceived);
            }
        });

    }

    //===========================================================
    public native static String login(String jsonString);

    public native static String sendto(int destinationCID, byte[] byteBuffer, int needRecord);

    public native static void call();
}
