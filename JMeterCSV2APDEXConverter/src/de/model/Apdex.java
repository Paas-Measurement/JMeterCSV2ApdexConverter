package de.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Apdex implements Serializable{

	private static final long serialVersionUID = 1L;
	private double apdexValue;
	private int satisfiedCount;
	private int toleratedCount;
	private int frustradedCount;
	private int totalSamplesCount;
	private int toleratedBucketsCount;
	private int totalErrorCount;	
	private Map<String, SampleError> occurredErrors;
	private List<Bucket> toleratedBuckets;
	
	// Getters & Setters
	public double getApdexValue() {
		return apdexValue;
	}
	public void setApdexValue(double apdexValue) {
		this.apdexValue = apdexValue;
	}
	public int getSatisfiedCount() {
		return satisfiedCount;
	}
	public void setSatisfiedCount(int satisfiedCount) {
		this.satisfiedCount = satisfiedCount;
	}
	public int getToleratedCount() {
		return toleratedCount;
	}
	public void setToleratedCount(int toleratedCount) {
		this.toleratedCount = toleratedCount;
	}
	public int getFrustradedCount() {
		return frustradedCount;
	}
	public void setFrustradedCount(int frustradedCount) {
		this.frustradedCount = frustradedCount;
	}
	public int getTotalSamplesCount() {
		return totalSamplesCount;
	}
	public void setTotalSamplesCount(int totalSamplesCount) {
		this.totalSamplesCount = totalSamplesCount;
	}
	public int getToleratedBucketsCount() {
		return toleratedBucketsCount;
	}
	public void setToleratedBucketsCount(int toleratedBucketsCount) {
		this.toleratedBucketsCount = toleratedBucketsCount;
	}
	public List<Bucket> getToleratedBuckets() {
		return toleratedBuckets;
	}
	public void setToleratedBuckets(List<Bucket> toleratedBuckets) {
		this.toleratedBuckets = toleratedBuckets;
	}
	public int getTotalErrorCount() {
		return totalErrorCount;
	}
	public void setTotalErrorCount(int totalErrorCount) {
		this.totalErrorCount = totalErrorCount;
	}
	public Map<String, SampleError> getOccurredErrors() {
		return occurredErrors;
	}
	public void setOccurredErrors(Map<String, SampleError> occurredErrors) {
		this.occurredErrors = occurredErrors;
	}
}
