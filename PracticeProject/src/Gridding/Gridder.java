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
	private final static int gridSize = 1024;
	/*
	 * Loads in the visibilities data from a given csv file.
	 */
	private static LinkedList<double[]> loadVisibilities()
	{
		// Where the data will be stored
		LinkedList<double[]> visibilities = new LinkedList<double[]>();

		// Amount of visibilities.
		int visibilitiesCount;

		// File name
		String csvFile = "Visibilities.csv";
		String line = "";
		String cvsSpilt = ",";

		try
		{
			// Creates buffered reader by loading file
			BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));

			visibilitiesCount = Integer.parseInt(bufferedReader.readLine());
			// Reads in each line of the reader
			while ((line = bufferedReader.readLine()) != null)
			{
				// Splits the line up by each comma
				String[] complexNumberAsString = line.split(cvsSpilt);

				// Converts the read String into a double
				double[] complexNumber = new double[4];
				for (int i = 0; i < complexNumber.length; i++)
				{
					complexNumber[i] = Double.parseDouble(complexNumberAsString[i]);
				}

				// Adds the complex number to the data
				visibilities.add(complexNumber);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return visibilities;
	}

	/*
	 * Take the u,v location and using the round function returns its grid location.
	 */
	private static double[] UVtoGrid(double u, double v)
	{
		double[] gridPoint = new double[2];
		
		// From data
		double cellSize = 0.00000484813681109536;
		double UVScale = gridSize*cellSize;
		
		double wavelengthsToMeters = 300000000.0 / 299792458.0; // frequencyHZ / speed of light

		gridPoint[0] = ((-u*wavelengthsToMeters) * UVScale) + gridSize/2;
		gridPoint[1] = ((v*wavelengthsToMeters) * UVScale) + gridSize/2;

		return gridPoint;
	}

	public static void main(String[] args)
	{
		// This represents the grid where we will be placing the visibilities
		double[][] realGrid = new double[gridSize][gridSize];
		double[][] imaginaryGrid = new double[gridSize][gridSize];
		
		// Visibilities
		LinkedList<double[]> visibilities = loadVisibilities();
		
		// Kernel from data
		double[] convolutionKernel = {0.013051,0.0308,0.061057,0.107442,0.172657,0.257676,0.361065,0.478594,0.603286,0.725965,0.836271,0.923991,0.98049,1};

		
		for (double[] visibility : visibilities)
		{
			double[] gridPoint = UVtoGrid(visibility[0], visibility[1]);
			
			for (int i = -(7-1)/2; i < (7-1)/2 ; i++)
			{
				for (int j = -(7-1)/2; j < (7-1)/2; j++)
				{
					
					realGrid[gridPoint[0]+i][gridPoint[1]+j] = visibility[2] * convolutionKernel[] 
				}
			}
			
			System.out.println(gridPoint[0] + "," + gridPoint[1]);
		}
	}
}
