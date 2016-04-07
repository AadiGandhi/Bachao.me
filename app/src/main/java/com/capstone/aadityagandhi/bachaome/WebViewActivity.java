package com.capstone.aadityagandhi.bachaome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    private WebView mWebView;
    private String url = "http://capstone.bitnamiapp.com/capstone/uploads/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras.containsKey("device_id"))
            url+=extras.getString("device_id");
        url+="/";
        url+="images.html";
        mWebView.loadUrl(url);
    }
}
