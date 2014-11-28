package com.example.healthplus.datamodels;

public class FoodModel {
	//String goal;
	String cal;
	String carbs;
	String fat;
	String fiber;
	String protein;
	String sodium;
	
	public FoodModel(String cal, String carbs, String fat,
			String fiber, String protein, String sodium) {
	
		//this.goal = goal;
		this.cal = cal;
		this.carbs = carbs;
		this.fat = fat;
		this.fiber = fiber;
		this.protein = protein;
		this.sodium = sodium;
	}
	
//	public String getGoal() {
//		return goal;
//	}
//	public void setGoal(String goal) {
//		this.goal = goal;
//	}
	public String getCal() {
		return cal;
	}
	public void setCal(String cal) {
		this.cal = cal;
	}
	public String getCarbs() {
		return carbs;
	}
	public void setCarbs(String carbs) {
		this.carbs = carbs;
	}
	public String getFat() {
		return fat;
	}
	public void setFat(String fat) {
		this.fat = fat;
	}
	public String getFiber() {
		return fiber;
	}
	public void setFiber(String fiber) {
		this.fiber = fiber;
	}
	public String getProtein() {
		return protein;
	}
	public void setProtein(String protein) {
		this.protein = protein;
	}
	public String getSodium() {
		return sodium;
	}
	public void setSodium(String sodium) {
		this.sodium = sodium;
	}
	
	
}
