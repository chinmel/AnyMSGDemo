package com.anymsgdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.nativeapi.AnyMSG;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    public static WebView wv;
    static final AnyMSG demo = new AnyMSG();
    private Handler ui_main_handler;
    public Handler mHandler = new Handler();

    int ownCID = 0xFFFFFFFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ui_main_handler = new Handler(this.getMainLooper(), new MyCallback());

        wv = (WebView) findViewById(R.id.webView);
        wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wv.setBackgroundColor(Color.TRANSPARENT);
// mWebView.setBackgroundResource(Color.TRANSPARENT);
        if (wv.getBackground() != null) {
            wv.getBackground().setAlpha(2);
        }
        WebSettings setting = wv.getSettings();
        setting.setJavaScriptCanOpenWindowsAutomatically(false);
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("GBK");//设置字符编码
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        setting.setDisplayZoomControls(false);
        setting.setBuiltInZoomControls(false); // 设置显示缩放按钮
        setting.setSupportZoom(false); // 支持缩放
        setting.setUseWideViewPort(true);//关键点
        setting.setLoadWithOverviewMode(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        wv.setWebChromeClient(new WebViewOverride(ui_main_handler));
        wv.addJavascriptInterface(new WebViewJS2Java(), "webViewJS2Java");
        wv.loadUrl("file:///android_asset/index.html");

//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int mDensity = metrics.densityDpi;
//        Log.d("maomao", "densityDpi = " + mDensity);
//        if (mDensity == 240) {
//            setting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == 160) {
//            setting.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        } else if(mDensity == 120) {
//            setting.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
//            setting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        }else if (mDensity == DisplayMetrics.DENSITY_TV){
//            setting.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        }else{
//            setting.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        }

        demo.setOnLoginListener(new AnyMSG.OnLoginListener() {
            @Override
            public void onLogin() {
                Log.d("anyMSGDemo", "onLogin");
                ownCID = 0xFFFFFFFF;
                AnyMSGWebAPI.uiEventTrigger(wv, ErrGenerator.opration(ErrGenerator.ErrCode.ERRCODE_SUCCESS_LOGINING));
            }
        });

        demo.setOnWelcomeListener(new AnyMSG.OnWelcomeListener() {
            @Override
            public void onWelcome(String uid, String cid) {
                Log.d("anyMSGDemo", "onWelcome: uid[" + uid + "],cid[" + cid + "]");
                ownCID = Integer.parseInt(String.valueOf(cid));
                AnyMSGWebAPI.uiEventTrigger(wv, ErrGenerator.opration(ErrGenerator.ErrCode.ERRCODE_SUCCESS_LOGINOK, String.valueOf(cid)));
            }
        });

        demo.setOnHeartBeatReceivedListener(new AnyMSG.OnHeartBeatReceivedListener() {
            @Override
            public void onHeartBeatReceived(String uid, String cid) {
//                Log.d("anyMSGDemo", "onHeartBeatReceived: uid[" + uid + "],cid[" + cid + "]");
                AnyMSGWebAPI.uiEventTrigger(wv, ErrGenerator.opration(ErrGenerator.ErrCode.ERRCODE_SUCCESS_HB));
            }
        });

        demo.setOnHeartBeatSentListener(new AnyMSG.OnHeartBeatSentListener() {
            @Override
            public void onHeartBeatSent(String uid, String cid) {
//                Log.d("anyMSGDemo", "onHeartBeatSent: uid[" + uid + "],cid[" + cid + "]");
                AnyMSGWebAPI.uiEventTrigger(wv, ErrGenerator.opration(ErrGenerator.ErrCode.ERRCODE_SUCCESS_HBP));
            }
        });

        demo.setOnMessageReceivedListener(new AnyMSG.OnMessageReceivedListener() {
            @Override
            public void onMessageReceived(String cid, byte[] messageByteArray) {
                AnyMSGWebAPI.uiEventTrigger(wv, ErrGenerator.message(Integer.parseInt(cid, 10), ownCID, new String(messageByteArray)));
//                Log.d("anyMSGDemo", "onMessageReceived: " + cid + "," + new String(messageByteArray));
            }
        });

        demo.setOnMessageSentListener(new AnyMSG.OnMessageSentListener() {
            @Override
            public void onMessageSent(String cid, String verifyStringReceived) {
//                Log.d("anyMSGDemo", "onMessageSent: " + cid + verifyStringReceived);
            }
        });


//        Button btn = (Button) findViewById(R.id.btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                AnyMSGWebAPI.uiEventTrigger(wv,ErrGenerator.opration(ErrGenerator.ErrCode.ERRCODE_SUCCESS_LOGINOK,String.valueOf(1231234)));
//            }
//        });

    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    public static void s(final int uid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject j = new JSONObject();
                JSONObject opr = new JSONObject();
                try {
                    j.put("api", "anyMSGNative");
                    j.put("version", "1.2.40");
                    j.put("domain", "anymsg.net");
                    j.put("port", 9000);
                    j.put("uid", uid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AnyMSG.login(j.toString());
            }
        }).start();


    }

    public static void send(int cid, String message) {
        AnyMSG.sendto(cid, message.getBytes(), 0);
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
