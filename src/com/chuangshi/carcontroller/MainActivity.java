package com.chuangshi.carcontroller;

import java.io.OutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	/******** UI界面 ********/
	public Button bt_go;
	public Button bt_back;
	public Button bt_stop;
	public Button bt_left;
	public Button bt_right;
	public Button bt_speedup;
	public Button bt_speeddown;
	public String[] toast_array;
	public static ProgressDialog pd;
	public TextView tv_dis;
	public TextView tv_x;
	public TextView tv_y;
	public TextView tv_z;
	public TextView tv_move;
	public TextView tv_speed;
	public LinearLayout dataLinearLayout;
	public LinearLayout control_ly;
	/******** 其他 ********/
	static ConnectThread connectThread = null;
	static OutputStream pw = null;

	public static Cardata mCardata = null;
	final Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 连接成功
			case Constant.CONNECT:

				pw = connectThread.getPrintWriter();// 返回PrintWriter对象

				pd.dismiss();
				Toast.makeText(MainActivity.this, "连接小车成功", Toast.LENGTH_LONG)
						.show();

				Log.d("MainActivity", "连接成功，返回PrintWriter对象");
				break;
			// 连接失败
			case Constant.UNCONNECT:

				pd.dismiss();
				Toast.makeText(MainActivity.this, "连接小车失败", Toast.LENGTH_LONG)
						.show();
				Log.d("MainActivity", "连接失败");
				break;
			case Constant.DATA:
				tv_dis.setText(mCardata.getDis());
				tv_x.setText(mCardata.getAngle_x());
				tv_y.setText(mCardata.getAngle_y());
				tv_z.setText(mCardata.getAngle_z());
				tv_move.setText(mCardata.getMove());

				break;
			}

			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		bindView();
		toast_array = this.getResources().getStringArray(R.array.toast_tips);
		mCardata = new Cardata();
		connectThread = new ConnectThread(mHandler);
		connectThread.start();
		initView();

	}

	private void initView() {
		pd = new ProgressDialog(MainActivity.this);
		pd.setTitle("智能小车控制器");
		pd.setMessage("正在连接小车，请等待...");
		pd.setCancelable(false);// 设置对话框能否用back键取消
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置对话框风格
		pd.show();

	}

	private void bindView() {
		bt_go = (Button) findViewById(R.id.bt_go);
		bt_back = (Button) findViewById(R.id.bt_back);
		bt_stop = (Button) findViewById(R.id.bt_stop);
		bt_left = (Button) findViewById(R.id.bt_left);
		bt_right = (Button) findViewById(R.id.bt_right);
		bt_speedup = (Button) findViewById(R.id.bt_speedup);
		bt_speeddown = (Button) findViewById(R.id.bt_speeddown);
		tv_dis = (TextView) findViewById(R.id.tv_dis);
		tv_x = (TextView) findViewById(R.id.tv_x);
		tv_y = (TextView) findViewById(R.id.tv_y);
		tv_z = (TextView) findViewById(R.id.tv_z);
		tv_move = (TextView) findViewById(R.id.tv_move);
		tv_speed = (TextView) findViewById(R.id.tv_speed);
		dataLinearLayout = (LinearLayout) findViewById(R.id.dataLinerly);
		control_ly = (LinearLayout) findViewById(R.id.control_ly);

		bt_go.setOnClickListener(this);
		bt_back.setOnClickListener(this);
		bt_stop.setOnClickListener(this);
		bt_left.setOnClickListener(this);
		bt_right.setOnClickListener(this);
		bt_speedup.setOnClickListener(this);
		bt_speeddown.setOnClickListener(this);
		dataLinearLayout.setOnClickListener(this);
		control_ly.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		if (pw != null) {
			switch (v.getId()) {
			case R.id.bt_go:

				new sendThread(1).start();
				// pw.write(1);

				break;
			case R.id.bt_back:
				new sendThread(2).start();
				// pw.write(2);

				break;
			case R.id.bt_stop:
				new sendThread(5).start();
				// pw.write(5);

				break;
			case R.id.bt_left:
				new sendThread(3).start();
				// pw.write(3);

				break;
			case R.id.bt_right:
				new sendThread(4).start();
				// pw.write(4);

				break;

			case R.id.bt_speedup:

				tv_speed.setText(mCardata.addSpeed() + "档");
				new sendThread(8).start();
				// pw.write(8);

				break;
			case R.id.bt_speeddown:
				tv_speed.setText(mCardata.subSpeed() + "档");
				new sendThread(9).start();
				// pw.write(9);

				break;
			case R.id.control_ly:

				int index = (int) (Math.random() * (toast_array.length - 1));
				Toast.makeText(MainActivity.this, toast_array[index],
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.dataLinerly:

				int index2 = (int) (Math.random() * (toast_array.length - 1));
				Toast.makeText(MainActivity.this, toast_array[index2],
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	}
}
