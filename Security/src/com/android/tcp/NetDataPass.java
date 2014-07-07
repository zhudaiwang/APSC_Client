package com.android.tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class NetDataPass implements Serializable{
	public Socket client;
	public OutputStream outputStream;
	public InputStream inputStream;
	public InetAddress serverAddr;// TCPServer.SERVERIP
	public int port;
	public SocketAddress my_sockaddr;
}
