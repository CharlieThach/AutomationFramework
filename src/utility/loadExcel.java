package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class loadExcel {

	String filePath;
	FileInputStream fs ;
	Sheet sh; 
	Workbook wb; 
	int numOfRow;
	int numOfCol; 
	String[][] arrRec; 

	public loadExcel(){
		filePath = "src/testCase/booktest.xls";
		numOfRow = 0;
		numOfCol = 0; 
		arrRec = null; 
	}
	public loadExcel(String path){
		filePath = path;
		numOfRow = 0;
		numOfCol = 0; 
		arrRec = null; 
	}
	
	public String[][] readExcel(){
	 	
		try {
			fs = new FileInputStream(filePath);
			wb = Workbook.getWorkbook(fs);
		} catch (FileNotFoundException e) {
			System.out.println("Can't open the file");
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sh = wb.getSheet("Sheet1");
		numOfRow = sh.getRows();
		numOfCol = sh.getColumns(); 
		
		arrRec = new String[numOfRow-1][numOfCol];
		for (int i=1; i<numOfRow; i++){
			for(int j =0; j<numOfCol; j++){
				arrRec[i-1][j]= sh.getCell(j,i).getContents();
			}	
		}
		wb.close();
		return arrRec; 
	}
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//	public String[][] loadExcel(){
//	XSSFWorkbook workbook;
//	XSSFSheet sheet = null; 
//	int i = 0; 
//	String[][] array = null;  
//	
//	
//	try {
//		FileInputStream file = new FileInputStream(new File("src/testCase/booktest.xlsx"));
//		//file = new FileInputStream("src/testCase/booktest.xlsx");
//		workbook = new XSSFWorkbook (file); 
//		sheet = workbook.getSheetAt(0);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//Iterator<Row> rowIterator = sheet.iterator();
////Iterator<Cell> cell = row.next().cellIterator();
//
//while(rowIterator.hasNext()){
//	Row row = rowIterator.next(); 
//	
//	//for each row, iterate
//	Iterator<Cell> cellIterator = row.cellIterator();
//	while(cellIterator.hasNext()){
//		Cell cell = cellIterator.next(); 
//		switch((int)cell.getNumericCellValue()){
//		case 0:
//			array[i][0] = cell.getStringCellValue();
//			break;
//		case 1:
//			array[i][1] = cell.getStringCellValue();
//			break;
//		case 2:
//			array[i][2] = cell.getStringCellValue();
//			break;
//		case 3:
//			array[i][3] = cell.getStringCellValue();
//			break; 
//		
//		}
//			
//		i++;
//	}
//}
//return array; 
//}
}
