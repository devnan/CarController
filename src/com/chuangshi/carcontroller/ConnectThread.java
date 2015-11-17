package com.chuangshi.carcontroller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author Allen Lin
 * @date 2015-10-31
 * @desc 线程负责连接小车服务器来获取socket对象
 */
public class ConnectThread extends Thread {

	private Socket mSocket = null;
	private OutputStream pw = null;
	Handler mHandler;

	public ConnectThread(Handler mHandler) {

		this.mHandler = mHandler;
	}

	public void run() {
		try {
			mSocket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(Constant.IP,
					Constant.PORT);// IP和端口号
			mSocket.connect(socketAddress, Constant.TIMEOUT);
			pw = mSocket.getOutputStream();// 由Socket对象得到输出流，并构造PrintWriter对象
			new CarService(mSocket, mHandler).start();// 开启小车服务线程,负责接收小车的数据，并更新UI界面

			Log.d("MainActivity", "成功连接小车");
			Message message = new Message();
			message.what = Constant.CONNECT;
			mHandler.sendMessage(message);

		} catch (IOException e) {

			Log.d("MainActivity", "连接小车失败");

			Message message = new Message();
			message.what = Constant.UNCONNECT;
			mHandler.sendMessage(message);
		}
	};

	public OutputStream getPrintWriter() {
		return pw;
	}
}
