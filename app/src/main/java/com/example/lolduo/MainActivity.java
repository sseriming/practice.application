package com.example.lolduo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WebView webView = (WebView) findViewById(R.id.webView2);
//        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //자바스크립트 허용
        webView.loadUrl("https://www.op.gg/");


        WebView webView2 = (WebView) findViewById(R.id.webView);
        WebSettings webSettings2 = webView2.getSettings();
        webView2.setWebViewClient(new WebViewClient());
        webSettings2.setJavaScriptEnabled(true); //자바스크립트 허용
        webView2.loadUrl("https://www.youtube.com/c/LeagueofLegendsKoreaOfficial");


        Button duosrh = findViewById(R.id.button);
        duosrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, gradeActivity.class);
                startActivity(intent);
            }
        });
        Button logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferences auto = getSharedPreferences("auto",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                //edit.clear()는 auto에 있는 모든 정보를 지움
                editor.clear();
                editor.commit();
                Toast.makeText(MainActivity.this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }
}