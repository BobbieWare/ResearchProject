/*
 * This class is used to place visibility data onto a grid.
 * The grid for this version is a 2D array of Complex objects.
 * 
 * @author Bobbie Ware
 */
package UsingComplex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class GridderUsingComplex
{
	private final static int gridSize = 1024;
	private final static int heightOfSupport = 7;
	private final static int widthOfSupport = 7;

	// Kernel from data
	private final static double[] prolateSpheroidal = { 0.013051, 0.0308, 0.061057, 0.107442, 0.172657, 0.257676,
			0.361065, 0.478594, 0.603286, 0.725965, 0.836271, 0.923991, 0.98049, 1 };

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
		String csvFile = "FILE_TO_LOAD.csv";
		String line = "";
		String cvsSpilt = ",";

		try
		{
			// Creates buffered reader by loading file
			BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));

			// The first line will the count of visibilities
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
					complexNumber[i] = Double.valueOf(complexNumberAsString[i]);
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
		double cellSize = Double.parseDouble("4.848136811095360e-06");
		double UVScale = gridSize * cellSize;

		double wavelengthsToMeters = 300000000.0 / 299792458.0; // frequencyHZ / speed of light

		gridPoint[0] = ((-u * wavelengthsToMeters) * UVScale) + gridSize / 2;
		gridPoint[1] = ((v * wavelengthsToMeters) * UVScale) + gridSize / 2;

		return gridPoint;
	}

	/*
	 * Rounds d to find the correct value with the kernel to use
	 */
	private static int inKernel(double d)
	{
		return (int) ((d / 4) * 14);
	}

	/*
	 * Function to carry out all the processes involved in forming the grid
	 */
	public static Complex[][] grid()
	{
		// This represents the grid where we will be placing the visibilities
		Complex[][] grid = new Complex[gridSize][gridSize];

		// Fills the grid with Complex object as we want to add
		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid.length; column++)
			{
				grid[row][column] = new Complex();
			}
		}
		
		// The following lines forms the 2d prolate spherodial.
		double[][] gridProlateSpheroidal = new double[14][14];

		for (int i = 0; i < gridProlateSpheroidal[0].length; i++)
		{
			for (int j = 0; j < gridProlateSpheroidal[0].length; j++)
			{
				gridProlateSpheroidal[gridProlateSpheroidal[0].length - 1 - i][gridProlateSpheroidal[0].length - 1
						- j] = prolateSpheroidal[i] * prolateSpheroidal[j];
			}
		}

		// Visibilities
		LinkedList<double[]> visibilities = loadVisibilities();
		
		// Each visibility is placed on the grid
		for (double[] visibility : visibilities)
		{
			// The absolute point on the grid
			double[] trueGridPoint = UVtoGrid(visibility[0], visibility[1]);
			
			// Rounds the true point to a grid point 
			int[] nearestGridPoint = { (int) trueGridPoint[0], (int) trueGridPoint[1] };

			// The area in which each point is spread
			int xMin = -(heightOfSupport - 1) / 2;
			int xMax = (heightOfSupport - 1) / 2;
			int yMin = -(widthOfSupport - 1) / 2;
			int yMax = (widthOfSupport - 1) / 2;

			// For the 2D support area
			for (int i = xMin; i <= xMax; i++)
			{
				for (int j = yMin; j <= yMax; j++)
				{
					// Finds the corresponding point in the kernel
					double deltaX = (nearestGridPoint[0] + i) - trueGridPoint[0];
					int kernelX = inKernel(deltaX);
					double deltaY = (nearestGridPoint[1] + j) - trueGridPoint[1];
					int kernelY = inKernel(deltaY);
					
					// Loads the support value
					double kernelValue = gridProlateSpheroidal[Math.abs(kernelX)][Math.abs(kernelY)];
					
					// Stores the value to be add to the Complex grid
					Complex newValue = new Complex(visibility[2] * kernelValue, visibility[3] * kernelValue);
					
					// In-place addition of the Complex number
					grid[nearestGridPoint[0] + i][nearestGridPoint[1] + j].addInPlace(newValue);
				}
			}
		}

		return grid;
	}
}
