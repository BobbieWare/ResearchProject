/*
 * This class is used to load the grid that will be deconvoluted
 * It can be configured by changing the csvFile variable to 
 * the image that will be processed.
 * 
 * @author Bobbie Ware
 */
package Deconvolution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadGrid
{
	public static Complex[][] loadGrid()
	{
		
		Complex[][] grid = new Complex[1024][1024];

		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid.length; column++)
			{
				grid[row][column] = new Complex();
			}
		}

		// File name
		String csvFile = "input-real.csv";
		String csvFile2 = "input-imaginary.csv";
		String line = "";
		String line2 = "";
		String cvsSpilt = ",";
		int lineNum = 0;

		try
		{
			// Creates buffered reader by loading file
			BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
			BufferedReader bufferedReader2 = new BufferedReader(new FileReader(csvFile2));
			// Reads in each line of the reader
			while ((line = bufferedReader.readLine()) != null)
			{
				line2 = bufferedReader2.readLine();
				// Splits the line up by each comma
				String[] complexNumberAsString = line.split(cvsSpilt);
				String[] complexNumberAsString2 = line2.split(cvsSpilt);
				
				for (int i = 0; i < 1024; i++)
				{
					grid[lineNum][i] = new Complex(Double.parseDouble(complexNumberAsString[i]), Double.parseDouble(complexNumberAsString2[i]));
				}
				lineNum++;
			}
			bufferedReader.close();
			bufferedReader2.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return grid;
	}
}
