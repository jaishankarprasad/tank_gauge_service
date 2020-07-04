package com.rainiersoft.tankgauge.reportsgenerator.definitions;

public class ReportConstants {

	public static final String REPORT_HEADER = "Endress & Hauser";
	public static final String REPORT_HEADER_FONT = "Calibri";
	public static final String REPORT_SUB_HEADER_FONT = "Calibri";
	public static final String REPORT_CONFIGURATION_FILE = "tgreportsconfiguration.properties";
	public static final String LOGGING_PROPERTIES_FILE = "tglogging.properties";

	public static final String DB_DRIVER_CLASS = "db.driverclass";
	public static final String DB_URL = "db.url";
	public static final String DB_USERNAME = "db.username";
	public static final String DB_PASSWORD = "db.password";
	
	// Defining columns based on the report format
	public static final String START_DATE_TIME = "START DATE AND TIME :";
	public static final String END_DATE_TIME = "END DATE AND TIME :";
	public static final String PRINT_DATE_TIME = "PRINT DATE AND TIME :";
	public static final String COMPUTER_ID = "EQUIPMENT ID :";
	public static final String DATE_TIME = "Date & Time";
	public static final String DESCRIPTION = "Description";
	public static final String STATUS = "Status";
	public static final String VALUE = "Value";
	public static final String UNIT = "Unit";
	public static final String COMMENTS = "Comments";
	public static final String REMARKS = "REMARKS :";
	public static final String PRINTED_BY = "Printed By :";
	public static final String CHECKED_BY = "Checked By :";
	public static final String VERIFIED_BY = "Verified By :";
	
	public static final String DEFAULT_ALARM_FILENAME_PREFIX = "Alarm_";
	public static final String DEFAULT_BUFFER_FILENAME_PREFIX = "Buffer_";
	public static final String DEFAULT_EVENT_FILENAME_PREFIX = "Event_";
	public static final String DEFAULT_CAPTURE_FILENAME_PREFIX = "Capture_";
	public static final String DEFAULT_DSP2_FILENAME_PREFIX = "DSP2_";
	public static final String DEFAULT_DSP1_FILENAME_PREFIX = "DSP1_";
	public static final String DEFAULT_FRMTN_FILENAME_PREFIX = "Fermentation_";
	public static final String DEFAULT_SNTZEQUP_FILENAME_PREFIX = "SanitizedEquipment_";
	public static final String DEFAULT_CRITICAL_ALARM_FILENAME_PREFIX = "CriticalAlarm_";
	public static final String DEFAULT_NON_CRITICAL_ALARM_FILENAME_PREFIX = "NonCriticalAlarm_";
	public static final String DEFAULT_EXT_ALARM_FILENAME_PREFIX = "ExtendedAlarm_";
	
	public static final String DEFAULT_FILENAME_EXTENSION = ".pdf";
	public static final String DEFAULT_PDF_PAGE_ORIENTATION = "PORTRAIT";
	public static final String PAGE_LAYOUT_PORTRAIT = "PORTRAIT";
	public static final String PAGE_LAYOUT_LANDSCAPE = "LANDSCAPE";
	
	public static final String EMPTY_REPLACEMENT_VALUE = "NA";

}
