package de.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.main.APDEXCalculator;
import de.main.CSVFileReader;
import de.model.Apdex;
import de.model.Bucket;
import de.model.Sample;
import de.model.SampleError;
import de.util.MyUtility;
/**
 * 
 * @author Michael Wurst
 *
 */
public class MainFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MainFrame.class
			.getName());

	// Strings
	private static final String TITLE = "JMeter CSV-File to APDEX-Score Converter";
	private static final String CALCULATE = "Calculate APDEX!";
	private static final String FILENAME = "Path to JMeter CSV-File: ";
	private static final String T_BUCKETS = "Count of Tolerated Buckets: ";
	private static final String T_VALUE = "Tolerated-Value (ms): ";
	private static final String F_VALUE = "Frustrated-Value (ms): ";
	private static final String TOGGLE_BLAZEMETER = "Blazemeter-File?";
	private static final String SELECT_FILE = "...";
	private static final String JMETER_CSV_FILE = "JMeter CSV-File";
	private static final String APDEX = "<html><b><h3>APDEX-Score: </h3></b></html>";
	private static final String T_COUNT = "n(Tolerated): ";
	private static final String F_COUNT = "n(Frustrated): ";
	private static final String S_COUNT = "n(Satisfied): ";
	private static final String N_COUNT = "n(Total): ";
	private static final String ERROR_COUNT = "n(Errors): ";
	private static final String TEST_DURATION = "Testdauer: ";
	private static final String ERROR_DETAILS = "Show Details...";
	private static final String FILE = "File";
	private static final String EXIT = "Exit";
	private static final String SELECT_FILE_TOOLTIP = "Select a JMeter CSV-File...";
	private static final String T_VALUE_TOOLTIP = "Select the tolerated value T...";
	private static final String F_VALUE_TOOLTIP = "Select the frustrated value F...";
	private static final String FILE_NOT_EXISTS = "Entered file doesn't exists!";
	private static final String FILE_CANT_BE_EMPTY = "Please enter a valid file path!";
	private static final String APDEX_ERROR_MSG = "Error while calculating APDEX. Check if File is not broken.";
	private static final String T_BUCKETS_CANT_BE_ZERO = "Count of Tolerated Buckets can't be 0 and must be at least 1!";
	private static final String T_VALUE_CANT_BE_LESS_THAN_F_VALUE = "Value of Tolerated can't be less than Frustrated Value!";
	private static final String EMPTY_FOLDER = "The selected folder doesnt contain appropriate files.";
	private static final String WELCOME_MSG = "Welcome to JMeter-CSV to APDEX Converter";
	private static String APDEX_CALCULATED = "Successfully calculated APDEX";
	private static String APDEX_TOOLTIP = "<html><body>The <b>APDEX</b> can be partitionied into following scala: <br><b><span style=\"background-color: #0000FF; color:white\">Excellent</span></b> Value between 0.94-1.00<br>"
			+ "<b><span style=\"background-color: #00FF00\">Good</span></b> Apdex between 0.85-0.94<br>"
			+ "<b><span style=\"background-color: #FFFF00\">Fair</span></b> Apdex between 0.70-0.85<br>"
			+ "<b><span style=\"background-color: #FF0000\">Poor</span></b> Apdex between 0.50-0.70<br>"
			+ "<b><span style=\"background-color: #555555; color:white\">Unacceptable</span></b> Apdex < 0.50<br>"
			+ "</body></html>";
	private static String T_BUCKETS_TOOLTIP = "<html><body>"
			+ "Select the number of tolerated buckets (at least 1).<br>"
			+ "This value can be used to clarify and sharpen the <b>APDEX</b> value.<br><br>"
			+ "<b>Example:</b><br><ul>"
			+ "<li>All Samples with <i>11,99s</i></li>"
			+ "<li>All Samples with <i>3,01s</i></li>"
			+ "<li><i>T=3s</i>, <i>F=12s</i></ul>"
			+ ">>> Result in both cases: Apdex = 0,80<sub>3</sub>"
			+ "<br><br><b>SOLUTION</b> - More tolerated Buckets, e.g. 2: <br><ul>"
			+ "<li><b>0.20<sub>3</sub></b> (11,99s)</li>"
			+ "<li><b>0.80<sub>3</sub></b> (3,01s)</li>" + "</ul></body></html>";

	private static final int T_DEFAULT = 750;
	private List<String> allFiles = new ArrayList<>();
	private List<Long> testDurationList = new ArrayList<>();
	private Long testDuration = null;
	
	// Components
	private final JLabel l_filename = new JLabel(FILENAME);
	private final JLabel l_apdex_t_buckets = new JLabel(T_BUCKETS);
	private final JLabel l_apdex_t_value = new JLabel(T_VALUE);
	private final JLabel l_apdex_f_value = new JLabel(F_VALUE);
	private final JLabel l_apdex_f_count = new JLabel(F_COUNT);
	private final JLabel l_apdex_t_count = new JLabel(T_COUNT);
	private final JLabel l_apdex_s_count = new JLabel(S_COUNT);
	private final JLabel l_apdex_total = new JLabel(N_COUNT);
//	private final JLabel l_blazemeter_file = new JLabel(TOGGLE_BLAZEMETER);
	private final JLabel l_test_duration = new JLabel(TEST_DURATION);
	private final JLabel l_apdex = new JLabel(APDEX);
	private final JLabel l_apdex_error_count = new JLabel(ERROR_COUNT);

	private JTextField txt_filename = null;
	private JSpinner spinner_apdex_t_value = null;
	private JSpinner spinner_apdex_f_value = null;

	private JTextField txt_s_count = null;
	private JTextField txt_t_count = null;
	private JTextField txt_f_count = null;
	private JTextField txt_total_count = null;
	private JTextField txt_apdex_value = null;
	private JTextField txt_total_errors = null;
	private JTextField txt_test_duration = null;
	
	private JCheckBox chkb_blazemeter_file = null;
	
	private StatusBar statusBar = null;
	// private JSpinner spinner_t_buckets = null;
	private JSlider slider_t_buckets = null;

	private JButton btn_calc = null;
	private JButton btn_file = null;
	private JButton btn_errorDetails = null;

	private Collection<SampleError> errorCollection;
	private String lastFileDirPath = "";
	
	
	public MainFrame() {
		init();
		createView();
		statusBar.setMessage(WELCOME_MSG, StatusBar.INFORMATION);
	}

	private void init() {
		// Spinners
		spinner_apdex_t_value = new JSpinner(new SpinnerNumberModel(T_DEFAULT,1, 1000000, 50));
		spinner_apdex_t_value.setToolTipText(T_VALUE_TOOLTIP);
		spinner_apdex_f_value = new JSpinner(new SpinnerNumberModel(4 * T_DEFAULT, 1, 1000000, 50));
		spinner_apdex_f_value.setToolTipText(F_VALUE_TOOLTIP);
		// Textfields
		txt_s_count = new JTextField();
		txt_s_count.setEditable(false);
		txt_t_count = new JTextField();
		txt_t_count.setEditable(false);
		txt_f_count = new JTextField();
		txt_f_count.setEditable(false);
		txt_apdex_value = new JTextField();		
		txt_apdex_value.setToolTipText(APDEX_TOOLTIP);
		l_apdex.setToolTipText(APDEX_TOOLTIP);
		txt_apdex_value.setEditable(false);
		txt_apdex_value.setFont(new Font(txt_apdex_value.getFont().getName(),
				Font.BOLD, 14));
		txt_total_count = new JTextField();
		txt_total_count.setEditable(false);
		txt_total_errors = new JTextField();
		txt_total_errors.setEditable(false);
		txt_test_duration = new JTextField();
		txt_test_duration.setEditable(false);
		txt_filename = new JTextField(20);
		// Sliders
		slider_t_buckets = new JSlider(0, 20, 1);
		slider_t_buckets.setMajorTickSpacing(5);
		slider_t_buckets.setMinorTickSpacing(1);
		slider_t_buckets.setPaintTicks(true);
		slider_t_buckets.setPaintLabels(true);
		slider_t_buckets.setToolTipText(T_BUCKETS_TOOLTIP);
		l_apdex_t_buckets.setToolTipText(T_BUCKETS_TOOLTIP);
		// Buttons
		btn_calc = new JButton(CALCULATE);
		btn_calc.addActionListener(this);
		btn_calc.setActionCommand(CALCULATE);
		btn_calc.setToolTipText(CALCULATE);
		btn_file = new JButton(SELECT_FILE);
		btn_file.addActionListener(this);
		btn_file.setActionCommand(SELECT_FILE);
		btn_file.setToolTipText(SELECT_FILE_TOOLTIP);
		btn_errorDetails = new JButton(ERROR_DETAILS);
		btn_errorDetails.setActionCommand(ERROR_DETAILS);
		btn_errorDetails.addActionListener(this);
		btn_errorDetails.setToolTipText(ERROR_DETAILS);
		btn_errorDetails.setEnabled(false);
		
		chkb_blazemeter_file = new JCheckBox();
		chkb_blazemeter_file.setActionCommand(TOGGLE_BLAZEMETER);
		chkb_blazemeter_file.setSelected(false);
	}

	public void createView() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set Screen Location
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		MyUtility.centerWindowOnTheScreen(this);
		this.setTitle(TITLE);
		// Set Layout
		GridLayout gridLayout = new GridLayout(0, 2, 0, 0);
		// Basics Panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel settings_panel = new JPanel(gridLayout);
		settings_panel.add(l_filename);
		JPanel filename_panel = new JPanel();
		filename_panel
				.setLayout(new BoxLayout(filename_panel, BoxLayout.X_AXIS));
		filename_panel.add(txt_filename);
		filename_panel.add(btn_file);
		settings_panel.add(filename_panel);
//		settings_panel.add(l_blazemeter_file);
//		settings_panel.add(chkb_blazemeter_file);
		settings_panel.add(l_apdex_t_value);
		settings_panel.add(spinner_apdex_t_value);
		settings_panel.add(l_apdex_f_value);
		settings_panel.add(spinner_apdex_f_value);
		settings_panel.add(l_apdex_t_buckets);
		settings_panel.add(slider_t_buckets);
		// settings_panel.add(spinner_t_buckets);

		JPanel apdex_panel = new JPanel(gridLayout);
		apdex_panel.add(l_apdex);
		apdex_panel.add(txt_apdex_value);
		apdex_panel.add(l_apdex_s_count);
		apdex_panel.add(txt_s_count);
		apdex_panel.add(l_apdex_t_count);
		apdex_panel.add(txt_t_count);
		apdex_panel.add(l_apdex_f_count);
		apdex_panel.add(txt_f_count);
		apdex_panel.add(l_apdex_total);
		apdex_panel.add(txt_total_count);
		apdex_panel.add(l_apdex_error_count);
		JPanel errorPanel = new JPanel();
		errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.X_AXIS));
		errorPanel.add(txt_total_errors);
		errorPanel.add(btn_errorDetails);
		apdex_panel.add(errorPanel);
		apdex_panel.add(l_test_duration);
		apdex_panel.add(txt_test_duration);

		mainPanel.add(settings_panel);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(btn_calc);
		mainPanel.add(Box.createVerticalStrut(30));
		mainPanel.add(apdex_panel);

		// StatusBar
		statusBar = StatusBar.getInstance();

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(statusBar, BorderLayout.SOUTH);
		// contentPane.add(settings_panel, BorderLayout.PAGE_START);
		this.add(mainPanel);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(FILE);
		menu.setMnemonic('F');
		// exit
		JMenuItem exit = new JMenuItem(EXIT);
		exit.setActionCommand(EXIT);
		exit.setMnemonic('X');
		exit.setAccelerator(KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F4, java.awt.Event.ALT_MASK));
		exit.addActionListener(this);
		menu.add(exit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		String action = evt.getActionCommand();

		switch (action) {
		case CALCULATE:
			String filename = txt_filename.getText();
			int t_value = (int) spinner_apdex_t_value.getValue();
			int f_value = (int) spinner_apdex_f_value.getValue();
			int t_buckets = (int) slider_t_buckets.getValue();
			List<Sample> sampleList = new ArrayList<>();
			boolean isBlazeMeterFile = chkb_blazemeter_file.isSelected();
			testDuration = null;
			testDurationList = new ArrayList<>();
			if (filename == null || filename.isEmpty()) {
				statusBar.setMessage(FILE_CANT_BE_EMPTY, StatusBar.ERROR);
				break;
			}

			if (t_buckets == 0) {
				statusBar.setMessage(T_BUCKETS_CANT_BE_ZERO, StatusBar.ERROR);
				break;
			}
			if(f_value <= t_value){
				statusBar.setMessage(T_VALUE_CANT_BE_LESS_THAN_F_VALUE, StatusBar.ERROR);
			}

			File file = new File(filename);
			if (!file.exists() && !file.isDirectory()) {
				statusBar.setMessage(FILE_NOT_EXISTS, StatusBar.ERROR);
			} else {
				int totalFileNo = 0;
				// Read csv file(s) 
				if (file.isDirectory()) {
					allFiles = new ArrayList<>();
					getAllCSVFilenames(file);
					totalFileNo = allFiles.size();
					LOGGER.info("TOTAL NUMBER OF FILES: " + totalFileNo);
					
					
					if (totalFileNo == 0) {
						statusBar.setMessage(EMPTY_FOLDER, StatusBar.ERROR);
					} else {
						boolean errorOccurred = false;
						
						for (String tempFilename : allFiles) {
							try {
								List<Sample> tempSampleList = CSVFileReader
										.readCSVFile2SampleList(file
												.getAbsolutePath()
												+ "/"
												+ tempFilename, isBlazeMeterFile);								
								sampleList.addAll(tempSampleList);
								testDurationList.add(getTestDuration(tempSampleList));
								
								} catch (IOException e) {
								statusBar.setMessage(e.getMessage(),
										StatusBar.ERROR);
								errorOccurred = true;
								break;
							}
						}
						if (errorOccurred)
							return;

					}

				} else {
					// single file processing...
					try {
						sampleList = CSVFileReader.readCSVFile2SampleList(filename, isBlazeMeterFile);
						testDuration = getTestDuration(sampleList);
					} catch (IOException e) {
						statusBar.setMessage(e.getMessage(), StatusBar.ERROR);
					}
				}
				
				// calculate APDEX
				APDEXCalculator apdexCalculator = new APDEXCalculator(t_value, f_value);
				apdexCalculator.setBucketCountofInterval_T_F(t_buckets);
				Apdex apdex = apdexCalculator.calculate(sampleList);
				
				// calculate Testduration
				if(testDuration == null) {
					long tempLongVal = 0;
					if(isBlazeMeterFile){
						testDuration = new Long(0);
					} else{
						for(Long temp : testDurationList){
						tempLongVal += temp.longValue();
					}
						testDuration = new Long(tempLongVal / totalFileNo);
					}
				} 
							
				if (apdex != null) {
					// Log infos
					LOGGER.info("============= APDEX Calculated =============");
					LOGGER.info("Apdex-Value = " + apdex.getApdexValue());
					LOGGER.info("n(SATISFIED) = " + apdex.getSatisfiedCount());
					LOGGER.info("n(TOLERATED) = " + apdex.getToleratedCount());
					LOGGER.info("n(FRUSTRADED) = " + apdex.getFrustradedCount());
					List<Bucket> bucketList = apdex.getToleratedBuckets();
					LOGGER.info("n(TOTAL SAMPLES = "
							+ apdex.getTotalSamplesCount());
					if (bucketList != null) {
						LOGGER.info("---------- TOLERATED BUCKET INFO -----------");
						for (Bucket temp : bucketList) {
							LOGGER.info(temp.toString());
						}
					}
					LOGGER.info("TOTAL ERRORS = " + apdex.getTotalErrorCount());
					Map<String, SampleError> error = apdex.getOccurredErrors();
					if (error != null) {
						Collection<SampleError> coll = error.values();
						for (Iterator<SampleError> iterator = coll.iterator(); iterator
								.hasNext();) {
							SampleError sampleError = (SampleError) iterator
									.next();
							LOGGER.info(sampleError.toString());
						}
					}
					LOGGER.info("============================================");

					// show results
					txt_apdex_value.setText(String.valueOf(apdex
							.getApdexValue()));
					setBackgroundColorAPDEX(apdex.getApdexValue());
					txt_s_count.setText(String.valueOf(apdex
							.getSatisfiedCount()));
					txt_t_count.setText(String.valueOf(apdex
							.getToleratedCount()));
					txt_f_count.setText(String.valueOf(apdex
							.getFrustradedCount()));
					txt_total_count.setText(String.valueOf(apdex
							.getTotalSamplesCount()));
					txt_total_errors.setText(String.valueOf(apdex
							.getTotalErrorCount()));
					txt_test_duration.setText(testDuration.toString() + "s");
					
					this.errorCollection = apdex.getOccurredErrors().values();
					if (apdex.getTotalErrorCount() > 0) {
						btn_errorDetails.setEnabled(true);
					} else {
						btn_errorDetails.setEnabled(false);
					}

					statusBar.setMessage(APDEX_CALCULATED,
							StatusBar.INFORMATION);
				} else {
					statusBar.setMessage(APDEX_ERROR_MSG, StatusBar.ERROR);
				}
			}
			break;
		case SELECT_FILE:
			JFileChooser chooser = new JFileChooser(lastFileDirPath);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			FileFilter filter = new FileNameExtensionFilter(JMETER_CSV_FILE,
					"csv");
			chooser.setFileFilter(filter);

			int i = chooser.showOpenDialog(null);
			if (i == JFileChooser.APPROVE_OPTION) {
				String filepath = chooser.getSelectedFile().getAbsolutePath();
				txt_filename.setText(filepath);
				lastFileDirPath = chooser.getSelectedFile().getPath();
			}
			break;
		case ERROR_DETAILS:
			new ErrorDetailsDialog(errorCollection);
			break;
		case EXIT:
			System.exit(0);
			break;
		default:
			break;
		}

	}

	private void setBackgroundColorAPDEX(double apdexValue) {
		if (apdexValue >= 0.94) {
			// excellent
			txt_apdex_value.setBackground(Color.BLUE);
			txt_apdex_value.setForeground(Color.WHITE);
		} else if (apdexValue >= 0.85) {
			// good
			txt_apdex_value.setBackground(Color.GREEN);
			txt_apdex_value.setForeground(Color.BLACK);
		} else if (apdexValue >= 0.70) {
			// fair
			txt_apdex_value.setBackground(Color.YELLOW);
			txt_apdex_value.setForeground(Color.BLACK);
		} else if (apdexValue >= 0.50) {
			// poor
			txt_apdex_value.setBackground(Color.RED);
			txt_apdex_value.setForeground(Color.BLACK);
		} else {
			// unacceptable
			txt_apdex_value.setBackground(Color.GRAY);
			txt_apdex_value.setForeground(Color.WHITE);
		}
	}
	
	private Long getTestDuration(List<Sample> tempList){
		Long duration = null;
		// Calculate Test Duration with 1st and Last list element
		if(tempList!= null){
			if(!tempList.isEmpty()){
				Sample tempSamp = tempList.get(0);
				if(tempSamp != null){
				Date testStart = tempSamp.getTimeStamp();
				Date testEnd = null;
				if(tempList.size()>1){
					testEnd = tempList.get(tempList.size()-1).getTimeStamp();
				} else {
					testEnd = testStart;					
				}
				
				duration = new Long(getDateDiff(testStart, testEnd, TimeUnit.SECONDS));
			}}
		}
		return duration;
	}

	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	private void getAllCSVFilenames(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				getAllCSVFilenames(fileEntry);
			} else {
				String filename = fileEntry.getName();
				if (filename.endsWith(".csv") || filename.endsWith(".CSV")) {
					allFiles.add(fileEntry.getName());
				}
			}
		}
	}
}
