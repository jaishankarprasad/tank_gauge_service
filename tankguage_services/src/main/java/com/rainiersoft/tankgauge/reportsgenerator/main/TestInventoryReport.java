package com.rainiersoft.tankgauge.reportsgenerator.main;

public class TestInventoryReport {

	public static void main(String args[]) {
		
		TankGaugingReportsGenerator tgrg = new TankGaugingReportsGenerator();
		String filePath = tgrg.generatePDFReports(args);
		System.out.println("File Path - " + filePath);
		
	}
	
}
