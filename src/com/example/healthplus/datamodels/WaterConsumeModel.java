package com.example.healthplus.datamodels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WaterConsumeModel {
	EachWaterEntry[]consumptionArray;
	String total_consumption;
	String goal;
	String remAmount;
	
	public WaterConsumeModel(JSONObject obj){
		try {
			this.consumptionArray = getConsumptionArrayfromJson(obj.getJSONArray("water"));
			this.total_consumption = new JSONObject(obj.getString("summary")).getString("water");
			this.goal = "1892.71";
			this.remAmount = getRemaining();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getRemaining(){
		double g = Double.parseDouble(goal);
		double c = Double.parseDouble(total_consumption);
		if(c>g){
			return "0";
		}else{
			return (String.valueOf(g-c));
		}
	}
	

	
	public String getRemAmount() {
		return remAmount;
	}

	public void setRemAmount(String remAmount) {
		this.remAmount = remAmount;
	}

	private EachWaterEntry[] getConsumptionArrayfromJson(JSONArray array){
		EachWaterEntry[] res =new EachWaterEntry[array.length()];
		
			try {
				for(int i=0;i<array.length();i++){
				EachWaterEntry q = new EachWaterEntry(array.getJSONObject(i).getString("amount"));
				res[i]=q;
				
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		return res;
	}
	
	/*public String CalculatetotalConsumption(){
		double res=0;
		for(int i=0;i<consumptionArray.length;i++){
			res += Double.parseDouble(consumptionArray[i].getQuantity());
			
		}
		return String.valueOf(res);
	}*/
	public EachWaterEntry[] getConsumptionArray() {
		return consumptionArray;
	}
	public void setConsumptionArray(EachWaterEntry[] consumptionArray) {
		this.consumptionArray = consumptionArray;
	}
	public String getTotal_consumption() {
		return total_consumption;
	}
	public void setTotal_consumption(String total_consumption) {
		this.total_consumption = total_consumption;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	

}

class EachWaterEntry{
	String quantity;
	
	public EachWaterEntry(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
