package de.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
/**
 * 
 * @author Michael Wurst
 *
 */
public class MyUtility {
	
	// Attributes
	/**
	 * SimpleDateFormat with Pattern "dd.MM.YYYY"
	 */
	public static SimpleDateFormat dateFormat =  new SimpleDateFormat("dd.MM.yyyy");
	
	
	/**
	 * @param String to check.
	 * @return TRUE if String is empty or null otherwise returns FALSE.
	 */
	public static boolean isNullOrEmpty(String objectString){
		boolean ok = false;
		if(objectString.isEmpty() || objectString == null){
			ok = true;
		}
		return ok;
	}
	/**
	 * @param String to check.
	 * @return TRUE if String is not empty or null otherwise returns FALSE.
	 */
	public static boolean isNotNullOrEmpty(String objectString){
		boolean ok = true;
		if(objectString.isEmpty() || objectString == null){
			ok = false;
		}
		return ok;
	}
	
	/**
	 * @param Number to round.
	 * @param Identifies how many digits to round. 
	 * @return Rounded Result.  
	 */
	public static double roundAtDecimals(double number, int deci){
		int decimal = deci * 10;
		number = number * decimal;
		Math.round(number);
		return number / decimal;		
	}
	/**
	 * Packs all Columns of the Table  
	 * @param Table
	 * @param margin  Abstand zu nächster Spalte
	 */
	public static void packColumns(JTable table, int margin) {
	    for (int i=0; i<table.getColumnCount(); i++) {
	        packColumn(table, i, margin);
	    }
	}
	/**
	 * Packs a Column of the Table
	 * @param Table
	 * @param column index to pack 
	 * @param margin  Abstand zu nächster Spalte
	 */
	public static void packColumn(JTable table, int vColIndex, int margin) {
	    DefaultTableColumnModel colModel = (DefaultTableColumnModel)table.getColumnModel();
	    TableColumn col = colModel.getColumn(vColIndex);
	    int width = 0;

	    // Get width of column header
	    TableCellRenderer renderer = col.getHeaderRenderer();
	    if (renderer == null) {
	        renderer = table.getTableHeader().getDefaultRenderer();
	    }
	    Component comp = renderer.getTableCellRendererComponent(
	        table, col.getHeaderValue(), false, false, 0, 0);
	    width = comp.getPreferredSize().width;

	    // Get maximum width of column data
	    for (int r=0; r<table.getRowCount(); r++) {
	        renderer = table.getCellRenderer(r, vColIndex);
	        comp = renderer.getTableCellRendererComponent(
	            table, table.getValueAt(r, vColIndex), false, false, r, vColIndex);
	        width = Math.max(width, comp.getPreferredSize().width);
	    }

	    // Add margin
	    width += 2*margin;

	    // Set the width
	    col.setPreferredWidth(width);
	}
	/**
	 * 
	 * @param textField which will be checked, if it's initial it will be displayed with a red border and returnCode = true 
	 * @return returnCode if textField is trimmed-empty
	 */
	public static boolean checkTextField(JTextField textField){
		boolean returnCode = false;
		Border border = new JTextField().getBorder();
		String text = textField.getText().trim();
		if(MyUtility.isNullOrEmpty(text)){
			textField.setBorder(new LineBorder(Color.RED));
			returnCode = true;
		}
		else{
			textField.setBorder(border);
			returnCode = false;
		}
		return returnCode;
	}
	/**
	 * @param txtFieldList which will be checked, if one or more Field is initial the components border will be displayed red and returnCode = true 
	 * @return returnCode = true if one or more JTextField is initial, else false
	 */
	public static boolean checkAllTextFields(List<JTextField> txtFieldList){
		boolean ok = false;
		for (JTextField temp_txtField : txtFieldList){
			if(checkTextField(temp_txtField)){
				ok = true;
			}
		}
		return ok;
	}
	/**
	 * @return currentYear indicates the the actual year
	 */
	public static int getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.YEAR); 
	}
	/**
	 * @param comp Window which will be located at the center position of the user's screen, e.g. a JDialog, JFrame, etc.
	 */
	public static void centerWindowOnTheScreen(Window window) {
//		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//		int x = d.width / 4;
//		int y = d.height / 4;
//		window.setLocation(x, y);
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - window.getWidth()) / 2;
		final int y = (screenSize.height - window.getHeight()) / 2;
		window.setLocation(x, y);

	}
	/**
	 * 
	 * @param error exception which will be formated into string
	 * @return stack trace as string
	 */
	public static String formatStackTrace(Exception error){
		final StringBuffer br = new StringBuffer();
	    StackTraceElement[] elem = error.getStackTrace();
		for(int i=0; i<elem.length;i++)
		br.append(elem[i].toString()+"\n");
		return br.toString();
	}
}
