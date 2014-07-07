package com.android.tcp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
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
import android.widget.Button;
import android.widget.EditText;
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
	
	

class CMakeCmd{
	static short sA;
	static byte bB;
	static byte bC;
	static byte bD;
	static short sE;
	
	public static void putInt(byte[] bb, int x, int index) {
	    bb[index + 0] = (byte) (x >> 24);
	    bb[index + 1] = (byte) (x >> 16);
	    bb[index + 2] = (byte) (x >> 8);
	    bb[index + 3] = (byte) (x >> 0);
	}
	
	public static void putShort(byte[] bb, short x, int index) {
	    bb[index + 0] = (byte) (x >> 8);
	    bb[index + 1] = (byte) (x >> 0);
	}
	
	public static void putbyte(byte[] bb, byte x, int index) {
	    bb[index + 0] = (byte) (x >> 0);
	}
	public static void Build(byte[] bb) {
	    putShort(bb, sA, 0);
	    putbyte(bb, bB, 2);
	    putbyte(bb, bC, 3);
	    putbyte(bb, bD, 4);
	    putShort(bb, sE, 5);
	}
}





