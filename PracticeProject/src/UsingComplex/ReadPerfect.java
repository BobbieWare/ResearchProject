package UsingComplex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadPerfect
{
	public static Complex[][] loadVisibilities()
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
		String csvFile = "perfect_image_real.csv";
		String csvFile2 = "perfect_image_imaginary.csv";
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
