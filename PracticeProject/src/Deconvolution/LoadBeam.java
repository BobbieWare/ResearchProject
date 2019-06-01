package Deconvolution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoadBeam
{
	public static Complex[][] loadBeam()
	{
		
		Complex[][] grid = new Complex[15][15];

		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid.length; column++)
			{
				grid[row][column] = new Complex();
			}
		}

		// File name
		String csvFile = "DirtyBeam.csv";
		String line = "";
		String cvsSpilt = ",";
		int lineNum = 0;

		try
		{
			// Creates buffered reader by loading file
			BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
			// Reads in each line of the reader
			while ((line = bufferedReader.readLine()) != null)
			{
				// Splits the line up by each comma
				String[] complexNumberAsString = line.split(cvsSpilt);
				
				for (int i = 0; i < 15; i++)
				{
					if (Double.parseDouble(complexNumberAsString[i]) > 0)
					{
					grid[lineNum][i] = new Complex(Double.parseDouble(complexNumberAsString[i]), 0);						
					}
				}
				lineNum++;
			}
			bufferedReader.close();
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
