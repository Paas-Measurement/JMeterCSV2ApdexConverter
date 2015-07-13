package de.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.model.Apdex;
import de.model.Bucket;
import de.model.Sample;
import de.model.SampleError;

/**
 * Calculates the Apdex Value according to the formula:
 * APDEX = (SATISFIED + 0,5 * TOLERATED) / TOTAL SAMPLES
 * 
 * It also provides an option to divide the tolerated-frustrated 
 * interval into more buckets as improvement to gain a more granular value.
 * @author Michael Wurst
 */
public class APDEXCalculator {

	private int frustratedValueF;
	private int toleratedValueT;
	private int bucketCountofInterval_T_F = 1;

	public APDEXCalculator() {
	}

	public APDEXCalculator(int toleratedValueT) {
		this.toleratedValueT = toleratedValueT;
		frustratedValueF = 4 * toleratedValueT;
	}

	public APDEXCalculator(int toleratedValueT, int frustradedValueF) {
		this.toleratedValueT = toleratedValueT;
		this.frustratedValueF = frustradedValueF;
	}

	/**
	 * Calculates the Apdex according to the specified settings and returns
	 * the results in an new object.
	 * @param sampleList
	 * @return Apdex Object
	 */
	public Apdex calculate(List<Sample> sampleList) {
		Apdex apdex = new Apdex();
		int totalSamplesCount = sampleList.size();
		int satisfiedCount = 0;
		int toleratedCount = 0;
		int frustradedCount = 0;
		int totalErrorCount = 0;
		Map<String, SampleError> errorMap = new HashMap<>();
		
		if (bucketCountofInterval_T_F == 1) {
			for (Sample temp : sampleList) {
				if(temp.getErrorCount() == 1){
					totalErrorCount++;
					SampleError error = new SampleError();
					if(errorMap.containsKey(temp.getResponseCode())){
						error = errorMap.get(temp.getResponseCode());
						int count = error.getErrorCount() + 1;
						error.setErrorCount(count);	
						errorMap.put(temp.getResponseCode(), error);
					} else {
						error.setErrorCode(temp.getResponseCode());
						error.setErrorMsg(temp.getResponseMessage());
						error.setErrorCount(temp.getErrorCount());
						errorMap.put(temp.getResponseCode(), error);
					}
				}
				
				int elapsedTime = temp.getElapsedTime();
				if (elapsedTime < toleratedValueT) {
					satisfiedCount++;
				} else {
					if (elapsedTime < frustratedValueF) {
						toleratedCount++;
					} else {
						frustradedCount++;
					}
				}
			}
			// calculate APDEX value
			double roundApdex = (double)(satisfiedCount + toleratedCount / 2)	/ totalSamplesCount;
			apdex.setApdexValue((double)Math.round(roundApdex * 10000) /10000);
		} else {
			int[] tolBuckets = new int[bucketCountofInterval_T_F];
			int intervallT_F = frustratedValueF - toleratedValueT;
			double bucketSize = intervallT_F / bucketCountofInterval_T_F;
			
			for (Sample temp : sampleList) {
				int elapsedTime = temp.getElapsedTime();
				
				if(temp.getErrorCount() == 1){
					totalErrorCount++;
					SampleError error = new SampleError();
					if(errorMap.containsKey(temp.getResponseCode())){
						error = errorMap.get(temp.getResponseCode());
						int count = error.getErrorCount() + 1;
						error.setErrorCount(count);			
						errorMap.put(temp.getResponseCode(), error);
					} else {
						error.setErrorCode(temp.getResponseCode());
						error.setErrorMsg(temp.getResponseMessage());
						error.setErrorCount(temp.getErrorCount());
						errorMap.put(temp.getResponseCode(), error);
					}
				}
				
				if (elapsedTime < toleratedValueT) {
					satisfiedCount++;
				} else {
					if (elapsedTime < frustratedValueF) {
						toleratedCount++;
						// Tolerated Buckets with sample counts
						double i = bucketSize;
						int bucketNumber = 0;
						// find the right bucket
						while(i <= intervallT_F && elapsedTime < frustratedValueF){
							if(elapsedTime < (toleratedValueT+i)){
								if(bucketNumber < bucketCountofInterval_T_F){
								  tolBuckets[bucketNumber] = tolBuckets[bucketNumber] + 1;
								  break;	
								} else {
									// if you've reached the upper limit, add to last possible bucket
									tolBuckets[bucketCountofInterval_T_F-1] += 1;
									break;
								}
							}							
							i = i + bucketSize;
							bucketNumber++;
						}
					} else {
						frustradedCount++;
					}
				}
			}
			double toleratedValue = 0;
			int weight = 0;
			List<Bucket> bucketList = new ArrayList<>();
			double minValue = toleratedValueT;
			double maxValue = 0;
			// bucketCountofInterval_T_F + 1, because 'Satisfied bucket' is has a the most weight with 0 
			for (int i = 0; i < tolBuckets.length; i++) {				
				maxValue = minValue + bucketSize;
				Bucket bucket = new Bucket();
				bucket.setBucketNumber(i+1);
				bucket.setTotalSamples(tolBuckets[i]);
				bucket.setBucketSize(bucketSize);
				bucket.setMinValue(minValue);
				bucket.setMaxValue(maxValue);
				bucketList.add(bucket);
				toleratedValue = toleratedValue
						+ ((tolBuckets.length - weight) * tolBuckets[i])
						/ (bucketCountofInterval_T_F + 1);
				weight++;
				minValue = maxValue;
			}
			apdex.setToleratedBucketsCount(bucketCountofInterval_T_F);
			double roundApdex = (double)(satisfiedCount + toleratedValue)/totalSamplesCount;
			apdex.setApdexValue((double)Math.round(roundApdex * 10000) /10000);
			apdex.setToleratedBuckets(bucketList);
		}
		apdex.setFrustradedCount(frustradedCount);
		apdex.setSatisfiedCount(satisfiedCount);
		apdex.setToleratedCount(toleratedCount);
		apdex.setTotalSamplesCount(totalSamplesCount);
		apdex.setTotalErrorCount(totalErrorCount);
		apdex.setOccurredErrors(errorMap);
		
		return apdex;
	}

	public int getBucketCountofInterval_T_F() {
		return bucketCountofInterval_T_F;
	}

	public void setBucketCountofInterval_T_F(int bucketCountofInterval_T_F) {
		this.bucketCountofInterval_T_F = bucketCountofInterval_T_F;
	}

	public int getFrustradedValueF() {
		return frustratedValueF;
	}

	public void setFrustradedValueF(int frustradedValueF) {
		this.frustratedValueF = frustradedValueF;
	}

	public int getToleratedValueT() {
		return toleratedValueT;
	}

	public void setToleratedValueT(int toleratedValueT) {
		this.toleratedValueT = toleratedValueT;
	}
}
