package com.android.tcp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class SetupActivity extends Activity {
	EditText SetupedtIpAddr;
	EditText SetupedtPort;
    private SeekBar seek = null ;
    AudioManager mAudioManager ;
    private static final String FILENAME = "mldn.txt" ;	// 设置文件名称
    FileOutputStream output = null ;	// 接收文件输出对象

	public SetupActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup);
		SetupedtIpAddr=(EditText)findViewById(R.id.txtIp_Setup);
		SetupedtPort=(EditText)findViewById(R.id.txtPort_Setup);
	    this.seek = (SeekBar) super.findViewById(R.id.seekBar1) ;		// 取得SeekBar
		Intent intent = getIntent();
		String SetupServerIp = intent.getStringExtra("ServerIp");
		String SetupServerPort = intent.getStringExtra("ServerPort");
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		this.seek.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImpl());// 设置监听
		seek.setProgress(currentVolume*6);
		
		SetupedtIpAddr.setText(SetupServerIp);
		SetupedtPort.setText(SetupServerPort);
		
		
		
		try {
			output = super.openFileOutput(FILENAME, Activity.MODE_PRIVATE) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override

	public boolean onCreateOptionsMenu(Menu menu) {			// 显示菜单
		menu.add(Menu.NONE,  Menu.FIRST + 1,  5, "保存")	 .setIcon(
				android.R.drawable.ic_menu_save);	// 设置图标
		return true;											// 菜单显示
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		// 选中某个菜单项
		switch (item.getItemId()) {								// 判断菜单项ID
		case Menu.FIRST + 1:
			Toast.makeText(this, "保存成功！！", Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.putExtra("IpAddrChanged",  SetupedtIpAddr.getText().toString());
			intent.putExtra("PortChanged",  SetupedtPort.getText().toString());
			setResult(1001, intent);
			
			
			PrintStream outToFile = new PrintStream(output) ; 	// 输出方便
			outToFile.println( SetupedtIpAddr.getText().toString()) ;
			outToFile.close() ;	// 资源一定要关闭
			
			
			finish();
			
			break;

		}
		return false;
	}
	
	private void exitDialog(){
		Dialog dialog = new AlertDialog.Builder(SetupActivity.this)
			.setTitle("提示！")		// 创建标题
			.setMessage("是否保存？") // 表示对话框中的内容
			.setIcon(R.drawable.pic_m) // 设置LOGO
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.putExtra("IpAddrChanged",  SetupedtIpAddr.getText().toString());
					intent.putExtra("PortChanged",  SetupedtPort.getText().toString());
					setResult(1001, intent);
					
					
					
			       	
			       	
			       	
			       	
					
					PrintStream outToFile = new PrintStream(output) ; 	// 输出方便
					outToFile.println( SetupedtIpAddr.getText().toString()) ;
					outToFile.close() ;	// 资源一定要关闭
			        
					
					
					
					
					
					
					
					
					
					
					
					
					SetupActivity.this.finish() ;	// 操作结束
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SetupActivity.this.finish() ;	// 操作结束
				}
			}).create(); // 创建了一个对话框
		dialog.show() ;	// 显示对话框
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {


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
	
	
	
	private class OnSeekBarChangeListenerImpl implements
	SeekBar.OnSeekBarChangeListener {				// 设置操作监听
		
		
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		
	
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress()/6 , 1);
	}
}
	
}
