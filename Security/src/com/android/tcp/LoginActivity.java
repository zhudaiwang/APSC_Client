package com.android.tcp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button btEnter;
	private Button btSetup;
	EditText etUser;
	EditText etPass;
	
	String strIpAddr = "192.168.5.113";
	StringBuffer strIpBuf = new StringBuffer();
	String strIpPort = "7575";
	private static final String FILENAME = "mldn.txt" ;	// 设置文件名称
	private TextView tviewIpAddr = null;
	
	Socket client;
	private OutputStream outputStream=null;
	private InputStream inputStream=null;
	
	
	private static String TAG = "LoginActivity";

	public LoginActivity() {
		//15120140101 12:16     仓库的门禁报警报11      
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.login);
		 
		 
			btEnter = (Button)this.findViewById(R.id.button1);
			btSetup = (Button)this.findViewById(R.id.button2);
			etUser=(EditText)findViewById(R.id.etName);
			etPass=(EditText)findViewById(R.id.etpass);
			tviewIpAddr=(TextView)findViewById(R.id.TvServerIP);
			
		 	btEnter.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent  = new Intent(LoginActivity.this, NoticeHistoryActivity.class);

					String strUser = etUser.getText().toString();
					String strPass = etPass.getText().toString();
					if((true == strUser.equals("123"))&&(true == strPass.equals("123")))
					{
						if(isInWifi() != 0  || true) 
						{
							try
							{
								
									InetAddress serverAddr = InetAddress.getByName(strIpAddr);// TCPServer.SERVERIP
									int port=Integer.valueOf(strIpPort);
									SocketAddress my_sockaddr = new InetSocketAddress(serverAddr, port);
		
									//client = new Socket(serverAddr, port);
									client = new Socket();
									client.connect(my_sockaddr,50000);			
									outputStream = client.getOutputStream();
									inputStream = client.getInputStream();
									
									intent.putExtra("serverAddr", "serverAddr");
								    Session session = Session.getSession();

								    NetDataPass netdata = new NetDataPass();
								    netdata.client = client;
								    netdata.inputStream = inputStream;
									netdata.outputStream = outputStream;
								     
								    session.put("netdata", netdata);
									
									startActivity(intent);	
								
							
							}
							catch (NumberFormatException e)
							{
								Toast toast=Toast.makeText(getApplicationContext(), "连接错误！", Toast.LENGTH_SHORT); 
								toast.setGravity(Gravity.TOP|Gravity.CENTER, -30, 300); 
								toast.show();
								 
								//Log.d(TAG, e.getMessage());
							} 
							catch (IOException e)
							{

								//this.WarningDialog();
								Toast toast=Toast.makeText(getApplicationContext(), "连接错误！", Toast.LENGTH_SHORT); 
								toast.setGravity(Gravity.TOP|Gravity.CENTER, -20, 300); 
								toast.show();
							}
							
						}
						else
						{
							Toast toast=Toast.makeText(getApplicationContext(), "连接错误！", Toast.LENGTH_SHORT); 
							toast.setGravity(Gravity.TOP|Gravity.CENTER, -20, 300); 
							toast.show();
						}
						
						
					}

						
					
					else
					{
						
						
						Log.i(TAG, "wrong_"+strUser);
						Log.i(TAG, "wrong_"+strPass);
						System.out.println("--"+strUser);
						Toast toast=Toast.makeText(getApplicationContext(), "请输入正确的用户名和密码", Toast.LENGTH_SHORT); 
						toast.setGravity(Gravity.TOP|Gravity.CENTER, -20, 300); 
						toast.show();
					}
					
					
				}

				private void WarningDialog(){
					Dialog dialog = new AlertDialog.Builder(LoginActivity.this)
						.setTitle("警告!")		// 创建标题
						.setMessage("与服务器连接失败.") // 表示对话框中的内容
						.setIcon(R.drawable.pic_m) // 设置LOGO
						.setPositiveButton("设置", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
					//			Intent intentSetup  = new Intent(LoginActivity.this, SetupActivity.class);
					//			intentSetup.putExtra("ServerIp", strIpAddr);
					//			intentSetup.putExtra("ServerPort", strIpPort);
					//			startActivityForResult(intentSetup, 1000);
							}
						}).setNegativeButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {	
							}
						}).create(); // 创建了一个对话框
					dialog.show() ;	// 显示对话框
				}
				
				
				
			});
	
		 	btSetup.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent  = new Intent(LoginActivity.this, SecurityRingActivity.class);	
					startActivity(intent);	
				}	
			});

	
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
	    	
	
}
