package de.model;

import java.io.Serializable;

public class Bucket implements Serializable {

	private static final long serialVersionUID = 1L;
	private int bucketNumber;
	private int totalSamples;
	private double bucketSize;
	private double minValue;
	private double maxValue;
	
	public int getBucketNumber() {
		return bucketNumber;
	}
	public void setBucketNumber(int bucketNumber) {
		this.bucketNumber = bucketNumber;
	}
	public int getTotalSamples() {
		return totalSamples;
	}
	public void setTotalSamples(int totalSamples) {
		this.totalSamples = totalSamples;
	}
	public double getBucketSize() {
		return bucketSize;
	}
	public void setBucketSize(double bucketSize) {
		this.bucketSize = bucketSize;
	}
	public double getMinValue() {
		return minValue;
	}
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}
	
	@Override
	public String toString() {
		return "[" + bucketNumber + " | " + totalSamples + " | " + bucketSize
				+ " | " + minValue + " | " + maxValue + "]";
	}
}
