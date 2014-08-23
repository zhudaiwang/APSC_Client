package com.android.tcp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.content.SharedPreferences;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SecurityRingActivity extends Activity {
	private String data[][] = 
			{
			{ "001", " 断续声" },
			{ "002", " 船用警告声" }, 
			{ "003", " 长笛声(汽笛声)" },
			{ "004", " 铁路道口告警声(铛 铛声)" }, 
			{ "005", " 船用潜水艇警告声" },
			{ "006", " 欧洲报警声" },
			{ "007", " 欧版电铃声"},
			{ "008", " 电铃声" }, 
			{ "009", " 门铃(叮-咚声)" },
			{ "010", " 电话铃声" } ,
			{ "011", "  默认铃声" } 
			}; 
	
	
	
	private ListView datalist = null; 
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 定义显示的内容包装
	private SimpleAdapter simpleAdapter = null; 
	private TextView info = null;
	private static final String FILENAME = "mldn";
	SharedPreferences share;

	/** media. */
	private MediaPlayer myMediaPlayer = null ;
	/** media */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.ring_main);
		share = super.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
		
		System.out.println(share.getString("RingFilesNo", "  011"));
		
		this.datalist = (ListView) super.findViewById(R.id.datalist_ring); // 取锟斤拷锟斤拷锟�		
		this.info = (TextView) super.findViewById(R.id.info_ring); // 取锟斤拷锟斤拷锟�		
		for (int x = 0; x < this.data.length; x++) {
			Map<String, String> map = new HashMap<String, String>(); // 定义Map集合，保存每一行数据
			map.put("_id", this.data[x][0]); // 与data_list.xml中的TextView组加匹配
			map.put("name", this.data[x][1]); // 与data_list.xml中的TextView组加匹配
			this.list.add(map); // 保存了所有的数据行
		}
		this.simpleAdapter = new SimpleAdapter(this, this.list,
				R.layout.ring_data_list, new String[] { "_id", "name" } // Map中的key的名称
				, new int[] { R.id._id_ring, R.id.name_ring }); // 是data_list.xml中定义的组件的资源ID
		this.datalist.setAdapter(this.simpleAdapter);
		this.datalist.setOnItemClickListener(new OnItemClickListenerImpl()); // 单击选项
		
		SecurityRingActivity.this.info.setText("当前铃声为：" +share.getString("RingFilesName", "  默认铃声"));
	}

	private class OnItemClickListenerImpl implements OnItemClickListener {

		@SuppressWarnings("unchecked")
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Map<String, String> map = (Map<String, String>) SecurityRingActivity.this.simpleAdapter
					.getItem(position);
			String _id = map.get("_id");
			String name = map.get("name");
			SecurityRingActivity.this.info.setText("当前铃声为：" + name  );
			
			SharedPreferences.Editor edit = share.edit();
			edit.putString("RingFilesNo", _id);
			edit.putString("RingFilesName", name);
			edit.commit();
			MediaSound(_id);
		}
	}
	
	private void MediaSound(String strRingNo) {
		if(strRingNo.equals("001"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r001);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("002"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r002);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("003"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r003);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("004"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r004);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("005"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r005);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("006"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r006);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("007"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r007);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("008"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r008);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("009"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r009);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("010"))
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r010);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else 
		{
			SecurityRingActivity.this.myMediaPlayer = MediaPlayer.create(SecurityRingActivity.this, R.raw.r011);	// 要锟斤拷锟脚碉拷锟侥硷拷	
		}
		
		
		if (SecurityRingActivity.this.myMediaPlayer != null) 
			{
			SecurityRingActivity.this.myMediaPlayer.stop(); // 停止锟斤拷锟斤拷
			}
		SecurityRingActivity.this.myMediaPlayer.setOnCompletionListener(
			new OnCompletionListener()
			{
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					SecurityRingActivity.this.myMediaPlayer.release() ;	// 锟酵凤拷锟斤拷源
				}
			});
		
		try {
			SecurityRingActivity.this.myMediaPlayer.prepare() ;
			SecurityRingActivity.this.myMediaPlayer.start() ;
		} catch (Exception e) 
		{
		}
		
	}
}