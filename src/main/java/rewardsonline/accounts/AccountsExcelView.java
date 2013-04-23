package rewardsonline.accounts;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import common.money.MonetaryAmount;

/**
 * This view generates an Excel report from Account objects.
 */
public class AccountsExcelView extends AbstractExcelView {

	public final HSSFCellStyle NO_STYLE = null;
	protected final Logger logger = Logger.getLogger(AccountsExcelView.class);
	
	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Customer cust = (Customer) model.get("customer");
		List<Account> accounts = null;
		logger.setLevel(Level.INFO);

		if (cust == null) {
			accounts = (List<Account>) model.get("accountList");
			cust = accounts.get(0).getOwner();
		} else {
			accounts = cust.getAccounts();
		}

		HSSFSheet sheet = workbook.createSheet();

		if (cust == null || accounts == null) {
			HSSFRow row = sheet.createRow(0);
			addToCell(row, 0, "Error creating spreadsheet", NO_STYLE);
			return;
		}

		// Columns - width is measured in 256ths of an el
		short unit = (short) 1300; // = 1cm
		sheet.setColumnWidth((short) 0, (short) (unit * 3.9));
		sheet.setColumnWidth((short) 1, (short) (unit * 2.8));
		sheet.setColumnWidth((short) 2, (short) (unit * 3.7));
		sheet.setColumnWidth((short) 3, (short) (unit * 3.6));
		sheet.setColumnWidth((short) 4, (short) (unit * 2.4));
		sheet.setColumnWidth((short) 5, (short) (unit * 4.0));

		// Heading style
		HSSFCellStyle heading = workbook.createCellStyle();
		heading.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		heading.setBottomBorderColor(HSSFColor.BLACK.index);
		heading.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		heading.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
		heading.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		heading.setFont(font);

		// Other styles
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		HSSFCellStyle moneyStyle = workbook.createCellStyle();
		moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));

		// Headings
		HSSFRow row = sheet.createRow(0);
		addToCell(row, 0, "Customer Name", heading);
		addToCell(row, 1, "Date of Birth", heading);
		addToCell(row, 2, "Account Number", heading);
		addToCell(row, 3, "Account Type", heading);
		addToCell(row, 4, "Balance", heading);
		addToCell(row, 5, "Credit Card", heading);

		int nAccounts = accounts.size();

		// Content
		for (short i = 0; i < nAccounts; i++) {
			Account account = accounts.get(i);
			row = sheet.createRow(i + 1);
			addToCell(row, 0, cust.getName(), NO_STYLE);
			addToCell(row, 1, cust.getDateOfBirth(), dateStyle);
			addToCell(row, 2, account.getNumber(), NO_STYLE);
			addToCell(row, 3, account.getType(), NO_STYLE);
			addToCell(row, 4, account.getBalance(), moneyStyle);
			String cc = account.getCreditCardNumber();
			addToCell(row, 5, cc == null ? "N/A" : cc, NO_STYLE);
		}

		// Totals
		row = sheet.createRow(nAccounts + 2);
		addToCell(row, 3, "TOTAL", NO_STYLE);
		addToCell(row, 4, "=sum(E2:E" + (nAccounts+1) + ')', moneyStyle);
	}

	private HSSFCell addToCell(HSSFRow row, int index, Object value,
			HSSFCellStyle style) {
		HSSFCell cell = row.createCell((short) index);

		if (style == null)
			style = cell.getCellStyle();

		if (value instanceof String) {
			String str = (String) value;
			logger.info("STRING: [" + str + ']');
			if (str.startsWith("="))
				cell.setCellFormula(str.substring(1));
			else
				cell.setCellValue(new HSSFRichTextString(str));

			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		} else if (value instanceof Date) {
			logger.info("DATE:  " + value);
			cell.setCellValue((Date) value);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		} else if (value instanceof MonetaryAmount) {
			logger.info("MONEY: " + value);
			cell.setCellValue(((MonetaryAmount) value).asDouble());
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		} else {
			if ( value == null) value = "";   // Ignore
			logger.info("OTHER: " + value + " (" + value.getClass() + ")");
			cell.setCellValue(new HSSFRichTextString(value.toString()));
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		}

		cell.setCellStyle(style);
		logger.info(" (" + style + ")");
		return cell;
	}

}
