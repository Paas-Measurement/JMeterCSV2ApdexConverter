package de.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.model.Sample;
/**
 * Helper Class to Read the JMeter CSV-Files into Memory.
 * @author Michael Wurst
 *
 */
public class CSVFileReader {

	@SuppressWarnings("resource")
	public static List<Sample> readCSVFile2SampleList(String filename, boolean isBlazeMeterFile)
			throws IOException {
		BufferedReader bufferedFileReader = new BufferedReader(new FileReader(
				new File(filename)));

		String line = "";
		int i = 0;
		List<Sample> sampleList = new ArrayList<>();
		while ((line = bufferedFileReader.readLine()) != null) {
			i++;
			if( i == 0){
				//continue;
				
			}
			if (i > 0) {
				Sample samp = null;
				if(isBlazeMeterFile){
					if(i <= 1){					
						continue;
					}	
						samp = Sample.convertBlazeMeterCSVString2Object(line);
				} else {
					samp = Sample.convertJMeterCSVString2Object(line);
				}
				sampleList.add(samp);
			}			
		}
		
		return sampleList;
	}

}
