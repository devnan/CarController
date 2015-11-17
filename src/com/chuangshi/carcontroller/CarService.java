package com.chuangshi.carcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author Allen Lin
 * @date 2015-10-31
 * @desc 小车后台服务类，负责接收处理小车实时返回的数据，并及时更新UI线程
 */
public class CarService extends Thread {
	public Socket mSocket;
	public BufferedReader br;
	public InputStream is;
	Handler handler;

	public CarService(Socket mSocket, Handler handler) throws IOException {
		this.mSocket = mSocket;
		is = mSocket.getInputStream();
		this.handler = handler;
	}

	@Override
	public void run() {

		Log.d("MainActivity", "小车后台服务线程,负责接收小车返回的数据");
		try {
			br = new BufferedReader(new InputStreamReader(
					mSocket.getInputStream(), "GB2312"));

			String msg = null;
			while ((msg = br.readLine()) != null) {
				Log.d("MainActivity", "得到msg" + msg);
				if (msg != "") {
					getAngleStr(msg);
				}
				Message message = new Message();
				message.what = Constant.DATA;
				handler.sendMessage(message);

			}

		} catch (IOException e) {
			Log.d("MainActivity", "小车后台服务线程出错");
			try {
				br.close();
				mSocket.close();
			} catch (IOException e1) {
				Log.d("MainActivity", "关闭流出错");
			}
		}
		super.run();
	}

	/**
	 * 解析小车返回的数据格式
	 * @param msg
	 */
	private void getAngleStr(String msg) {

		if (msg.contains("X")) {

			Log.e("MainActivity",
					msg.substring(msg.indexOf("X") + 1, msg.indexOf("Y")));
			MainActivity.mCardata.setAngle_x(msg.substring(
					msg.indexOf("X") + 1, msg.indexOf("Y")));

			Log.e("MainActivity",
					msg.substring(msg.indexOf("Y") + 1, msg.indexOf("Z")));
			MainActivity.mCardata.setAngle_y(msg.substring(
					msg.indexOf("Y") + 1, msg.indexOf("Z")));

			Log.e("MainActivity", msg.substring(msg.indexOf("Z") + 1));
			MainActivity.mCardata
					.setAngle_z(msg.substring(msg.indexOf("Z") + 1));
		}
		if (msg.contains("移动有")) {
			MainActivity.mCardata.setMove("有");
		}
		if (msg.contains("移动无")) {
			MainActivity.mCardata.setMove("无");
		}

		if (msg.contains("有") && !msg.contains("移动")) {
			MainActivity.mCardata.setDis(msg.substring(msg.indexOf("有") + 1,
					msg.indexOf("有") + 6) + " mm");
		}
		if (msg.contains("无") && !msg.contains("移动")) {
			MainActivity.mCardata.setDis("无");
		}
	}
}
