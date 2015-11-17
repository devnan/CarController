package com.chuangshi.carcontroller;

import java.io.IOException;

/**
 * @author Allen Lin
 * @date 2015-11-7
 * @desc 线程负责发送指令
 */
public class sendThread extends Thread {
	public int num;

	public sendThread(int num) {
		this.num = num;
	}

	@Override
	public void run() {
		try {
			MainActivity.pw.write(num);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
