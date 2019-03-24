package Gridding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/*
 * This class ------------------------------------------
 */
public class Gridder
{
	/*
	 * Loads in the visibilities data from a given csv file.
	 */
	private LinkedList<float[]> loadVisibilities()
	{
		// Where the data will be stored
		LinkedList<float[]> visibilities = new LinkedList<float[]>();
		
		// File name
		String csvFile = "Visibilites.csv";
		String line = "";
		String cvsSpilt = ",";
		
		try
		{
			// Creates buffered reader by loading file
			BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
			
			// Reads in each line of the reader
			while ((line = bufferedReader.readLine()) != null)
			{
				// Splits the line up by each comma
				String[] complexNumberAsString = line.split(cvsSpilt);
				
				// Converts the read String into a float
				float[] complexNumber = new float[2];				
				for (int i = 0; i < complexNumber.length; i++)
				{
					complexNumber[i] = Float.parseFloat(complexNumberAsString[i]);
				}
				
				// Adds the complex number to the data
				visibilities.add(complexNumber);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return visibilities;
	}
	
	public static void main(String[] args)
	{
		float[][] realGrid = new float[1024][1024];
		float[][] imaginaryGrid = new float[1024][1024];
		
		
	}
}
