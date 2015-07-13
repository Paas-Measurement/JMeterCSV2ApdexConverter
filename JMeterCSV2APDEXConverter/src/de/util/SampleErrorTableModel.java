package de.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.model.SampleError;

/**
 * @author Michael Wurst
 */

public class SampleErrorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	// Table Text
	public static String[] columnNames = { "Code", "Message", "Error Count" };
	private List<SampleError> list = null;

	public SampleErrorTableModel(List<SampleError> list) {
		this.list = list;
	}
	
	public SampleErrorTableModel (Collection<SampleError> collection) {
		list = new ArrayList<>();
		for (Iterator<SampleError> iterator = collection.iterator(); iterator.hasNext();) {
			SampleError sampleError = (SampleError) iterator.next();
			list.add(sampleError);
		}
		
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return list.get(row).getErrorCode();
		case 1:
			return list.get(row).getErrorMsg();
		case 2:
			return list.get(row).getErrorCount();
		default:
			return null;
		}
	}
}
