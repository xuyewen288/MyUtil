package com.xyw.myutil.wxapi;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xyw.myutil.R;
import com.xyw.util.helper.LogUtil;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);
		api = WXAPIFactory.createWXAPI(this, "appid");

		api.handleIntent(getIntent(), this);
		boolean b=api.registerApp("appid");
		LogUtil.i("WXPayEntryActivity=="+b);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
		boolean b=api.registerApp("appid");
		LogUtil.i("WXPayEntryActivity onNewIntent=="+b);
	}

	@Override
	public void onReq(BaseReq baseReq) {
		LogUtil.i("openid="+baseReq.openId);
		LogUtil.i("arg="+baseReq.checkArgs());
		LogUtil.i("type="+baseReq.getType());

		//...
	}

	@Override
	public void onResp(BaseResp baseResp) {
		if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			//...
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("返回码："+ String.valueOf(baseResp.errCode));
			builder.show();
		}
	}
}