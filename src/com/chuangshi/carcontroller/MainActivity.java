package com.chuangshi.carcontroller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	public Button bt_go;
	public Button bt_back;
	public Button bt_stop;
	public Button bt_left;
	public Button bt_right;
	public TextView tv_show;
	// static Socket CarClient = null;
	// static ServerSocket server = null;
	static OutputStream pw = null;
	static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.CONNECT:

				pw = Server.getPrintWriter();
				if (pw != null) {
					Log.d("MainActivity", "得到PrintWriter");

				} else {
					Log.d("MainActivity", "PrintWriter为null");
				}

				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindView();
		new Server().start();// 开启服务器

	}

	private void bindView() {
		bt_go = (Button) findViewById(R.id.bt_go);
		bt_back = (Button) findViewById(R.id.bt_back);
		bt_stop = (Button) findViewById(R.id.bt_stop);
		bt_left = (Button) findViewById(R.id.bt_left);
		bt_right = (Button) findViewById(R.id.bt_right);
		tv_show = (TextView) findViewById(R.id.show);
		bt_go.setOnClickListener(this);
		bt_back.setOnClickListener(this);
		bt_stop.setOnClickListener(this);
		bt_left.setOnClickListener(this);
		bt_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.bt_go:
				if (pw != null) {
					Log.d("MainActivity", "发1");

					pw.write(1);

				}

				break;
			case R.id.bt_back:
				pw.write(2);
				Log.d("MainActivity", "发2");

				break;
			case R.id.bt_stop:
				pw.write(5);
				break;
			case R.id.bt_left:
				Log.d("MainActivity", "发.....");
				pw.write(3);
				break;
			case R.id.bt_right:
				Log.d("MainActivity", "发.....");
				pw.write(4);
				break;

			default:
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
