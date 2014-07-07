package com.android.tcp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

//import org.lxh.demo.MyClientDemo;

import com.android.tcp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/** Vibrator. */

import android.app.Service;

import android.os.Vibrator;

/** Vibrator. */


import android.media.AudioManager;
/** media. */
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
/** media. */

class CNewClass{
	short a;
	short b;
	byte c;
	
	public static void putInt(byte[] bb, int x, int index) {
	    bb[index + 0] = (byte) (x >> 24);
	    bb[index + 1] = (byte) (x >> 16);
	    bb[index + 2] = (byte) (x >> 8);
	    bb[index + 3] = (byte) (x >> 0);
	}
}



public class TCPComActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private final String TAG="TCPComActivity";
    public static final String ARRAYS_COUNT = "com.android.tcp.ARRAYS_COUNT";  
    public static final String ARRAY_INDEX = "com.android.tcp.ARRAY_INDEX";  
    
    private static final String FILENAME = "mldn.txt" ;	// 设置文件名称
    
	
    Button btnConnect;
	Button btnSend;
	Button btnClean;
	EditText edtIpAddr;
	EditText edtPort;
	EditText edtSend;
	TextView tvIpPort;
	//TextView tvRecv;
	TextView tvRecv[] = new TextView[18];
	String tvRecvBuf[]  = new String[512];
	int nBufIndex = 0;
	boolean isConnected=false;
	Socket client;
	private OutputStream outputStream=null;
	private InputStream inputStream=null;
	private boolean thread_flag=true;
	private boolean thread_read_flag=true;
	StringBuilder sb = new StringBuilder();
	

	
	/** media. */
	private MediaPlayer myMediaPlayer = null ;
	/** media */
	
	/** vibrator */	    								
	private Vibrator mVibrator01;
	/** vibrator */
	 
	//TCPServerThread TcpServerThread = new TCPServerThread();
	//TCPCheckConnectThread TcpCheckConnectThread = new TCPCheckConnectThread(); 
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnConnect=(Button)findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(this);
        btnSend=(Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnClean=(Button)findViewById(R.id.btnClean);
        btnClean.setOnClickListener(this);
        edtIpAddr=(EditText)findViewById(R.id.txtIp);
        edtPort=(EditText)findViewById(R.id.txtPort);
        edtSend=(EditText)findViewById(R.id.txtSend);
        tvIpPort=(TextView)findViewById(R.id.tvIPPort);
        
        tvRecv[0]=(TextView)findViewById(R.id.textView1);
        tvRecv[1]=(TextView)findViewById(R.id.textView2);
        tvRecv[2]=(TextView)findViewById(R.id.textView3);
        tvRecv[3]=(TextView)findViewById(R.id.textView4);
        tvRecv[4]=(TextView)findViewById(R.id.textView4);
        tvRecv[5]=(TextView)findViewById(R.id.textView5);
        tvRecv[6]=(TextView)findViewById(R.id.textView6);
        tvRecv[7]=(TextView)findViewById(R.id.textView7);
        tvRecv[8]=(TextView)findViewById(R.id.textView8);
        tvRecv[9]=(TextView)findViewById(R.id.textView9);
        tvRecv[10]=(TextView)findViewById(R.id.textView10);
        tvRecv[11]=(TextView)findViewById(R.id.textView11);
        tvRecv[12]=(TextView)findViewById(R.id.textView12);
        tvRecv[13]=(TextView)findViewById(R.id.textView13);
        tvRecv[14]=(TextView)findViewById(R.id.textView14);
        tvRecv[15]=(TextView)findViewById(R.id.textView15);
        tvRecv[16]=(TextView)findViewById(R.id.textView16);
        tvRecv[17]=(TextView)findViewById(R.id.textView17);




       
        edtPort.setText("7575");
        edtSend.setText("hello");
    	mVibrator01 = ( Vibrator ) getApplication().getSystemService(Service.VIBRATOR_SERVICE);  
       	String str=new String();
       	str=String.format("本地IP地址："+getLocalIpAddress());//
       	tvIpPort.setText(str);
       	//////////////////////////////////////file input
       	int a = 1;
       	FileInputStream input = null ;
		try {
			input = super.openFileInput(FILENAME) ;	// 取得输入流
		} catch (FileNotFoundException e) {
			e.printStackTrace();                    //如果没有Ip文件。则写入原有IP，     还要
			 edtIpAddr.setText("192.168.5.115");
			 a = 2;
		}
		if (a ==1)
		{
			
			Scanner scan = new Scanner(input) ;
			while(scan.hasNext()) {
				this.edtIpAddr.append(scan.next() + "\n") ;
			}
			scan.close() ;
		}
		// //设置布局的第三种方式：直接从XML中读取，并在代码中创建组件
		// //直接从XML中读取布局方式：LinearLayout
		
	

    }
   // public String getLocalIpAddress() 
    public String getLocalIpAddress(){
    	//获取wifi服务
    	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    	//判断wifi是否开启
    	if (!wifiManager.isWifiEnabled()) {
    	wifiManager.setWifiEnabled(true);
    	}
    	WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    	int ipAddress = wifiInfo.getIpAddress();
    	String ip = intToIp(ipAddress);
    	return ip;
    	}
    	private String intToIp(int i) {

    	return (i & 0xFF ) + "." +
    	((i >> 8 ) & 0xFF) + "." +
    	((i >> 16 ) & 0xFF) + "." +
    	( i >> 24 & 0xFF) ;
    	}
    	
    	
    public int isInWifi(){
    	//获取wifi服务
    	WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    	//判断wifi是否开启
    	if (!wifiManager.isWifiEnabled()) {
    	wifiManager.setWifiEnabled(true);
    	}
    	WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    	int ipAddress = wifiInfo.getIpAddress();
    	return ipAddress;
    	}


	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btnConnect)
		{
//			if(isInWifi() == 0)           //判断是否连入wifi
			if(false)
			{
				// TODO Auto-generated catch block
				Toast toast=Toast.makeText(getApplicationContext(), "请先连接无线网络！", Toast.LENGTH_SHORT); 

				toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 300); 
				toast.show();
				
				
			}
			else
			{
				try
				{
					if (false == isConnected)
					{
		
						InetAddress serverAddr = InetAddress.getByName(edtIpAddr.getText().toString());// TCPServer.SERVERIP
						int port=Integer.valueOf(edtPort.getText().toString());
						SocketAddress my_sockaddr = new InetSocketAddress(serverAddr, port);

						//client = new Socket(serverAddr, port);
						client = new Socket();
						client.connect(my_sockaddr,50000);
						btnConnect.setText("断开");               						
						outputStream = client.getOutputStream();
						inputStream = client.getInputStream();
						
						
						isConnected = true;
						thread_read_flag = true;
						/////////new Thread(new InputStreamThread()).start();
						new Thread(new TCPServerThread()).start();
						//TcpServerThread.start();
						
						new Thread(new TCPCheckConnectThread()).start();
						//TcpCheckConnectThread.start();

						
						Toast toast=Toast.makeText(getApplicationContext(), "连接成功！", Toast.LENGTH_SHORT); 
						toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 300); 
						toast.show();
					} 
					else 
					{
						btnConnect.setText("连接");    //按断开出错

						//tvRecv.setText("");
						//edtSend.setText("");
						client.close();
						//TcpServerThread.stopme();
						//TcpCheckConnectThread.stopme();
						
						isConnected = false;
						thread_read_flag = false;
						Toast toast=Toast.makeText(getApplicationContext(), "断开连接！", Toast.LENGTH_SHORT); 
						toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 300); 
						toast.show();
					
					}
				} 
				catch (NumberFormatException e)
				{
 
					Log.d(TAG, e.getMessage());
				} 
				catch (IOException e)
				{
					this.WarningDialog();
					Log.d(TAG, e.getMessage());
				}
			}
			


		}
		else if(v.getId()==R.id.btnSend)
		{
			// TODO Auto-generated method stub
						//Log.d(TAG, "sendText:"+edit_send.getText().toString());
			if (true == isConnected)
			{
				try 
				{										
					outputStream.write(edtSend.getText().toString().getBytes());
					outputStream.flush();	
					Toast toast=Toast.makeText(getApplicationContext(), "发送成功！", Toast.LENGTH_SHORT); 
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
			}
			else
			{
				Toast toast=Toast.makeText(getApplicationContext(), "请先连接服务器！", Toast.LENGTH_SHORT); 
				toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 300); 
				toast.show();
			}

		}
		
		else if(v.getId()==R.id.btnClean)
		{
			int nIndex = 0;
            for(nIndex = 0; nIndex <18; nIndex++)
            {
    			TCPComActivity.this.tvRecv[nIndex].setText("") ; //----------------------------------------------            
            }
			sb.delete(0, sb.length());
			}
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
	             	
	    			Log.i(TAG,"readSize:"+readSize);
	    			//Server is stoping
	    			if(readSize == -1)
	    			{	  
	    				
	    				inputStream.close();///////////////
	    				runOnUiThread(new Runnable()
	    				{
	    					public void run() 
	    					{
	    						 btnConnect.setText("连接");
	    						 isConnected = false;
	    						 BreakDialog();
	    						 
	    					}
	    					
	    				});
	    				//这里要中断另一个进程。要在close前先结束另一个进程
	    				//不对，一个是input 一个是output 有什么影响呢？
	    				
	    				break;
	    			}
	    			//Update the receive editText
	    			else if(readSize>0)
	    			{
	    				sb.append(new String(charBuf,0,readSize));	    						
	    				//sb.append(new String(buffer,0,readSize));
	    				runOnUiThread(new Runnable()
	    				{
	    					public void run() 
	    					{	
	    						/** media. */
	    						MediaSound();
	    						/** media. */	    									
	    						/** vibrator */
	    		                mVibrator01.vibrate( new long[]{100,10,100,1000},-1);  	
	    						/** vibrator */

	    		                	Log.i(TAG,"-- run300=="+nIndex);
	    		                	//tvRecv[nIndex].setText(sb);
	    		                while( nIndex <19)
	    		                {
	    		                
	    		                	if(nIndex == 18)
    			   					{
    			   						
    			   						for(nIndex = 0; nIndex <18; nIndex++)
	    					            {
	    					    			TCPComActivity.this.tvRecv[nIndex].setText("") ; //----------------------------------------------            
	    					    			Log.i(TAG,"-- TCPComActivity");
	    					            }
    			   						nIndex = 0;
    			   						
    			   					}
	    		                	
	    		                	
	    		   				    if(true )
	    							{
	    		   				    	Log.i(TAG,"-- if(tvRecv[nIndex].length()  >3 )");
	    		   				    	Log.i(TAG,"-- index = "+nIndex);
	    		   				    	if(nIndex == 0)
	    		   				    	{
	    		   				    		tvRecv[nIndex].setText(sb);
		 		    						tvRecv[nIndex].setTextColor(0xffff0000);
		 		    						tvRecvBuf[nBufIndex] = sb.toString();
		 		    						nBufIndex++;
		 		    						sb.delete(0, sb.length());
		 		    						 nIndex++;
	    		   				    		break;
	    		   				    	}
	    								
	    								else if(nIndex > 0)
	    								{
	    									tvRecv[nIndex].setText(sb);
		 		    						tvRecv[nIndex].setTextColor(0xffff0000);
		 		    						tvRecvBuf[nBufIndex] = sb.toString();
		 		    						sb.delete(0, sb.length());
		 		    						nBufIndex++;			
	    									nIsFull = nIndex-1;
	    									tvRecv[nIsFull].setTextColor(0xffDCDCDC);
	    									 nIndex++;
	    									break;
	    								}
	    							}
	    		                }
	    					}
	    				});
	    				
	    				
	    				
	    			}

	             }
	             catch (IOException e) 
	             {  
	                 e.printStackTrace(); 
	                Log.i(TAG,"-- e.printStackTrace(); line-428");
	                break;
	             }  
	         }
	      
	         
	     }
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
	                	Log.i(TAG,"--TCPCheckConnectThread(ERROR) line 377");
	                	break;
	                }  
	            }   
	        }   
	       // tvRecv.setText("end");
	} 
	
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	Log.d(TAG,"onDestroy");
    	if(true == isConnected)
    	{
    		try {
    			client.close();
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		}		
    	}
    	btnConnect.setText("连接");
		isConnected = false;
		thread_read_flag = false;
		super.onDestroy();
	}
    
    
    
    
	private void MediaSound() {
		TCPComActivity.this.myMediaPlayer = MediaPlayer.create(
				TCPComActivity.this, R.raw.mldn_ad);	// 要播放的文件
		if (TCPComActivity.this.myMediaPlayer != null) {
			TCPComActivity.this.myMediaPlayer.stop(); // 停止操作
		}
		TCPComActivity.this.myMediaPlayer.setOnCompletionListener(new OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {

				TCPComActivity.this.myMediaPlayer.release() ;	// 释放资源
			}}) ;
		try {
			TCPComActivity.this.myMediaPlayer.prepare() ;
			TCPComActivity.this.myMediaPlayer.start() ;
		} catch (Exception e) 
		{
		}
		
	}

	
	
	private void exitDialog(){
		Dialog dialog = new AlertDialog.Builder(TCPComActivity.this)
			.setTitle("注销!")		// 创建标题
			.setMessage("注销后将无法收到服务器的信息.") // 表示对话框中的内容
			.setIcon(R.drawable.pic_m) // 设置LOGO
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					TCPComActivity.this.finish() ;	// 操作结束
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create(); // 创建了一个对话框
		dialog.show() ;	// 显示对话框
	}
	
	
	
	private void WarningDialog(){
		Dialog dialog = new AlertDialog.Builder(TCPComActivity.this)
			.setTitle("警告!")		// 创建标题
			.setMessage("与服务器连接失败.") // 表示对话框中的内容
			.setIcon(R.drawable.pic_m) // 设置LOGO
			.setPositiveButton("设置", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intentSetup  = new Intent(TCPComActivity.this, SetupActivity.class);
					intentSetup.putExtra("ServerIp", edtIpAddr.getText().toString());
					intentSetup.putExtra("ServerPort", edtPort.getText().toString());
					startActivityForResult(intentSetup, 1000);
				}
			}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {	
				}
			}).create(); // 创建了一个对话框
		dialog.show() ;	// 显示对话框
	}
	
	
	
	private void BreakDialog(){
		Dialog dialog = new AlertDialog.Builder(TCPComActivity.this)
			.setTitle("警告!")		// 创建标题
			.setMessage("与服务器连接断开。") // 表示对话框中的内容
			.setIcon(R.drawable.pic_m) // 设置LOGO
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create(); // 创建了一个对话框
		dialog.show() ;	// 显示对话框
	}
	@Override

	public boolean onCreateOptionsMenu(Menu menu) {			// 显示菜单
		menu.add(Menu.NONE,  Menu.FIRST + 1,  5, "注销")	 .setIcon(
				android.R.drawable.ic_lock_power_off);	// 设置图标
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "历史记录").setIcon(
				android.R.drawable.presence_away);	
		menu.add(Menu.NONE, Menu.FIRST + 3, 3, "设置").setIcon(
				android.R.drawable.ic_menu_manage);	// 设置菜单项
		return true;											// 菜单显示
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		// 选中某个菜单项
		
		switch (item.getItemId()) {								// 判断菜单项ID
		case Menu.FIRST + 1:
			TCPComActivity.this.exitDialog() ;
			break;
		case Menu.FIRST + 2://tvRecvBuf[nBufIndex]
			Intent intentHistory  = new Intent(TCPComActivity.this, NoticeHistoryActivity.class);
			intentHistory.putExtra("nBufIndex", nBufIndex);
			Bundle bundle = new Bundle();
			bundle.putStringArray("tvRecvBuf", tvRecvBuf);
			intentHistory.putExtras(bundle);   
			startActivity(intentHistory);
			break;
		case Menu.FIRST + 3:
			Intent intentSetup  = new Intent(TCPComActivity.this, SetupActivity.class);
			intentSetup.putExtra("ServerIp", edtIpAddr.getText().toString());
			intentSetup.putExtra("ServerPort", edtPort.getText().toString());
			startActivityForResult(intentSetup, 1000);
			break;
		}
		return false;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1000 && resultCode == 1001)
		{
			String resultIp = data.getStringExtra("IpAddrChanged");
			String resultPort = data.getStringExtra("PortChanged");
			edtIpAddr.setText(resultIp);
	        edtPort.setText(resultPort);	
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

	

	
	
}