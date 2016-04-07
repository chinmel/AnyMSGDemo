package com.anymsgdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.nativeapi.AnyMSG;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv = (WebView) findViewById(R.id.webView);
        WebSettings setting = wv.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("GBK");//设置字符编码
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        setting.setSupportZoom(false);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.setWebChromeClient(new WebViewJava2JS());
        wv.addJavascriptInterface(new WebViewJS2Java(), "webViewJS2Java");
        wv.loadUrl("file:///android_asset/index.html");


        final AnyMSG demo = new AnyMSG();

        demo.setOnMSGLoginListener(new AnyMSG.OnMSGLoginListener() {
            @Override
            public void onMSGLogin() {
                Log.d("anyMSGDemo", "onMSGLogin");
            }
        });

        demo.setOnMSGWelcomeListener(new AnyMSG.OnMSGWelcomeListener() {
            @Override
            public void onMSGWelcome(String uid, String cid) {
                Log.d("anyMSGDemo", "onMSGWelcome: uid[" + uid + "],cid[" + cid + "]");
            }
        });

        demo.setOnMSGHeartBeatListener(new AnyMSG.OnMSGHeartBeatListener() {
            @Override
            public void onMSGHeartBeat(String uid, String cid) {
                Log.d("anyMSGDemo", "onMSGHeartBeat: uid[" + uid + "],cid[" + cid + "]");
            }
        });

        demo.setOnMSGHeartBeatReplyListener(new AnyMSG.OnMSGHeartBeatReplyListener() {
            @Override
            public void onMSGHeartBeatReply(String uid, String cid) {
                Log.d("anyMSGDemo", "onMSGHeartBeatReply: uid[" + uid + "],cid[" + cid + "]");
            }
        });


        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                demo.call();

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject j = new JSONObject();
                JSONObject opr = new JSONObject();
                try {
                    j.put("api", "anyMSGNative");
                    j.put("version", "1.2.40");
                    j.put("domain", "192.168.2.101");
                    j.put("port", 9000);
                    j.put("uid", 98721);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                demo.login(j.toString());
            }
        }).start();
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


    private Handler ui_main_handler;
    private Handler mHandler = new Handler();

//    public class MyCallback implements Handler.Callback {
//        @Override
//        public boolean handleMessage(Message msg) {
//            //UI消息处理
//
//
//            return false;
//        }
//    }
}
