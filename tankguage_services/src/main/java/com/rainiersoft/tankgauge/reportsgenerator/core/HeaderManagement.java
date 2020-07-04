package com.rainiersoft.tankgauge.reportsgenerator.core;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rainiersoft.tankgauge.reportsgenerator.definitions.ReportConstants;
import com.rainiersoft.tankgauge.reportsgenerator.main.TankGaugingReportsGenerator;
import com.rainiersoft.tankgauge.reportsgenerator.definitions.InventoryReportConstants;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;

public class HeaderManagement extends PdfPageEventHelper {

	DateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	PdfContentByte contentByte = null;
	PdfTemplate footerTemplate = null;
	BaseFont baseFont = null;
	
	public void onStartPage(PdfWriter pdfWriter, Document pdfDocument) {

		try {

			// Add header and sub-header on top of the PDF file
			PdfContentByte contentbytes = pdfWriter.getDirectContent();

			ColumnText.showTextAligned(contentbytes, Element.ALIGN_CENTER,
					new Phrase(ReportConstants.REPORT_HEADER,
							new Font(FontFactory.getFont(ReportConstants.REPORT_HEADER_FONT, 12.5f, Font.BOLD))),
					(pdfDocument.right() - pdfDocument.left()) / 2 + pdfDocument.leftMargin(), pdfDocument.top() + 10,
					0);

			// Check whether the PDF document is set as PORTRAIT or LANDSCAPE
			if (TankGaugingReportsGenerator.reportPageOrientation.equals(ReportConstants.PAGE_LAYOUT_PORTRAIT)) {
				
				ColumnText.showTextAligned(contentbytes, Element.ALIGN_CENTER,
						new Phrase(TankGaugingReportsGenerator.reportSubHeader,
								new Font(FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 8f, Font.BOLD))),
						297.5f, 800f, 0f);
				
			} else {
				
				ColumnText.showTextAligned(contentbytes, Element.ALIGN_CENTER,
						new Phrase(TankGaugingReportsGenerator.reportSubHeader,
								new Font(FontFactory.getFont(InventoryReportConstants.REPORT_SUB_HEADER_FONT, 8f, Font.BOLD))),
						(pdfDocument.right() - pdfDocument.left()) / 2 + pdfDocument.leftMargin(), pdfDocument.top() - 10,
						0);
				
			}

			// Add image on top of the PDF file
			Image imageIcon = Image.getInstance("images/Endress And Hauser.PNG");
			
			// Check whether the PDF document is set as PORTRAIT or LANDSCAPE
			if (TankGaugingReportsGenerator.reportPageOrientation.equals(ReportConstants.PAGE_LAYOUT_PORTRAIT))
				imageIcon.setAbsolutePosition(10, PageSize.A4.getHeight() + 20f - imageIcon.getScaledHeight());
			else
				imageIcon.setAbsolutePosition(10, PageSize.A4.rotate().getHeight() + 20f - imageIcon.getScaledHeight());
			
			imageIcon.scaleToFit(70, 80);
			pdfDocument.add(imageIcon);

			pdfDocument.add(new Paragraph(Chunk.NEWLINE));

			PdfPTable pdfTop = new PdfPTable(1);
			
			if (TankGaugingReportsGenerator.reportPageOrientation.equals(ReportConstants.PAGE_LAYOUT_PORTRAIT))
				pdfTop.setWidthPercentage(30f);
			else
				pdfTop.setWidthPercentage(20f);
			
			pdfTop.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell pdfCell = new PdfPCell();
			pdfCell.setColspan(3);
			pdfCell.setVerticalAlignment(Element.ALIGN_CENTER);
			pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			// Add start, end and print date below sub-header in the PDF file
//			pdfTop.addCell(pdfCell);
//			pdfTop.addCell(new Phrase(15f,
//			ReportConstants.START_DATE_TIME + "\b\b\b\b" + TankGaugingReportsGenerator.reformattedStartDateAndTime,
//			FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
//			pdfTop.addCell(new Phrase(15f,
//			ReportConstants.END_DATE_TIME + "\b\b\b\b\b\b\b\b" + TankGaugingReportsGenerator.reformattedEndDateAndTime,
//			FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));

//			pdfTop.addCell(new Phrase(15f,
//					ReportConstants.START_DATE_TIME + "\b\b\b\b"
//							+ ReportsGenerator.repConfigProperties.getProperty(ReportConstants.START_DATE_TIME),
//					FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
//			pdfTop.addCell(new Phrase(15f,
//					ReportConstants.END_DATE_TIME + "\b\b\b\b\b\b\b\b"
//							+ ReportsGenerator.repConfigProperties.getProperty(ReportConstants.END_DATE_TIME),
//					FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
//			pdfTop.addCell(new Phrase(15f, ReportConstants.PRINT_DATE_TIME + "\b\b\b\b\b" + sdf.format(new Date()),
//					FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));

			// Equipment ID field is required for specific set of reports
			// String computerID = getComputerID(TankGaugingReportsGenerator.reportType);
			
			// if (computerID != null)
			//	computerID = TankGaugingReportsGenerator.repConfigProperties.getProperty(computerID);
			
			// Check whether Equipment ID field to be displayed in the report based on report type
//			if (reportTypesShowingComputerID.contains(TankGaugingReportsGenerator.reportType)) {
//
//				// Check whether the PDF document is set as PORTRAIT or LANDSCAPE
//				if (TankGaugingReportsGenerator.reportPageOrientation.equals(ReportConstants.PAGE_LAYOUT_PORTRAIT))
//					pdfTop.addCell(new Phrase(15f, ReportConstants.COMPUTER_ID + "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b" + computerID,
//						FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
//				else
//					pdfTop.addCell(new Phrase(15f, ReportConstants.COMPUTER_ID + "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b" + computerID,
//							FontFactory.getFont(ReportConstants.REPORT_SUB_HEADER_FONT, 6f, Font.NORMAL)));
//				
//			}

			pdfDocument.add(pdfTop);

			pdfDocument.add(new Paragraph(Chunk.NEWLINE));

		} catch (Exception excp) {

			TankGaugingReportsGenerator.reportsLogger
					.debug(getClass().getName() + " : Exception in header management - " + excp.getMessage());

		}

	}

	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {

		try {

			baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			contentByte = writer.getDirectContent();
			footerTemplate = contentByte.createTemplate(50f, 50f);

		} catch (DocumentException docEx) {

			TankGaugingReportsGenerator.reportsLogger
					.debug(getClass().getName() + " : Exception in opening the document - " + docEx.getMessage());

		}

		catch (IOException docEx) {

			TankGaugingReportsGenerator.reportsLogger
					.debug(getClass().getName() + " : IO Exception in opening document - " + docEx.getMessage());

		}
	}

	@Override
	public void onEndPage(PdfWriter pdfWriter, Document pdfDocument) {

		// Setting format of the page number to display
		String pageNumber = "Page " + pdfWriter.getPageNumber() + " of ";

		contentByte.beginText();
		contentByte.setFontAndSize(baseFont, 6f);
		contentByte.setTextMatrix(pdfDocument.getPageSize().getRight(75f), pdfDocument.getPageSize().getBottom(20f));
		contentByte.showText(pageNumber);
		contentByte.endText();

		float len = baseFont.getWidthPoint(pageNumber, 6f);
		contentByte.addTemplate(footerTemplate, pdfDocument.getPageSize().getRight(75f) + len,
				pdfDocument.getPageSize().getBottom(20f));

	}

	@Override
	public void onCloseDocument(PdfWriter pdfWriter, Document document) {

		footerTemplate.beginText();
		footerTemplate.setFontAndSize(baseFont, 6f);
		footerTemplate.setTextMatrix(0, 0);
		footerTemplate.showText("" + (pdfWriter.getPageNumber()));
		footerTemplate.endText();

	}
	
	/*
	 * Method to return report type specific Equipment ID property field
	 */
	
	public String getComputerID(String reportType) {
		
		String computerID = null;
		
		/*switch (reportType) {

			case InventoryReportConstants.INVENTORY_REPORT_TYPE_INPUT_PARAMETER: {

				computerID = null;
				break;

			}

		}*/
		if (reportType.equalsIgnoreCase(InventoryReportConstants.INVENTORY_REPORT_TYPE_INPUT_PARAMETER)) 
		{

			computerID = null;
		}

		
		return computerID;
		
	}
	
}