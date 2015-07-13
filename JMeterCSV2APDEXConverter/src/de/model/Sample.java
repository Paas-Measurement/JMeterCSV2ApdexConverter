package de.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author Michael Wurst
 */
public class Sample implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(Sample.class.getName());

	private static final long serialVersionUID = 1L;

	private Date timeStamp = null;
	private long testDuration;
	private int elapsedTime;
	private String labelName;
	private String responseCode;
	private String responseMessage;
	private String threadName;
	private String dataType;
	private boolean success;
	private String bytes;
	private String grpThreads;
	private String allThreads;
	private int latency;
	private int sampleCount;
	private int errorCount;
	private int idleTime;
	private int connectionTime;

	/**
	 * timeStamp (0), elapsed (1), label (2), responseCode (3), responseMessage
	 * (4), threadName (5), dataType (6), success (7), bytes (8), grpThreads
	 * (9), allThreads (10), Latency (11), SampleCount (12), ErrorCount (13),
	 * IdleTime (14), Connect (15)
	 * 
	 * @param csvString
	 * @return Sample Object or null
	 */

	public static Sample convertJMeterCSVString2Object(String csvString) {
		if (csvString == null)
			return null;
		if (csvString == "")
			return null;
		Sample sample = new Sample();
		String[] splittedValues = csvString.split(",");
		if (splittedValues != null) {
			int arraySize = splittedValues.length;
			LOGGER.info("Total CSV-Row-Count: " + arraySize);
			// 1 - Time Stamp 
			sample.setTimeStamp(new Date(((long) Long.valueOf(splittedValues[0]))));
			// 2 - Elapsed Time
			sample.setElapsedTime(Integer.valueOf(splittedValues[1]).intValue());
			// 3 - Label 
			sample.setLabelName(splittedValues[2]);
			// 4 - Response Code
			sample.setResponseCode(splittedValues[3]);
			// 5 - Response Msg
			sample.setResponseMessage(splittedValues[4]);
			// 6 - Thread Name
			sample.setThreadName(splittedValues[5]);
			// 7 - Data Type
			sample.setDataType(splittedValues[6]);
			// 8 - Success
			sample.setSuccess(Boolean.valueOf(splittedValues[7]).booleanValue());
			// 9 - Bytes
			sample.setBytes(splittedValues[8]);
			// 10 - Thread Group
			if(arraySize > 9){
				sample.setGrpThreads(splittedValues[9]);
			}
			// 11 - All Threads
			if(arraySize > 10){
				sample.setAllThreads(splittedValues[10]);
			}
			// 12 - Latency
			if(arraySize > 11){
				sample.setLatency(Integer.valueOf(splittedValues[11]).intValue());
			}
			if(arraySize > 12){
				sample.setSampleCount(Integer.valueOf(splittedValues[12]).intValue());
			}
			if(arraySize > 13) {
				sample.setErrorCount(Integer.valueOf(splittedValues[13]).intValue());
			}
			if(arraySize > 14){
				sample.setIdleTime(Integer.valueOf(splittedValues[14]).intValue());
			}
			if(arraySize > 15){
				sample.setConnectionTime(Integer.valueOf(splittedValues[15]).intValue());
			}
		}

		return sample;
	}
	/**
	 * Label (0), Samples Count (1), Avg Latency (2), Avg Response Time (3), Geo Mean Response Time
	 * (4), Standard Deviation (5), 90% Line (6), 95% Line (7), 99% Line (8), Min
	 * (9), Max (10), Avg Bandwith (11), Avg Throughput (12), Error (13),
	 * Duration (14)
	 * 
	 * @param csvString
	 * @return Sample Object or null
	 */
	public static Sample convertBlazeMeterCSVString2Object(String csvString) {
		if (csvString == null)
			return null;
		if (csvString == "")
			return null;
		Sample sample = new Sample();
		String[] splittedValues = csvString.split(",");
		if (splittedValues != null) {
			int arraySize = splittedValues.length;
			LOGGER.info("Total CSV-Row-Count: " + arraySize);
		
			
			// 1 - Label
			sample.setLabelName(splittedValues[0]);
			// 2 - Samples Count
			sample.setSampleCount(Integer.valueOf(splittedValues[1]).intValue());
			// 3 - Latency
			sample.setLatency(convertResponseTimeStringToInteger(splittedValues[2]));
			// 4 - Response Time
			sample.setElapsedTime(convertResponseTimeStringToInteger(splittedValues[3]));
			// 5 - Geo Mean Response Time
			
			// 6 - Std Deviation
			// 7 - 90% Line
			// 8 - 95% Line
			// 9 - 99 % Line
			// 10 - Min
			// 11 - Max
			// 12 - Avg Bandwith
			sample.setBytes(splittedValues[11]);
			// 13 - Avg Trhoughtput
			// 14 - Error
			// 15 - Duration (hh:mm:ss)
		}

		return sample;
	}
	/**
	 * Formats the response time string into an Integer.
	 * @param respString
	 * @return Response time as integer
	 */
	static private int convertResponseTimeStringToInteger(String respString){
		int result = 0;
		String replacedStr = respString.replaceAll("[^-?0-9.]+", " ").trim();
		Double doubleMs = new Double(replacedStr);
		result = doubleMs.intValue();
		return result;
	}
	
	@Override
	public String toString() {
		String objectString = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		objectString = "[" + sdf.format(timeStamp) + " | " + elapsedTime
				+ " | " + labelName + " | " + responseCode + " | "
				+ responseMessage + " | " + success + " | " + bytes + latency
				+ " | " + sampleCount + " | " + errorCount + " | " + idleTime
				+ " | " + connectionTime + "]";
		return objectString;
	}

	// Getters & Setters

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public String getGrpThreads() {
		return grpThreads;
	}

	public void setGrpThreads(String grpThreads) {
		this.grpThreads = grpThreads;
	}

	public String getAllThreads() {
		return allThreads;
	}

	public void setAllThreads(String allThreads) {
		this.allThreads = allThreads;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}

	public int getConnectionTime() {
		return connectionTime;
	}

	public void setConnectionTime(int connectionTime) {
		this.connectionTime = connectionTime;
	}

	public long getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(long testDuration) {
		this.testDuration = testDuration;
	}
	
}
