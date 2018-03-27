package com.jackchen.webview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/27 16:19
 * Version 1.0
 * Params:
 * Description:  Android调用Js方法的2种方式、  点击Html中某个 onClick点击事件
 */
public class MainActivity extends AppCompatActivity {

    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView() ;

    }

    private void initView() {
        web_view = (WebView) findViewById(R.id.web_view);
        // 获取WebView的设置
        WebSettings webSettings = web_view.getSettings();
        //设置编码
        webSettings.setDefaultTextEncodingName("utf-8");
        //设置背景颜色 透明
        web_view.setBackgroundColor(Color.argb(0, 0, 0, 0));
        // 将JavaScript设置为可用，这一句话是必须的，不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        // 通过webview加载html页面
        web_view.loadUrl("file:///android_asset/ll.html");


        // Android 调用 Js 第一种方法：使用下边方式来写
        // 给 WebView 添加 JavaScript接口  参数1：   ；参数2：JS中的变量名
        web_view.addJavascriptInterface(new JsInterface() , "obj");
    }


    public class JsInterface{
        // Android 调用 Js 方法1 中的返回值
        @JavascriptInterface
        public void onSum(int result){
            Toast.makeText(MainActivity.this , "Android调用Js方法有返回值，返回结果是 -> " + result , Toast.LENGTH_SHORT).show();
        }


        /**
         *  点击 WebView中的（即就是 Html中的） 点击事件
         *  <input type="button" value="结算" onclick="showToast('12')">
         */
        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(MainActivity.this , "你的商品价格是：￥" + toast , Toast.LENGTH_SHORT).show();
        }

    }


    public void btn_1(View view){
        web_view.loadUrl("javascript:sum(6,6)");
    }


    /**
     * Android 调用 Js 第二种方法
     *    Android4.4 之后 直接调用evaluateJavascript方法即可
     */
    public void btn_2(View view){
        web_view.evaluateJavascript("sumn(6,11)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(MainActivity.this, "返回值" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Android 调用 Js方法，无参数
     */
    public void btn_3(View view){
        web_view.loadUrl("javascript:funFromjs()");
    }


    /**
     * Android 调用 Js 方法 ， 有参数
     */
    public void btn_4(View view){
        web_view.loadUrl("javascript:funJs('Android端传入的信息，div标签中显示,含参数')");
    }

}
