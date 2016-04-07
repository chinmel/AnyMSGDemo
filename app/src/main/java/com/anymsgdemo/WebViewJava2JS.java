package com.anymsgdemo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by chinmel on 16/4/6.
 */
public class WebViewJava2JS extends WebChromeClient {
    private Handler msgHandler;

    public WebViewJava2JS(Handler h) {
        msgHandler = h;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d("anyMSGDemo", message);
        result.confirm();

        Message msgs = msgHandler.obtainMessage();
        msgs.what = 1;
        msgs.obj = message;
        msgHandler.sendMessage(msgs);

        return true;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("anyMSGDemo", url);
        view.loadUrl(url);   //在当前的webview中跳转到新的url

        return true;
    }

    //==================================================================
    //        JSONObject j = new JSONObject();
//        JSONObject opr = new JSONObject();
//        try {
//            j.put("opr", opr);
////                    j.put("port", 9000);
//            opr.put("code", 2001);
//            opr.put("desp", 98721);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        wv.loadUrl("javascript:anyMSGLoginCallbackFNC('" + j +"')");
}

