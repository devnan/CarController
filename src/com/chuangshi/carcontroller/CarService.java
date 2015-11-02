package com.chuangshi.carcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import android.R.string;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Allen Lin
 * @date 2015-10-31
 * @desc：
 */
public class CarService extends Thread {
	public Socket mSocket;
	public BufferedReader br;
	public InputStream is;

	public CarService(Socket mSocket) throws IOException {
		this.mSocket = mSocket;
		// br = new BufferedReader(new
		// InputStreamReader(mSocket.getInputStream()));
		is = mSocket.getInputStream();
	}

	@Override
	public void run() {

		Log.d("MainActivity", "小车服务线程,负责接收小车的数据");

		try {
			int msg = is.read();
			Log.d("MainActivity", "小车数据" + msg);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String msg = null;
		//
		// try {
		// while ((msg = br.readLine()) != null) {
		// {
		// Log.d("MainActivity", "得到msg" + msg);
		// }
		//
		// }
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// Log.d("MainActivity", "br断开");
		// }
		super.run();
	}
}
