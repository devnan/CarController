package com.chuangshi.carcontroller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Message;
import android.util.Log;

/**
 * @author Allen Lin
 * @date 2015-10-31
 * @desc：
 */
public class Server extends Thread {
	private static final int PORT = 8000;// 端口
	private static final String IP = "192.168.0.100";// 端口
	private static Socket mSocket = null;

	private static OutputStream pw = null;

	public void run() {
		try {
			mSocket = new Socket(IP, PORT);
			Log.d("MainActivity", "已经连接小车");

			// Socket carClient = server.accept();// 阻塞，等待小车连上
			// Log.d("MainActivity", "小车已经连上0");
			//
			// new CarService(carClient).start();// 开启小车服务线程,负责接收小车的数据，并更新UI界面
			//
			// pw = new PrintWriter(carClient.getOutputStream());//
			// 由Socket对象得到输出流，并构造PrintWriter对象
			pw = mSocket.getOutputStream();// 由Socket对象得到输出流，并构造PrintWriter对象

			new CarService(mSocket).start();// 开启小车服务线程,负责接收小车的数据，并更新UI界面
			Message message = new Message();
			message.what = Constant.CONNECT;
			MainActivity.mHandler.sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("MainActivity", "连接小车失败");
		}
	};

	public static OutputStream getPrintWriter() {
		return pw;
	}
}
