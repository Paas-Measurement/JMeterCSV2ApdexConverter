package de.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import de.model.SampleError;
import de.util.MyUtility;
import de.util.SampleErrorTableModel;

public class ErrorDetailsDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String TITLE = "Occurred Error Details";
	private static final String OK_TEXT = "OK";

	// Components
	private JButton btn_ok = null;
	private JTable errorTable = null;
	
	private Collection<SampleError> collection;

	public ErrorDetailsDialog(Collection<SampleError> collection) {
		this.collection = collection;
		init();
	}

	protected void init() {
		// Button
		btn_ok = new JButton(OK_TEXT);
		btn_ok.addActionListener(this);
		btn_ok.setActionCommand(OK_TEXT);
		SampleErrorTableModel dm = new SampleErrorTableModel(collection);
		errorTable = new JTable();
//		errorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		errorTable.setModel(dm);
		errorTable.setAutoCreateRowSorter(true);

		// Main Panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JScrollPane pane = new JScrollPane(errorTable);
		mainPanel.add(pane);
		mainPanel.add(btn_ok);
		this.add(mainPanel);
		this.setModal(true);
		this.setTitle(TITLE);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(640, 240);
		MyUtility.centerWindowOnTheScreen(this);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals(OK_TEXT)) {
			dispose();
		}
	}
}
