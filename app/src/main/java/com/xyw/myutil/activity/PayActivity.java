package com.xyw.myutil.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.xyw.myutil.R;
import com.xyw.util.alipay.AlipayHelp;
import com.xyw.util.alipay.AlipayPayListener;
import com.xyw.util.helper.LogUtil;
import com.xyw.util.wxapi.WXHelp;
import com.xyw.util.wxapi.WXPayListener;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.Map;


public class PayActivity extends Activity implements WXPayListener, AlipayPayListener {

    private WXHelp wxHelp;
    private MyHandler myHandler;

    private AlipayHelp alipayHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        myHandler=new MyHandler(this);
        wxHelp= WXHelp.getInstance(this, "appid", "appsecret");
        wxHelp.addPayListener(this);

        findViewById(R.id.bt_zfb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubaoPay();
            }
        });

        findViewById(R.id.bt_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    weixinPay();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wxHelp.removePayListener(this);
    }

    private void zhifubaoPay() {
        alipayHelp=new AlipayHelp();
        alipayHelp.setAlipayPayListener(this);
        //url是自己服务器的统一下单接口
        alipayHelp.pay("url",PayActivity.this);

    }


    private void weixinPay() throws JSONException {
        //url是自己服务器的统一下单接口
        wxHelp.pay("url");
    }

    @Override
    public void paySuccess() {
        LogUtil.e("支付成功");
        LogUtil.e("支付成功"+Thread.currentThread());
    }

    @Override
    public void payFail(int errCode) {
        LogUtil.e("支付失败："+errCode);
    }

    @Override
    public void onResponse(Map<String, String> result) {
        for (Map.Entry<String, String> entry : result.entrySet()) {
                        //Map.entry<String,String> 映射项（键-值对）  有几个方法：用上面的名字entry
                         //entry.getKey() ;entry.getValue(); entry.setValue();
                        //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
                        LogUtil.e("key= " + entry.getKey() + " and value= "
                                        + entry.getValue());
                    }
    }


    private class MyHandler extends Handler {
        private final WeakReference<PayActivity> weakReference;

        public MyHandler(PayActivity payActivity){
            weakReference=new WeakReference<>(payActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            PayActivity activity=weakReference.get();
            if(null==activity)
                return;
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    break;
            }
        }
    }
}
