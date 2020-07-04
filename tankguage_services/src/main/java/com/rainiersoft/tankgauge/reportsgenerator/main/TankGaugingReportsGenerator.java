package com.rainiersoft.tankgauge.reportsgenerator.main;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rainiersoft.tankgauge.reportsgenerator.definitions.InventoryReportConstants;
import com.rainiersoft.tankgauge.reportsgenerator.definitions.ReportConstants;
import com.rainiersoft.tankgauge.reportsgenerator.util.Utilities;
import com.rainiersoft.tankgauge.reportsgenerator.beans.PropertyBeans;
import com.rainiersoft.tankgauge.reportsgenerator.core.inventory.InventoryReportsGenerator;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

public class TankGaugingReportsGenerator {

	public PropertyBeans propertyBeans = null;
	public Connection databaseConnection = null;
	public Utilities genericUtil = new Utilities();

	public Document pdfDocument = null;
	public PdfWriter pdfWriter = null;

	// Variable to store report sub-headed specific to report type
	public static String reportSubHeader = null;
	
	// Variable to store page layout type specific to report type
	public static String reportPageOrientation = ReportConstants.DEFAULT_PDF_PAGE_ORIENTATION;

	public static Properties repConfigProperties = null;
	public static String reportType = null;
	public static String reportStartDateAndTime = null;
	public static String reformattedStartDateAndTime = null;
	public static String reformattedEndDateAndTime = null;

	public static Logger reportsLogger = Logger.getLogger(TankGaugingReportsGenerator.class);
	
	public static String fullFilePath = null;

	public String generatePDFReports(String args[]) {

		TankGaugingReportsGenerator reportsGenerator = new TankGaugingReportsGenerator();
		reportsGenerator.initializeLogger();

		TankGaugingReportsGenerator.reportsLogger.debug("Inside generatePDFReports()...");

		if (args.length == 0 || args.length > 2 || args.length < 2) {

			TankGaugingReportsGenerator.reportsLogger.debug("Invalid input arguments");
			TankGaugingReportsGenerator.reportsLogger.debug
				("Expected format: TankGaugingReportsGenerator <Report Type> <Start Date & Time>");
			System.exit(0);

		}

		reportType = args[0];
		reportStartDateAndTime = args[1];
		
		TankGaugingReportsGenerator.reportsLogger.debug("Report Type - " + reportType);
		TankGaugingReportsGenerator.reportsLogger.debug("Report Start Date & Time - " + reportStartDateAndTime);
		
		try {
			
			reportsGenerator.initializeReportConfiguration();
			reportsGenerator.validateInputPathDirectory(reportType);
			reportsGenerator.initializePropertyBeans();
			reportsGenerator.initializeDatabaseConnection();
			reportsGenerator.setPageLayoutBasedOnReportType();
			reportsGenerator.processSQLResultAndGeneratePDF();

		} catch (SQLException sqlExcp) {

			TankGaugingReportsGenerator.reportsLogger
					.debug("Encountered SQLServerException - " + sqlExcp.getLocalizedMessage());

		} catch (Exception excp) {

			TankGaugingReportsGenerator.reportsLogger.debug("Exception - " + excp.getMessage());
			excp.printStackTrace();

		}
		
		return fullFilePath;

	}
	
	public void initializeLogger() {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Initializing logger...");

		// PropertiesConfigurator is used to configure logger from properties
		// file
		PropertyConfigurator.configure(ReportConstants.LOGGING_PROPERTIES_FILE);

		// Log in console and log file
		reportsLogger.debug(getClass().getName() + " : Log4j appender configuration is successful!!");

	}

	public void initializeReportConfiguration() throws Exception
	{
		File directory = new File("./");
		System.out.println(directory.getAbsolutePath());
		
		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Loading report configurations from file...");

		repConfigProperties = new Properties();
		InputStream fileInputStream = 
				this.getClass().getResourceAsStream("/" + ReportConstants.REPORT_CONFIGURATION_FILE);
		repConfigProperties.load(fileInputStream);
		

		//repConfigProperties.load(new FileReader(ReportConstants.REPORT_CONFIGURATION_FILE));
		
		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Loading of properties file successful...");

	}

	public void initializePropertyBeans() {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Initializing property beans...");

		propertyBeans = new PropertyBeans();
		propertyBeans.setDbDriver(repConfigProperties.getProperty(ReportConstants.DB_DRIVER_CLASS));
		propertyBeans.setDbURL(repConfigProperties.getProperty(ReportConstants.DB_URL));
		propertyBeans.setDbUserName(repConfigProperties.getProperty(ReportConstants.DB_USERNAME));
		propertyBeans.setDbPassword(repConfigProperties.getProperty(ReportConstants.DB_PASSWORD));

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Initialization of property beans successful...");

	}

	public void validateInputPathDirectory(String reportType) {
		
		TankGaugingReportsGenerator.reportsLogger
			.debug(getClass().getName() + " : Verifying configuration parameters for report type - " + reportType);
		
		System.out.println("reportType::"+reportType);
		
		String inputDirPath = null;
	/*	
		switch (reportType) {
		
			case InventoryReportConstants.INVENTORY_REPORT_TYPE_INPUT_PARAMETER: {
				
				inputDirPath = 
						repConfigProperties.getProperty(InventoryReportConstants.INVENTORY_PDF_REPORT_PATH_NAME);
				break;
				
			}
		
		}*/
		
		if (reportType.equalsIgnoreCase(InventoryReportConstants.INVENTORY_REPORT_TYPE_INPUT_PARAMETER))
		{
		
		// InventoryReportConstants.INVENTORY_REPORT_TYPE_INPUT_PARAMETER: {
			
			inputDirPath = 
					repConfigProperties.getProperty(InventoryReportConstants.INVENTORY_PDF_REPORT_PATH_NAME);
		//	break;
			
		}
	
	
		
		if (inputDirPath == null || inputDirPath.length() == 0) {
				
				reportsLogger.debug(getClass().getName() + " : Output directory path is not defined...");
				reportsLogger.debug(getClass().getName() + " : Exiting from the system...");
				System.exit(0);
				
		} 
		else 
		{
			File dir = new File(inputDirPath);

			 // Check if it's a directory
			if (!dir.isDirectory()) {
				
				reportsLogger.debug(getClass().getName() + " : Invalid output directory specified in configuration file...");
				reportsLogger.debug(getClass().getName() + " : Exiting from the system...");
				System.exit(0);
				
			}
			
		}

	}

	/*
	 * Method to initialize database connection 
	 */
	
	public void initializeDatabaseConnection() throws Exception {

		reportsLogger.debug(getClass().getName() + " : Connecting to database - " + propertyBeans.getDbURL());

		Class.forName(propertyBeans.getDbDriver());
		databaseConnection = DriverManager.getConnection(propertyBeans.getDbURL(), propertyBeans.getDbUserName(),
				propertyBeans.getDbPassword());

		reportsLogger.debug(getClass().getName() + " : Connected to database successfully...");

	}

	
	public void setPageLayoutBasedOnReportType() {
		
		String reportPageLayoutType = genericUtil.getPageLayoutType(TankGaugingReportsGenerator.reportType);
		reportPageOrientation = repConfigProperties.getProperty(reportPageLayoutType);

		if (reportPageOrientation == null) {
			
			reportPageOrientation = ReportConstants.DEFAULT_PDF_PAGE_ORIENTATION;
			
		}
			
		reportsLogger.debug(getClass().getName() + " : Page Layout Orientation - " + reportPageOrientation);
		
	}
	
	public void processSQLResultAndGeneratePDF() throws Exception {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Preparing sql create statement...");

		Statement sqlStatement = databaseConnection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
		System.out.println(sqlStatement);

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Report type - " + reportType);
/*
		switch (reportType) {

			case InventoryReportConstants.INVENTORY_REPORT_TYPE_INPUT_PARAMETER: {
	
				reportSubHeader = repConfigProperties.getProperty(InventoryReportConstants.INVENTORY_PDF_REPORT_SUB_HEADER);
				processInventorySQLResultAndGeneratePDF(sqlStatement);
				break;
	
			}

		}*/

		if (reportType.equalsIgnoreCase(InventoryReportConstants.INVENTORY_REPORT_TYPE_INPUT_PARAMETER))
		{
			reportSubHeader = repConfigProperties.getProperty(InventoryReportConstants.INVENTORY_PDF_REPORT_SUB_HEADER);
			processInventorySQLResultAndGeneratePDF(sqlStatement);
	
			}

		// Close database connection
		databaseConnection.close();

	}
	
	/*
	 * Method to handle generation of inventory reports 
	 */
	
	public void processInventorySQLResultAndGeneratePDF(Statement sqlStatement) throws Exception {
		
		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Processing Inventory SQL Resultset");
		
		System.out.println("Processing Inventory SQL Resultset");
		
		// Generate inventory report
		new InventoryReportsGenerator(sqlStatement);
		
	}
	
}