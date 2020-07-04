package com.rainiersoft.tankgauge.reportsgenerator.core.inventorydata;

import com.rainiersoft.tankgauge.reportsgenerator.definitions.InventoryReportConstants;
import com.rainiersoft.tankgauge.reportsgenerator.main.TankGaugingReportsGenerator;
import com.rainiersoft.tankgauge.reportsgenerator.interfaces.GenericPDFReportGenerator;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.Properties;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class InventoryDataPDFReportsGenerator extends GenericPDFReportGenerator {

	PdfPTable pdfTable = null;
	PdfPTable pdfTankDetails = null;
	PdfPTable pdfProperties = null;
	PdfPTable pdfGroupDetails = null;

	@Override
	public Document generatePDFReport(Document pdfDocument, ResultSet sqlResultSet, List<String[]> alarmListRowData,
			Properties repConfigProperties) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Generating inventory pdf report...");

		// Defining constant row header values required in the PDF file
		String tableHeaderValues[] = { "Tank Name", "Unit Name", "Location", "Product", "Site Name" };
		String propertyHeaderValues[] = { "Property Name", "Property Value" };
		String[] groupNames = TankGaugingReportsGenerator.repConfigProperties
				.getProperty(InventoryReportConstants.INVENTORY_GROUP_NAMES)
				.split(InventoryReportConstants.INVENTORY_GROUP_NAMES_DELIMITER);

		try {

			pdfTankDetails = new PdfPTable(1);
			pdfTankDetails.setTotalWidth(1000f);
			pdfTankDetails.setWidthPercentage(100f);

			// Configuring table header to repeat in every page
			// pdfTankDetails.setHeaderRows(2);

			pdfTankDetails.addCell(addTankGroups(pdfTable, "TANK DETAILS"));

			pdfTable = new PdfPTable(5);
			pdfTable.setWidths(new float[] { 1, 1, 1, 1, 1 });
			pdfTable.setTotalWidth(1000f);
			pdfTable.setWidthPercentage(100f);

			// Configuring table header to repeat in every page
			// pdfTable.setHeaderRows(2);

			this.addTableHeader(pdfTable, tableHeaderValues);
			this.addTableRows(pdfTable, alarmListRowData, "Tank3");

			pdfDocument.add(pdfTankDetails);
			pdfDocument.add(pdfTable);

			// pdfDocument.add(new Paragraph(Chunk.NEWLINE));

			pdfGroupDetails = new PdfPTable(1);
			pdfGroupDetails.setTotalWidth(1000f);
			pdfGroupDetails.setWidthPercentage(100f);

			pdfGroupDetails.addCell(addTankGroups(pdfTable, "MEASURED DATA"));
			pdfDocument.add(pdfGroupDetails);

			pdfTable = new PdfPTable(2);
			pdfTable.setWidths(new float[] { 1, 1 });
			pdfTable.setTotalWidth(1000f);
			pdfTable.setWidthPercentage(100f);

			this.addTableHeader(pdfTable, propertyHeaderValues);
			this.addPropertiesAndValues(pdfTable, alarmListRowData, groupNames[0]);

			pdfDocument.add(pdfTable);

			pdfGroupDetails = new PdfPTable(1);
			pdfGroupDetails.setTotalWidth(1000f);
			pdfGroupDetails.setWidthPercentage(100f);

			pdfGroupDetails.addCell(addTankGroups(pdfTable, "VOLUME DATA"));
			pdfDocument.add(pdfGroupDetails);

			pdfTable = new PdfPTable(2);
			pdfTable.setWidths(new float[] { 1, 1 });
			pdfTable.setTotalWidth(1000f);
			pdfTable.setWidthPercentage(100f);

			this.addTableHeader(pdfTable, propertyHeaderValues);
			this.addPropertiesAndValues(pdfTable, alarmListRowData, groupNames[1]);

			pdfDocument.add(pdfTable);

			pdfGroupDetails = new PdfPTable(1);
			pdfGroupDetails.setTotalWidth(1000f);
			pdfGroupDetails.setWidthPercentage(100f);

			pdfGroupDetails.addCell(addTankGroups(pdfTable, "MASS DATA"));
			pdfDocument.add(pdfGroupDetails);

			pdfTable = new PdfPTable(2);
			pdfTable.setWidths(new float[] { 1, 1 });
			pdfTable.setTotalWidth(1000f);
			pdfTable.setWidthPercentage(100f);

			this.addTableHeader(pdfTable, propertyHeaderValues);
			this.addPropertiesAndValues(pdfTable, alarmListRowData, groupNames[2]);

			pdfDocument.add(pdfTable);

			// Add table footer data
			// pdfDocument.add(this.addTableFooterValues());

		} catch (Exception excp) {

			TankGaugingReportsGenerator.reportsLogger
					.debug(getClass().getName() + " : Exception - " + excp.getMessage());

		}

		return pdfDocument;

	}

	public String[] retrieveMetadataFromSQLRS(ResultSet sqlResultSet) throws Exception {

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Retrieving sql resultset metatada...");

		ResultSetMetaData rsMetaData = sqlResultSet.getMetaData();
		String[] pdfTableHeader = new String[rsMetaData.getColumnCount()];

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : ResultSet metadata length - " + rsMetaData.getColumnCount());

		// Get column names; Column indexes start from 1
		for (int colIndex = 1; colIndex < rsMetaData.getColumnCount() + 1; colIndex++) {

			String columnLabel = rsMetaData.getColumnLabel(colIndex);
			pdfTableHeader[colIndex - 1] = columnLabel;

		}

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Retrieved inventory table header...");

		return pdfTableHeader;

	}

	public PdfPCell addTankGroups(PdfPTable pdfTable, String tankGroupName) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Adding header to the pdf file...");

		PdfPCell pdfCellHeader = new PdfPCell();
		pdfCellHeader.setUseBorderPadding(true);
		pdfCellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfCellHeader.setBorderColor(BaseColor.BLACK);
		pdfCellHeader.setBorderWidth(0.2f);
		pdfCellHeader.setVerticalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setPaddingTop(2.0f);
		pdfCellHeader.setPaddingBottom(2.0f);
		pdfCellHeader.setPhrase(new Phrase(15f, tankGroupName,
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.BOLD)));
		// pdfTankDetails.addCell(pdfCellHeader);
		return pdfCellHeader;

	}

	public void addTableHeader(PdfPTable pdfTable, String[] pdfTableHeader) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Adding header to the pdf file...");

		// Adding headers based on aliases added in the sql query
		for (int rowIndex = 0; rowIndex < pdfTableHeader.length; rowIndex++) {

			String columnHeader = pdfTableHeader[rowIndex];

			PdfPCell pdfCell = new PdfPCell();
			pdfCell.setUseBorderPadding(true);
			pdfCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			pdfCell.setBorderColor(BaseColor.BLACK);
			pdfCell.setBorderWidth(0.2f);
			pdfCell.setVerticalAlignment(Element.ALIGN_CENTER);
			pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfCell.setPaddingTop(2.0f);
			pdfCell.setPaddingBottom(2.0f);
			pdfCell.setPhrase(new Phrase(15f, columnHeader,
					FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
			pdfTable.addCell(pdfCell);

		}

	}

	public void addPropertiesAndValues(PdfPTable pdfTable, List<String[]> inventoryPropertyListRowData, String groupName) {

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Adding properties and their values to the pdf file...");

		for (String[] propertiesData : inventoryPropertyListRowData) {
			
			if (propertiesData[7].equalsIgnoreCase(groupName)) {

				for (int rowDataIndex = 5; rowDataIndex < propertiesData.length; rowDataIndex++) {
	
					String rowData = propertiesData[rowDataIndex];
	
					PdfPCell pdfCell = new PdfPCell();
					pdfCell.setBorderColor(BaseColor.BLACK);
					pdfCell.setVerticalAlignment(Element.ALIGN_CENTER);
					pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pdfCell.setPhrase(new Phrase(12f, ((rowData == null) ? "" : rowData.trim()),
							FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
					pdfTable.addCell(pdfCell);
	
				}
			
			}
		}

	}

	public void addTableRows(PdfPTable pdfTable, List<String[]> inventoryListRowData, String tankID) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Adding row data to the pdf file...");

		for (String[] tankData : inventoryListRowData) {

			if (tankData[0].equals(tankID)) {

				for (int rowDataIndex = 0; rowDataIndex < 5; rowDataIndex++) {

					String rowData = tankData[rowDataIndex];

					PdfPCell pdfCell = new PdfPCell();
					pdfCell.setBorderColor(BaseColor.BLACK);
					pdfCell.setVerticalAlignment(Element.ALIGN_CENTER);
					pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					pdfCell.setPhrase(new Phrase(12f, ((rowData == null) ? "" : rowData.trim()),
							FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
					pdfTable.addCell(pdfCell);

				}

				break;

			}
		}

	}

	/*
	 * Add bottom level data
	 */

	public PdfPTable addTableFooterValues() {

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Adding top level data to the pdf file...");

		PdfPTable pdfTableFooter = new PdfPTable(3);
		pdfTableFooter.setWidthPercentage(100f);

		PdfPCell pdfCellFooter = new PdfPCell();
		pdfCellFooter.setColspan(3);
		pdfCellFooter.setBorderColor(BaseColor.BLACK);
		pdfCellFooter.setBorderWidth(0.2f);
		pdfCellFooter.setVerticalAlignment(Element.ALIGN_CENTER);
		pdfCellFooter.setHorizontalAlignment(Element.ALIGN_LEFT);
		pdfCellFooter.setPhrase(new Phrase(15f, InventoryReportConstants.REMARKS,
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));

		pdfTableFooter.addCell(pdfCellFooter);
		pdfTableFooter.addCell(new Phrase(15f, InventoryReportConstants.CHECKED_BY,
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
		pdfTableFooter.addCell(new Phrase(15f, InventoryReportConstants.VERIFIED_BY,
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));

		return pdfTableFooter;

	}

}
