package edu.vanderbilt.cs390.androidrmi.db;

/**
 * Data
 * @author Li
 *
 */
public class TestData {
	private long id_;
	private double speed_;
	private double battery_;
	private long difficulty_;
	private long time_;
	// 0 is remote, 1 is local
	private int isLocal_;

	public TestData() {
		this.id_ = -1;
		this.speed_ = -1;
		this.battery_ = -1;
		this.difficulty_ = -1;
		this.time_ = -1;
		this.isLocal_ = -1;
	}

	public void setLocal(int isLocal) {
		this.isLocal_ = isLocal;
	}

	public int getLocal() {
		return this.isLocal_;
	}

	public void setID(long id) {
		this.id_ = id;
	}

	public long getID() {
		return this.id_;
	}

	public double getSpeed() {
		return this.speed_;
	}

	public double getBattery() {
		return this.battery_;
	}

	public long getDiff() {
		return this.difficulty_;
	}

	public long getTime() {
		return this.time_;
	}

	public void setSpeed(double speed) {
		this.speed_ = speed;
	}

	public void setBattery(double battery) {
		this.battery_ = battery;
	}

	public void setDiff(long  diff) {
		this.difficulty_ = diff;
	}

	public void setTime(long time) {
		this.time_ = time;
	}
}
