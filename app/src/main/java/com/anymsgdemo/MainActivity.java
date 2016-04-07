package com.anymsgdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.nativeapi.AnyMSG;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    WebView wv;
    private Handler ui_main_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ui_main_handler = new Handler(this.getMainLooper(), new MyCallback());

        wv = (WebView) findViewById(R.id.webView);
        WebSettings setting = wv.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("GBK");//设置字符编码
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        setting.setSupportZoom(false);
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.setWebChromeClient(new WebViewJava2JS(ui_main_handler));
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


    }


    public class MyCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            //UI消息处理
            switch (msg.what) {
                case 1:
                    Toast toast = Toast.makeText(getApplicationContext(),
                            msg.obj.toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                default:
                    break;
            }


            return false;
        }
    }
}
