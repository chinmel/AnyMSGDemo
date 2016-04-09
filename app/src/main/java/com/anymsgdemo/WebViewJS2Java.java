package com.anymsgdemo;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by chinmel on 16/4/6.
 */
public class WebViewJS2Java {
    /**
     * This is not called on the UI thread. Post a runnable to invoke
     * loadUrl on the UI thread.
     */
    //一定不要忘记加上注解
    @JavascriptInterface
    public void clickOnAndroid(final String uid) {
        Log.i("anyMSG", "clickOnAndroid: " + uid);

        MainActivity.s(Integer.valueOf(uid));
//        mHandler.post(new Runnable() {
//            public void run() {
//                final JSONObject j = new JSONObject();
//                try {
//                    j.put("api", "anyMSGNative");
//                    j.put("ver", "1.2.40");
//                    j.put("domain", "192.168.2.104");
//                    j.put("port", 9000);
//                    j.put("uid", 123456);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                new anyMSG(ui_main_handler).login(j.toString());
//
//            }
//        });

    }

    //一定不要忘记加上注解
    @JavascriptInterface
    public void clickOnSend(String cid, String message) {
        MainActivity.send(Integer.parseInt(cid, 10), message);
    }
}
