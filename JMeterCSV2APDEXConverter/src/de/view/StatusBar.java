package de.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

/**
 * Label, which will be displayed as status bar
 * 
 * @author Michael Wurst
 * 
 */

public class StatusBar extends JLabel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(StatusBar.class
			.getName());

	// Timer um Meldung nach 6 Sek. zu loeschen
	private Timer timer = new Timer(6000, this);

	// Icons
	private String BASIC_ICON_PATH = "de/icons/";
	private Icon INFO_ICON;
	private Icon ERROR_ICON;
	private Icon WARN_ICON;

	public static final String INFORMATION = "I";
	public static final String SUCCESS = "S";
	public static final String ERROR = "E";
	public static final String WARNING = "W";
	
	// Instance
	private static StatusBar instance = null;

	/**
	 * Creates a new instance of StatusBar
	 */
	private StatusBar() {
		super();
		// Basispfad der Icons setzen und die Icons erzeugen
		createIcons();
		super.setPreferredSize(new Dimension(255, 25));
		this.setOpaque(true);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}

	public static synchronized StatusBar getInstance() {
		if (instance == null) {
			instance = new StatusBar();
		}
		return instance;
	}

	/**
	 * @param message
	 *            Message which will be displayed
	 * @param statusType
	 *            "E" = Error "I" = Information "W" = Warning
	 * 
	 */
	public void setMessage(String message, String statusType) {
		this.setStatusIcon(statusType);
		this.setText(" " + message);
		if (message.trim() != "") {
			timer.restart();
		}
	}

	/**
	 * set icon depending on status flag
	 * 
	 * @param type
	 */
	private void setStatusIcon(String type) {
		if (type == null)
			return;

		type.toUpperCase();

		switch (type) {
		case ERROR:
			this.setIcon(ERROR_ICON);
			break;
		case INFORMATION:
			this.setIcon(INFO_ICON);
			break;
		case SUCCESS:
			this.setIcon(INFO_ICON);
			break;	
		case WARNING:
			this.setIcon(WARN_ICON);
			break;
		default:
			this.setIcon(null);
			break;
		}
	}

	/**
	 * delete displayed message
	 */
	public void clearStatus() {
		this.setText("");
		this.setStatusIcon("");
	}

	/**
	 * create icons used in the window
	 */
	private void createIcons() {
		try {
			ERROR_ICON = new ImageIcon(
					ClassLoader.getSystemResource(BASIC_ICON_PATH
							+ "cross-button.png"));
			INFO_ICON = new ImageIcon(
					ClassLoader.getSystemResource(BASIC_ICON_PATH
							+ "tick-button.png"));
			WARN_ICON = new ImageIcon(
					ClassLoader.getSystemResource(BASIC_ICON_PATH
							+ "exclamation-button.png"));
		} catch (NullPointerException npe) {
			logger.log(Level.WARNING,
					"Error creating icons. Reason: " + npe.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// Statusleiste leeren...
		clearStatus();
		if (evt.getSource() instanceof Timer) {
			((Timer) evt.getSource()).stop();
		}
	}
}
