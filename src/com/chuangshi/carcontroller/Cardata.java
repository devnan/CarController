package com.chuangshi.carcontroller;


/**
 * @author Allen Lin
 * @date 2015-11-5
 * @desc 小车数据实体类
 */
public class Cardata {

	private String angle_x;
	private String angle_y;
	private String angle_z;
	private String dis;
	private String move;
	private int speed;
	
	public Cardata(String angle_x, String angle_y, String angle_z, String dis,
			String move, int speed) {
		super();
		this.angle_x = angle_x;
		this.angle_y = angle_y;
		this.angle_z = angle_z;
		this.dis = dis;
		this.move = move;
		this.speed = speed;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getDis() {
		return dis;
	}

	public int addSpeed() {
		if (speed != 20) {
			speed++;

		}
		return speed;
	}

	public int subSpeed() {
		if (speed != 1) {
			speed--;
		}
		return speed;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	public Cardata() {
		super();
	}

	public String getAngle_x() {
		return angle_x;
	}

	public void setAngle_x(String angle_x) {
		this.angle_x = angle_x;
	}

	public String getAngle_y() {
		return angle_y;
	}

	public void setAngle_y(String angle_y) {
		this.angle_y = angle_y;
	}

	public String getAngle_z() {
		return angle_z;
	}

	public void setAngle_z(String angle_z) {
		this.angle_z = angle_z;
	}

}
