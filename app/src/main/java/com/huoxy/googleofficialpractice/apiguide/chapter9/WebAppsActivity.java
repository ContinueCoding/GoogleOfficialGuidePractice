package com.huoxy.googleofficialpractice.apiguide.chapter9;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.huoxy.googleofficialpractice.R;

public class WebAppsActivity extends AppCompatActivity {

    private static final String TAG = "WebApp";

    private WebView webView;

    //JsBridge
    private BridgeWebView bridgeWebView;
    private Button sendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_apps);

        //webViewTest();

        jsBridgeTest();
    }

    private void webViewTest(){
        webView = (WebView) findViewById(R.id.web_view);
        webView.loadUrl("http://www.sina.com.cn/");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "android_object");
        webView.setWebViewClient(new MyWebViewClient());
    }

    private void jsBridgeTest(){
        bridgeWebView = (BridgeWebView) findViewById(R.id.bridge_web_view);
        sendMsg = (Button) findViewById(R.id.send_msg);

        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.setWebChromeClient(new WebChromeClient());
        bridgeWebView.setWebViewClient(new MyBridgeWebViewClient(bridgeWebView));
        bridgeWebView.loadUrl("file:///android_asset/jsbridge_test.html");

        bridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "Handler = submitFromWeb, data from web = " + data);
                showToast(data);
                function.onCallBack("安卓收到Js的调用了 - 显示Toast, data = " + data);
            }
        });

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bridgeWebView.callHandler("functionInJs", "Call Js Function in Android", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        showToast(" ===== " + data);
                    }
                });
            }
        });
    }

    public void showToast(String msg){
        Toast.makeText(WebAppsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private class MyBridgeWebViewClient extends BridgeWebViewClient{

        public MyBridgeWebViewClient(BridgeWebView webView) {
            super(webView);
        }
    }


    public class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.i(TAG, "shouldOverrideUrlLoading(WebView, String) - url = " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            Log.i(TAG, "shouldOverrideUrlLoading(WebView, WebResourceRequest) - method = " + request.getMethod() + ", url = " + request.getUrl());
            return super.shouldOverrideUrlLoading(view, request);
        }*/
    }

    public class MyWebChromeClient extends WebChromeClient{
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    }

    public class WebAppInterface{
        private Context context;

        public WebAppInterface(Context context){
            this.context = context;
        }

        @JavascriptInterface
        public void showToast(String msg){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
