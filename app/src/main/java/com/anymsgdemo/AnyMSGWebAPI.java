package com.anymsgdemo;

import android.util.Log;
import android.webkit.WebView;

import org.json.JSONObject;

/**
 * Created by chinmel on 16/4/8.
 */
public class AnyMSGWebAPI {
    public static void uiEventTrigger(WebView wv, JSONObject jsonObj) {
//        Log.d("AnyMSGDemo", "uiEventTrigger: " + jsonObj.toString());
        wv.loadUrl("javascript:anyMSGLoginCallbackFNC('" + jsonObj + "')");
    }

    public static void uiEventTriggerInString(WebView wv, String jsonString) {
        wv.loadUrl("javascript:anyMSGLoginCallbackFNC('" + jsonString + "')");
    }

    public static void uiEventTriggerToString(WebView wv, JSONObject jsonObj) {
//        Log.d("AnyMSGDemo", "uiEventTriggerToString: " + jsonObj.toString());
        wv.loadUrl("javascript:anyMSGLoginCallbackFNC('" + jsonObj.toString() + "')");
    }
}
