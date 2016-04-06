package com.anymsgdemo;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by chinmel on 16/4/6.
 */
public class WebViewJava2JS extends WebChromeClient {
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d("anyMSGDemo", message);
        result.confirm();
        return true;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("anyMSGDemo", url);
        view.loadUrl(url);   //在当前的webview中跳转到新的url

        return true;
    }

}

