package utility;

import java.util.ArrayList;
import java.util.Arrays;

public class testing {

	public static void main(String arg[]){
		
		String [][] rec =null; 
		loadExcel load = new loadExcel();
		
		rec = load.readExcel(); 
		
		for(String[] arr: rec){
			System.out.println(Arrays.toString(arr));
			
		}	
		
	}
}
