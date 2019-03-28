package com.selenium.mindmatrix.project.util;

import java.util.Hashtable;


public class DataUtil {
	
	public static Object[][] getTestData(Xls_Reader xls, String testCaseName){
		
		String sheetName = "Data";
		
		
		//Read data for only testCaseName
		int testStartRowNum=1;
		while(!xls.getCellData(sheetName,0,testStartRowNum).equals(testCaseName)) {
			testStartRowNum++ ;	
		}
		System.out.println("Test is written on row number "+testStartRowNum);
		
		int colStartRowNum = testStartRowNum+1;
		int dataStartRowNum = testStartRowNum+2;
		
		//calculate total rows of data
		int rows=0;
		while(!xls.getCellData(sheetName,0,dataStartRowNum+rows).equals("")) { 
			rows++;
		}
		System.out.println("total rows in the test are = "+rows);
		
		//calculate total columns of data
		int cols = 0;
		while(!xls.getCellData(sheetName,cols,colStartRowNum).equals("")) {
			cols++;
		}
		System.out.println("total columns in the test are = "+cols);
		
		// read the data
		Object[][] data = new Object[rows][1];
		int dataRow = 0;
		
		Hashtable<String,String> table = null;

			for(int rNum =dataStartRowNum;rNum<dataStartRowNum+rows;rNum++) {
			  table = new Hashtable<String,String>();
				for (int cNum = 0;cNum<cols;cNum++) {
					String key = xls.getCellData(sheetName,cNum,colStartRowNum);
					String value = xls.getCellData(sheetName,cNum,rNum);
					table.put(key,value);
				
				}
				data[dataRow][0]=table;
				dataRow++;
			}
			 return data;
			 
	}
	public static boolean isRunnable(String testName, Xls_Reader xls) {
		String sheet = "AutoTestcases";
		int rows = xls.getRowCount(sheet);
		for(int r=2;r<=rows;r++) {
			//String tName=xls.getCellData(sheet,0,r);
			String tName=xls.getCellData(sheet,"TCID",r);
			if(tName.equals(testName)) {
				//String runmode=xls.getCellData(sheet,1,r);
				String runmode=xls.getCellData(sheet,"Runmode",r);
				if(runmode.equals("Y"))
				return true;
				else 
				return false;
				
			}
		}
		return false;
	}

}
