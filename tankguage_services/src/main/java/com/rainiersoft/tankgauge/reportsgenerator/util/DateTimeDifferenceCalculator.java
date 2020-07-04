package com.rainiersoft.tankgauge.reportsgenerator.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeDifferenceCalculator {
	
	public Long[] timeDifference(String startDateTime, String endDateTime) {

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date startDateAndTime = null;
		Date endDateAndTime = null;
		Long[] dtDiff = new Long[4];

		try {
			
			startDateAndTime = dateTimeFormat.parse(startDateTime);
			endDateAndTime = dateTimeFormat.parse(endDateTime);

			// Difference in milliseconds
			long diff = endDateAndTime.getTime() - startDateAndTime.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			
			dtDiff[0] = diffSeconds;
			dtDiff[1] = diffMinutes;
			dtDiff[2] = diffHours;
			dtDiff[3] = diffDays;

		} catch (Exception e) {
		
			e.printStackTrace();
		}

		return dtDiff;

	}
}