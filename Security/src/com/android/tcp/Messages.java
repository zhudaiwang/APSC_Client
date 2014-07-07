package com.android.tcp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Messages extends Activity {
	private final String TAG="---Messages";
	private String data[] = { " " };					// 定义显示的数据
	private ListView listView;  // 定义ListView组件
	String[] tvRecvBuf;
	String[] tvRecvBufTmp; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG,"super.onCreate(savedInstanceState);"+tvRecvBuf);
		Intent intent = getIntent();
		int nBufIndex = intent.getIntExtra("nBufIndex",0); 
		
		Bundle bundle = intent.getExtras();
		Log.i(TAG,"Bundle bundle = intent.getExtras();"+tvRecvBuf);
		tvRecvBuf = bundle.getStringArray("tvRecvBuf");
		
		
		if(nBufIndex == 0)
		{
			tvRecvBufTmp = new String[1];
			tvRecvBufTmp[0] = "暂无任何信息";
		}
		else
		{
			tvRecvBufTmp = new String[nBufIndex];
			for(int nIndex = 0; nIndex < nBufIndex; nIndex++)
			{
				tvRecvBufTmp[nIndex] = tvRecvBuf[nIndex];
			}
		}
		
		Log.i(TAG,"-----------------------------------");
		Log.i(TAG,"-----------------------------------"+tvRecvBuf[1]);
		this.listView = new ListView(this) ;						// 实例化组件
		this.listView.setAdapter(new ArrayAdapter<String>(this,	android.R.layout.simple_expandable_list_item_1, this.tvRecvBufTmp));		// 将数据包装    //每行显示一条数据	// 设置组件内容
		super.setContentView(this.listView); 						// 将组件添加到屏幕之中

	}
}