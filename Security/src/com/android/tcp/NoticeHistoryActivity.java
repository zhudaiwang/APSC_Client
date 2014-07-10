

package com.android.tcp;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NoticeHistoryActivity extends Activity {
	
	private final String TAG="NoticeHistoryActivity";
	private String data[][] = { 
			{ "报警", "二楼出口" },
			{ "红外", "一楼仓库" }, 
			{ "报警", "一楼6号门神" },
			{ "报警", "一楼8号门神" },
			{ "报警", "一楼18号门神" },
			{ "报警", "一楼生鲜区" }, 
			{ "报警", "二楼消防通道" },
			{ "红外", "监控室" },
			{ "报警", "三楼走廊" },
			{ "报警", "二楼1号门神" } }; // 准备出若干个信息而这些信息以后将通过程序加入到内嵌的线性布局文件之中
	
	private String nState[] = { "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃","响铃", "响铃", "响铃", "响铃", "响铃", "响铃","响铃", "响铃", "响铃", "响铃", "响铃", "响铃","响铃", "响铃", "响铃", "响铃", "响铃", "响铃","响铃", "响铃", "响铃", "响铃", "响铃", "响铃","响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃", "响铃","响铃", "响铃", "响铃"}; // 准备出若干个信息而这些信息以后将通过程序加入到内嵌的线性布局文件之中
	
	private String ringtime[] = {"", "", "", "", "", "", "", "", "", "", "", "", ""};
	
	private String ringAddr[] = {"", "", "", "", "", "", "", "", "", "", "", "", ""};
	
	private String ringType = "";
	
	private String MsgCancel = "";
	
	private ListView datalist = null; // 定义ListView组件
	
	private TextView info = null;
	
	private int nSelect = 0;
	
	private Button infoBtn = null;
	
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 定义显示的内容包装
	
	private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作
	
	//public NetDataPass netdata ;
	public Socket client;
	public OutputStream outputStream;
	public InputStream inputStream;
	public InetAddress serverAddr;// TCPServer.SERVERIP
	public int port;
	public SocketAddress my_sockaddr;
	
	StringBuilder sb = new StringBuilder();
	
	String tvRecvBuf[]  = new String[512];
	int nBufIndex = 0;
	
	/** media. */
	
	private static final String FILENAME = "mldn";
	SharedPreferences share;
	
	
	private MediaPlayer myMediaPlayer = null ;
	/** media */
	
	/** vibrator */	    								
	private Vibrator mVibrator01;
	/** vibrator */
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.mylistview);
		
		mVibrator01 = ( Vibrator ) getApplication().getSystemService(Service.VIBRATOR_SERVICE);  
		this.datalist = (ListView) super.findViewById(R.id.datalist); // 取得组件
		this.info = (TextView) super.findViewById(R.id.info);
		this.infoBtn = (Button) super.findViewById(R.id.infoBtn);
		share = super.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);

	    Session session = Session.getSession();

	    NetDataPass netdata = (NetDataPass)session.get("netdata");
	    client = netdata.client;
	    outputStream = netdata.outputStream;
	    inputStream = netdata.inputStream;
		
		infoBtn.setOnClickListener(new ShowListener());
		
		if(tvRecvBuf.length <0)
		{
			for (int nIndex = 0; nIndex < this.tvRecvBuf.length; nIndex++) {
				if(tvRecvBuf[nIndex] == "")
				{
					
				}
				
				Log.i(TAG,"--+--tvRecvBuf.length:"+ tvRecvBuf.length);
				
			}
		}
		else
		{
			for (int x = 0; x < nBufIndex; x++) {
				Map<String, String> map = new HashMap<String, String>(); // 定义Map集合，保存每一行数据
				map.put("_id", this.data[x][0]); // 与data_list.xml中的TextView组加匹配
				map.put("name", this.data[x][1]); // 与data_list.xml中的TextView组加匹配
				map.put("state", this.nState[x]); // 与data_list.xml中的TextView组加匹配
				map.put("ringtime", this.ringtime[x]); // 与data_list.xml中的TextView组加匹配
				this.list.add(map); // 保存了所有的数据行
			}
		}
		
		this.simpleAdapter = new SimpleAdapter(this, this.list,
				R.layout.data_list, new String[] { "_id", "name" ,"state", "ringtime"} // Map中的key的名称
				, new int[] { R.id._id, R.id.name , R.id.state, R.id.ringtime}); // 是data_list.xml中定义的组件的资源ID
		this.datalist.setAdapter(this.simpleAdapter);
		
		
		
		OnItemClickListener listener = new OnItemClickListener()
		{
			public void onItemClick(
					AdapterView<?> parent, View view, int positon, long id)
			{
				Toast.makeText(NoticeHistoryActivity.this, "text", Toast.LENGTH_LONG);
			}
		};
		datalist.setOnItemClickListener(listener);
		
		
		this.datalist.setOnItemClickListener(new OnItemClickListenerlmpl());
		
		new Thread(new TCPCheckConnectThread()).start();
		new Thread(new TCPServerThread()).start();
	}
	
	
	private class OnItemClickListenerlmpl implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?>parent, View view, int position, long id)
		{
			Map<String,String> map = (Map<String,String>)NoticeHistoryActivity.this.simpleAdapter.getItem(position);
			String _id = map.get("_id");
			String name = map.get("name");
			NoticeHistoryActivity.this.info.setText(""+ _id + ", 地点：" + name);
			
			nSelect = position;
			//deleteItem(position) ;
			 //addItem(position) ;
		}
		

	}
		

	
	private class ShowListener implements OnClickListener{
		@Override
				
		public void onClick(View v) 
		{
			if(v.getId()==R.id.infoBtn)
			{
				//这里添加发送关闭给服务器信息的代码，等待1秒钟，如果服务器回复指定信息就关闭更新这个列表
				try 
				{	
					String StrSendMsg  = ringAddr[nSelect];
					
					outputStream.write(StrSendMsg.getBytes());
					outputStream.flush();	
					Toast toast=Toast.makeText(getApplicationContext(), "发送关闭指令成功，等待服务器确认！", Toast.LENGTH_SHORT); 
					toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 300); 
					toast.show();
				} 
				catch (NumberFormatException e) 
				{
						//	Log.d(TAG, e.getMessage());
				} 
				catch (IOException e) {
						//	Log.d(TAG, e.getMessage());
				}
				//因为删除这个报警信息需要等待服务器确认，所以，改为判断服务器信息，删除报警铃声。
				 //UpdateList(nSelect);
			}
		}
	}
		
	
	
	
	private void addItem(int position)  
	{  
		HashMap<String, String> map = new HashMap<String, String>();  
			//map.put("image", R.drawable.icon);  
			//map.put("title", "标题");  
		map.put("_id",  this.data[position][0]); // 与data_list.xml中的TextView组加匹配
		map.put("name", this.data[position][1]); // 与data_list.xml中的TextView组加匹配
		map.put("state", this.nState[position]); // 与data_list.xml中的TextView组加匹配
		map.put("ringtime", this.ringtime[position]); // 与data_list.xml中的TextView组加匹配
		list.add(map);  
		simpleAdapter.notifyDataSetChanged();  
	}  
	  
	private void deleteItem(int position)  
	{  
		int size = list.size();  
		if( size > 0 )  
		{  
			list.remove(position);  
			simpleAdapter.notifyDataSetChanged();  
		}  
	}  
	

	private void ModefyItem(int nIndex)  
	{  
		deleteItem(nIndex);
		nState[nIndex] = "";
		addItem(nIndex);  
	}  
	
	
	public void UpdateList(int selectedItem)
	{
		ListAdapter la = datalist.getAdapter();
		int itemNum = datalist.getCount();
		for(int i=0; i< itemNum; i++)
		{
			Map<String,String> map = (Map<String,String>)NoticeHistoryActivity.this.simpleAdapter.getItem(i);
			if(i != selectedItem)
			{
				map.put("_id",  this.data[i%10][0]); // 与data_list.xml中的TextView组加匹配
				map.put("name", this.tvRecvBuf[i]); // 与data_list.xml中的TextView组加匹配
				map.put("ringtime", this.ringtime[i]); // 与data_list.xml中的TextView组加匹配
			}
			else
			{
				//map.put("_id",  ""); // 与data_list.xml中的TextView组加匹配
				//map.put("name", ""); // 与data_list.xml中的TextView组加匹配
				map.put("state", ""); 
			}
			
		}
		 ((SimpleAdapter)la).notifyDataSetChanged();
	}
	

	public void UpdateListByAuto()
	{
		ListAdapter la = datalist.getAdapter();
		int itemNum = datalist.getCount();
		
		Map<String, String> map = new HashMap<String, String>(); // 定义Map集合，保存每一行数据
		
			map.put("_id", this.data[nBufIndex%10][0]); // 与data_list.xml中的TextView组加匹配
			map.put("name", tvRecvBuf[nBufIndex]); // 与data_list.xml中的TextView组加匹配
			map.put("state", this.nState[nBufIndex]); // 与data_list.xml中的TextView组加匹配
			map.put("ringtime", this.ringtime[nBufIndex]); // 与data_list.xml中的TextView组加匹配
			Log.i(TAG,"--+--tvRecvBuf:"+ tvRecvBuf[nBufIndex]);
			this.list.add(map); // 保存了所有的数据行
		
		 ((SimpleAdapter)la).notifyDataSetChanged();
	}
	

	public class TCPCheckConnectThread extends Thread 
	{  
		private boolean flag = true;
	    public TCPCheckConnectThread() 
	    {  	
	    }
	    public void stopme()
	    {
	    	this.flag = false;
	    }
	    public void run() 
	    {  
	            while (flag) 
	            {  
	                try 
	                {  
	                	sleep(10000);
	                	byte[] data = new byte[7];
	                	
	                	CMakeCmd cMakeAcmd = null;
	                	
	                	cMakeAcmd.sA = 0;
	                	cMakeAcmd.bB = (byte)0xaa;
	                	cMakeAcmd.bC = (byte)0xee;
	                	cMakeAcmd.bD = 0x0;
	                	cMakeAcmd.sE = 0x0;
	                	cMakeAcmd.Build(data);

	                	//byte[] data = content.getBytes(); 
	                	outputStream.write(data);
		            }
	                catch (Exception ex) 
	                {  
	                	//是这里没有引入更改界面图形的接口吗？如WORD上写的那样？
	                	//btnConnect.setText("连接");
	                	break;
	                }  
	            }   
	        }   
		} 
	
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {	// 返回键
			this.exitDialog() ;
		}	
		AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {	// 音量增大
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume+1, 1);
		}
		else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)// 音量减小
		{
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume-1, 1);
		}
		   return false;
		   //return super.onKeyDown(keyCode, event);
	}

	
	
	
	private void exitDialog(){
		Dialog dialog = new AlertDialog.Builder(this)
			.setTitle("注销!")		// 创建标题
			.setMessage("注销后将无法收到服务器的信息.") // 表示对话框中的内容
			.setIcon(R.drawable.pic_m) // 设置LOGO
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					NoticeHistoryActivity.this.finish() ;	// 操作结束
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create(); // 创建了一个对话框
		dialog.show() ;	// 显示对话框
	}
	
	
	protected void onDestroy() {
		try {
			client.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		super.onDestroy();
	}  
    
    
	
	private void BreakDialog(){
		Dialog dialog = new AlertDialog.Builder(NoticeHistoryActivity.this)
			.setTitle("警告!")		// 创建标题
			.setMessage("与服务器连接断开。") // 表示对话框中的内容
			.setIcon(R.drawable.pic_m) // 设置LOGO
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				Intent intent  = new Intent(NoticeHistoryActivity.this, LoginActivity.class);
						startActivity(intent);	
					//回到之前的界面
					
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create(); // 创建了一个对话框
		dialog.show() ;	// 显示对话框
	}
	
	
	public class TCPServerThread extends Thread 
	{  
    	int nIndex = 0;
    	int nIsFull = 0;
    	private boolean flag = true;
	    public TCPServerThread() 
	    {  	
	    }
	    public void stopme()
	    {
	    	this.flag = false;
	    }
	    public void run() 
	    {  
	        final char[] charBuf = new char[1024];
	         while (flag) 
	         {  
	             try 
	             {  
	             	InputStreamReader inStrReader = new InputStreamReader(inputStream,"GBK");
	             	int readSize = inStrReader.read(charBuf,0,1024);
	    			//Server is stoping
	    			if(readSize == -1)
	    			{	  
	    				
	    				inputStream.close();///////////////
	    				runOnUiThread(new Runnable()
	    				{
	    					public void run() 
	    					{
	    						 BreakDialog();
	    					}
	    					
	    				});
	    				break;
	    			}
	    			else if(readSize>0)
	    			{
	    				sb.append(new String(charBuf,0,readSize));	    						
	    				runOnUiThread(new Runnable()
	    				{
	    					public void run() 
	    					{	

	    						Log.i(TAG,"sb.toString().length():"+ sb.toString().length());
	    						if(sb.toString().length() < 35)
	    						{
	    							
	    							ringType =  sb.toString().substring(0, 1);
	    							//strRingNo.equals("001")
	    							if(ringType.equals("0"))
	    							{
	    								int nAddrIndex = 0;
	    								//报警已经关闭的信息。请客户端删除对应的报警信息。
	    								MsgCancel =  sb.toString().substring(1, 4);
	    								
	    								//查找已接收列表中 （ringAddr[nBufIndex]）地址的序号。
	    								for(nAddrIndex = 0; nAddrIndex< ringAddr.length; nAddrIndex++)
	    								{
	    									if(ringAddr[nAddrIndex].equals(MsgCancel))
	    									{
	    										break;
	    									}
	    								}
	    								
	    								//将该序号对应的“报警”删除，如有必要，弹窗显示，某某报警已被关闭。
	    								if(nAddrIndex != ringAddr.length)
	    								{
	    		    						
	    		    						Toast toast=Toast.makeText(getApplicationContext(), "该报警已被关闭", Toast.LENGTH_SHORT); 
	    		    						toast.setGravity(Gravity.TOP|Gravity.CENTER, -20, 300); 
	    		    						toast.show();
	    									UpdateList(nAddrIndex);
	    								}
	    							}
	    							else 
	    							{
	    								
	    								tvRecvBuf[nBufIndex] = sb.toString().substring(21);
		    							ringtime[nBufIndex] =  sb.toString().substring(4, 18);
		    							ringAddr[nBufIndex] =  sb.toString().substring(1, 4);
	    							}
	    							
	    						}
	    						else
	    						{
	    							//tvRecvBuf[nBufIndex] = sb.toString();
	    						}
		 		    			
		 		    			sb.delete(0, sb.length());
		 		    			
		 		    			if(!ringType.equals("0"))
		 		    			{
		    						/** media. */
		    						MediaSound(share.getString("RingFilesNo", "  011"));
		    						/** media. */	    									
		    						/** vibrator */
		    						mVibrator01.vibrate( new long[]{50,500,250,700},-1);		
		    						/** vibrator */

		 		    				UpdateListByAuto();
			 		    			Log.i(TAG,"--+--nBufIndex:"+ nBufIndex);
			 		    			nBufIndex++;	
		 		    			}
	    					}
	    				});
	    			}

	             }
	             catch (IOException e) 
	             {  
	                 e.printStackTrace(); 
	                break;
	             }  
	         }
	      
	         
	     }
	} 
	
    

	private void MediaSound(String strRingNo) {
		if(strRingNo.equals("001"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r001);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("002"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r002);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("003"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r003);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("004"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r004);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("005"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r005);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("006"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r006);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("007"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r007);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("008"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r008);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("009"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r009);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else if (strRingNo.equals("010"))
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r010);	// 要锟斤拷锟脚碉拷锟侥硷拷
		}
		else 
		{
			NoticeHistoryActivity.this.myMediaPlayer = MediaPlayer.create(NoticeHistoryActivity.this, R.raw.r011);	// 要锟斤拷锟脚碉拷锟侥硷拷	
		}
		
		
		if (NoticeHistoryActivity.this.myMediaPlayer != null) 
			{
			NoticeHistoryActivity.this.myMediaPlayer.stop(); // 停止锟斤拷锟斤拷
			}
		NoticeHistoryActivity.this.myMediaPlayer.setOnCompletionListener(
			new OnCompletionListener()
			{
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					NoticeHistoryActivity.this.myMediaPlayer.release() ;	// 锟酵凤拷锟斤拷源
				}
			});
		
		try {
			NoticeHistoryActivity.this.myMediaPlayer.prepare() ;
			NoticeHistoryActivity.this.myMediaPlayer.start() ;
		} catch (Exception e) 
		{
		}
		
	}


}