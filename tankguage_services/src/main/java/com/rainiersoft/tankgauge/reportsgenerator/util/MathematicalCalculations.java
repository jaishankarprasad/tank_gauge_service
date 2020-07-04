package com.rainiersoft.tankgauge.reportsgenerator.util;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.rainiersoft.tankgauge.reportsgenerator.definitions.ReportConstants;

public class MathematicalCalculations {

	/*
	 * Method to retrieve maximum value from the list of string array
	 */

	public double getMaximum(List<String[]> bufferListRowData, int columnIndex) {

		Vector<Double> vecResultsetValues = new Vector<Double>();

		try {

			java.util.Iterator<String[]> buffListIterator = bufferListRowData.iterator();

			while (buffListIterator.hasNext()) {

				String rowValues[] = buffListIterator.next();
				String columnValue = rowValues[columnIndex];

				if (columnValue != null) {

					if (!columnValue.trim().isEmpty()
							&& !columnValue.trim().equalsIgnoreCase(ReportConstants.EMPTY_REPLACEMENT_VALUE))
						vecResultsetValues.add(Double.parseDouble(columnValue));

				}

			}

		} catch (Exception excp) {

			excp.printStackTrace();

		}

		if (vecResultsetValues.size() == 0)
			return 0.0;

		return Collections.max(vecResultsetValues);

	}

	/*
	 * Method to retrieve minimum value from the list of string array
	 */

	public double getMinimum(List<String[]> bufferListRowData, int columnIndex) {

		Vector<Double> vecResultsetValues = new Vector<Double>();

		try {

			java.util.Iterator<String[]> buffListIterator = bufferListRowData.iterator();

			while (buffListIterator.hasNext()) {

				String rowValues[] = buffListIterator.next();
				String columnValue = rowValues[columnIndex];

				if (columnValue != null) {

					if (!columnValue.trim().isEmpty()
							&& !columnValue.trim().equalsIgnoreCase(ReportConstants.EMPTY_REPLACEMENT_VALUE)) {

						vecResultsetValues.add(Double.parseDouble(columnValue));

					}

				}

			}

		} catch (Exception excp) {

			excp.printStackTrace();

		}

		if (vecResultsetValues.size() == 0)
			return 0;

		return Collections.min(vecResultsetValues);

	}

	/*
	 * Method to retrieve average value from the list of string array
	 */

	public double getAverage(List<String[]> bufferListRowData, int columnIndex) {

		double averageValue = 0.0;
		int totalRowCount = 0;

		try {

			java.util.Iterator<String[]> buffListIterator = bufferListRowData.iterator();

			while (buffListIterator.hasNext()) {

				String rowValues[] = buffListIterator.next();
				String columnValue = rowValues[columnIndex];

				totalRowCount++;

				if (columnValue != null) {

					if (!columnValue.trim().isEmpty()
							&& !columnValue.trim().equalsIgnoreCase(ReportConstants.EMPTY_REPLACEMENT_VALUE))
						averageValue = averageValue + (Double.parseDouble(columnValue));

				}

			}

		} catch (Exception excp) {

			excp.printStackTrace();

		}

		return (averageValue / totalRowCount);

	}

}
