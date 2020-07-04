package com.rainiersoft.tankgauge.reportsgenerator.interfaces;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rainiersoft.tankgauge.reportsgenerator.core.GenericHeaderGeneratorManager;
import com.rainiersoft.tankgauge.reportsgenerator.core.HeaderManagement;
import com.rainiersoft.tankgauge.reportsgenerator.definitions.ReportConstants;
import com.rainiersoft.tankgauge.reportsgenerator.main.TankGaugingReportsGenerator;

public abstract class GenericPDFReportGenerator {

	public DateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	public Date date = new Date();

	// Common method to add header in the PDF report
	public Document printPDFReportHeader(Properties repConfigProperties, String dirPath, String fileName,
			GenericHeaderGeneratorManager genericHeaderGenMgr, Document pdfDocument, PdfWriter pdfWriter,
			String computerID) {

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Initialized generating generic pdf report header...");
		HeaderManagement headerMgmt = new HeaderManagement();

		// Retrieve all the header values
		// Map<String, String> headerValues = (HashMap<String, String>)
		// genericHeaderGenMgr.getHeaders();

		try {

			TankGaugingReportsGenerator.reportsLogger
					.debug(getClass().getName() + " : Creating a new pdf document for writing...");

			// Get page layout configuration based on report type
			if (TankGaugingReportsGenerator.reportPageOrientation.equalsIgnoreCase(ReportConstants.PAGE_LAYOUT_PORTRAIT)) {

				pdfDocument = new Document(PageSize.A4);

			} else if (TankGaugingReportsGenerator.reportPageOrientation.equalsIgnoreCase(ReportConstants.PAGE_LAYOUT_LANDSCAPE)) {

				pdfDocument = new Document(PageSize.A4.rotate());

			}

			pdfWriter = PdfWriter.getInstance(pdfDocument,
					new FileOutputStream(repConfigProperties.getProperty(dirPath) + fileName));

			pdfWriter.setPageEvent(headerMgmt);
			pdfDocument.open();

			// Generate top level data in the pdf document
			// pdfDocument.add(this.generateTopLevelHeaderData(headerValues,
			// repConfigProperties, computerID));

		} catch (Exception excp) {

			TankGaugingReportsGenerator.reportsLogger.debug(getClass().getName() + " : Exception - " + excp.getMessage());

		}

		TankGaugingReportsGenerator.reportsLogger
				.debug(getClass().getName() + " : Returning the pdf file for adding table data...");

		return pdfDocument;

	}

	/*
	 * Generate top level data This method is deprecated because Header Manager
	 * class is handling this part
	 */

	@Deprecated
	public PdfPTable generateTopLevelHeaderData(Map<String, String> headerValues, Properties repConfigProperties,
			String computerID) {

		PdfPTable pdfTop = new PdfPTable(1);
		pdfTop.setWidthPercentage(30f);
		pdfTop.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell pdfCell = new PdfPCell();
		pdfCell.setColspan(3);
		pdfCell.setVerticalAlignment(Element.ALIGN_CENTER);
		pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);

		pdfTop.addCell(pdfCell);
		pdfTop.addCell(new Phrase(15f,
				headerValues.get(ReportConstants.START_DATE_TIME) + "\b\b\b\b"
						+ repConfigProperties.getProperty(ReportConstants.START_DATE_TIME),
				FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
		pdfTop.addCell(new Phrase(15f,
				headerValues.get(ReportConstants.END_DATE_TIME) + "\b\b\b\b\b\b\b\b"
						+ repConfigProperties.getProperty(ReportConstants.END_DATE_TIME),
				FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
		pdfTop.addCell(
				new Phrase(15f, headerValues.get(ReportConstants.PRINT_DATE_TIME) + "\b\b\b\b\b" + sdf.format(date),
						FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));

		if (computerID != null) {

			pdfTop.addCell(new Phrase(15f, headerValues.get(ReportConstants.COMPUTER_ID) + " " + computerID,
					FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));

		}

		return pdfTop;

	}

	public abstract Document generatePDFReport(Document pdfDocument, ResultSet sqlResultSet,
			List<String[]> reportListRowData, Properties repConfigProperties);

}
