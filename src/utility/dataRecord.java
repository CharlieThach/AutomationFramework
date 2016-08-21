/*
 * Purpose of this class is to record all the data from excel
 * there are setter/getter
 */

package utility;
public class dataRecord {
	private String testID;
	private String description;
	private String steps; 
	private String expectVal;
	
	public String getMethodName(){
		return testID; 
	}
	public String getDescription(){
		return description;
	}
	public String getSteps(){
		return steps;
	}
	public String getExpectedValue(){
		return expectVal;
	}
	
	//set setter
	public void setTestID(String id){
		this.testID = id;
	}
	public void setDescription(String name){
		this.description = name;
	}
	public void setSteps(String auth){
		this.steps = auth; 
	}
	public void setExpectedValue(String val){
		this.expectVal = val; 
	}
}
