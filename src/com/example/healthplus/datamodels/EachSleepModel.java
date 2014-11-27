package com.example.healthplus.datamodels;

public class EachSleepModel{
	String time;
	String val;
	String efficiency;

	public EachSleepModel(String time, String val,String efficiency){
		this.time = time;
		this.val = val;
		this.efficiency = efficiency;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}
}
