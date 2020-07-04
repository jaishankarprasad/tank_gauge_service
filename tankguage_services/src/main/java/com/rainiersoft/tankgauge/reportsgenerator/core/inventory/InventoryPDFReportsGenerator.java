package com.rainiersoft.tankgauge.reportsgenerator.core.inventory;

import com.rainiersoft.tankgauge.reportsgenerator.definitions.InventoryReportConstants;
import com.rainiersoft.tankgauge.reportsgenerator.main.TankGaugingReportsGenerator;
import com.rainiersoft.tankgauge.reportsgenerator.util.RowsToColumnsConverter;
import com.rainiersoft.tankgauge.reportsgenerator.interfaces.GenericPDFReportGenerator;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
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

public class InventoryPDFReportsGenerator extends GenericPDFReportGenerator {

	PdfPTable pdfTable = null;

	String[] inventoryPropertiesList = null;
	String[] groupNames = null;
	
	// Initiating object to convert inventory rows to columns for pdf generation
	RowsToColumnsConverter rowsToColumnsConverter = null;

	@Override
	public Document generatePDFReport(Document pdfDocument, ResultSet sqlResultSet, List<String[]> inventoryListRowData,
			Properties repConfigProperties) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Generating inventory pdf report...");

		// Defining constant row header values required in the PDF file
		groupNames = TankGaugingReportsGenerator.repConfigProperties
				.getProperty(InventoryReportConstants.INVENTORY_GROUP_NAMES)
				.split(InventoryReportConstants.INVENTORY_GROUP_NAMES_DELIMITER);

		// List of properties
		inventoryPropertiesList = TankGaugingReportsGenerator.repConfigProperties
				.getProperty(InventoryReportConstants.INVENTORY_PROPERTIES_LIST)
				.split(InventoryReportConstants.INVENTORY_GROUP_NAMES_DELIMITER);

		try {

			// Configuring table header to repeat in every page
			// pdfTankDetails.setHeaderRows(2);

			List<String[]> processedInventoryData = this.processInventoryData(inventoryListRowData);

			pdfTable = new PdfPTable(inventoryPropertiesList.length + 1);
			// pdfTable.setWidths(new float[] { 3, 2, 2, 2 });
			pdfTable.setTotalWidth(1000f);
			pdfTable.setWidthPercentage(100f);

			// Configuring table header to repeat in every page
			pdfTable.setHeaderRows(2);

			this.addTableHeader(pdfTable, groupNames);
			this.addTableSubHeader(pdfTable, inventoryPropertiesList);
			this.addTableRows(pdfTable, processedInventoryData);

			pdfDocument.add(pdfTable);

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

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Retrieved inventory table header...");

		return pdfTableHeader;

	}

	public void addTableHeader(PdfPTable pdfTable, String[] pdfTableHeader) {

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
		pdfCellHeader.setPhrase(new Phrase(15f, "Tank Name",
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
		pdfCellHeader.setRowspan(2);
		pdfTable.addCell(pdfCellHeader);

		pdfCellHeader = new PdfPCell();
		pdfCellHeader.setUseBorderPadding(true);
		pdfCellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfCellHeader.setBorderColor(BaseColor.BLACK);
		pdfCellHeader.setBorderWidth(0.2f);
		pdfCellHeader.setVerticalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setPaddingTop(2.0f);
		pdfCellHeader.setPaddingBottom(2.0f);
		pdfCellHeader.setPhrase(new Phrase(15f, groupNames[0],
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
		pdfCellHeader.setColspan(4);
		pdfTable.addCell(pdfCellHeader);

		pdfCellHeader = new PdfPCell();
		pdfCellHeader.setUseBorderPadding(true);
		pdfCellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfCellHeader.setBorderColor(BaseColor.BLACK);
		pdfCellHeader.setBorderWidth(0.2f);
		pdfCellHeader.setVerticalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setPaddingTop(2.0f);
		pdfCellHeader.setPaddingBottom(2.0f);
		pdfCellHeader.setPhrase(new Phrase(15f, groupNames[1],
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
		pdfCellHeader.setColspan(7);
		pdfTable.addCell(pdfCellHeader);

		pdfCellHeader = new PdfPCell();
		pdfCellHeader.setUseBorderPadding(true);
		pdfCellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		pdfCellHeader.setBorderColor(BaseColor.BLACK);
		pdfCellHeader.setBorderWidth(0.2f);
		pdfCellHeader.setVerticalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
		pdfCellHeader.setPaddingTop(2.0f);
		pdfCellHeader.setPaddingBottom(2.0f);
		pdfCellHeader.setPhrase(new Phrase(15f, groupNames[2],
				FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
		pdfCellHeader.setColspan(2);
		pdfTable.addCell(pdfCellHeader);

	}

	public void addTableSubHeader(PdfPTable pdfTable, String[] pdfTableHeader) {

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Adding sub-header to the pdf file...");
		
		// Adding headers based on aliases added in the sql query
		for (String columnHeader : pdfTableHeader) {

			PdfPCell pdfCell = new PdfPCell();
			pdfCell.setUseBorderPadding(true);
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

	private List<String[]> processInventoryData(List<String[]> inventoryListRowData) {

		List<String> tankNames = new ArrayList<String>();
		rowsToColumnsConverter = new RowsToColumnsConverter();

		// First retrieve & store all unique tank names
		for (String[] tankData : inventoryListRowData) {

			for (int rowDataIndex = 0; rowDataIndex < inventoryListRowData.size(); rowDataIndex++) {

				if (!tankNames.contains(tankData[0])) {

					tankNames.add(tankData[0]);
					break;

				}

			}

		}
		
		return rowsToColumnsConverter.convertRowsToColumns(tankNames, inventoryListRowData);

	}
	

	public void addTableRows(PdfPTable pdfTable, List<String[]> inventoryListRowData) {

		TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Adding row data to the pdf file...");

		for (String[] tankData : inventoryListRowData) {

			for (int rowDataIndex = 0; rowDataIndex < tankData.length; rowDataIndex++) {

				String rowData = tankData[rowDataIndex];

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
