package MultiThreaded;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * This class ------------------------------------------
 */
public class Gridder2GridsMultiThreaded
{
	private final static int gridSize = 1024;
	private final static int heightOfSupport = 7;
	private final static int widthOfSupport = 7;
	private static int visibilitiesCount;

	double[][][] realGrid;
	double[][][] imaginaryGrid;

	// Kernel from data
	private final static double[] prolateSpheroidal = { 0.013051, 0.0308, 0.061057, 0.107442, 0.172657, 0.257676,
			0.361065, 0.478594, 0.603286, 0.725965, 0.836271, 0.923991, 0.98049, 1 };

	/*
	 * Loads in the visibilities data from a given csv file.
	 */
	private static ArrayList<double[]> loadVisibilities()
	{
		// Where the data will be stored
		ArrayList<double[]> visibilities = new ArrayList<double[]>();

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
		double cellSize = 0.00000484813681109536;
		double UVScale = gridSize * cellSize;

		double wavelengthsToMeters = 300000000.0 / 299792458.0; // frequencyHZ / speed of light

		gridPoint[0] = ((-u * wavelengthsToMeters) * UVScale) + gridSize / 2;
		gridPoint[1] = ((v * wavelengthsToMeters) * UVScale) + gridSize / 2;

		return gridPoint;
	}

	private static int inKernel(double d)
	{
		return (int) ((d / 4) * 14);
	}

	public static double[][][] grid()
	{
		// This represents the grid where we will be placing the visibilities
		double[][][] realGrid = new double[4][gridSize][gridSize];
		double[][][] imaginaryGrid = new double[4][gridSize][gridSize];

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
		ArrayList<double[]> visibilities = loadVisibilities();

		Thread[] threads = new Thread[4];
		GriddingThread[] griddingThreads = new GriddingThread[4];

		int quarterOfCount = visibilitiesCount / 4;

		for (int i = 0; i < threads.length; i++)
		{
			int start = quarterOfCount * i;
			int end = quarterOfCount * (i + 1);

			griddingThreads[i] = new GriddingThread(start, end, visibilities, gridProlateSpheroidal);
			threads[i] = new Thread(griddingThreads[i]);
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++)
		{
			try
			{
				threads[i].join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < griddingThreads.length; i++)
		{
			realGrid[i] = griddingThreads[i].realGrid;
			imaginaryGrid[i] = griddingThreads[i].imaginaryGrid;
		}

		for (int row = 0; row < gridSize; row++)
		{
			for (int column = 0; column < griddingThreads.length; column++)
			{
				for (int i = 0; i < 4; i++)
				{
					realGrid[0][row][column] += realGrid[i][row][column];
					imaginaryGrid[0][row][column] += imaginaryGrid[i][row][column];
				}

			}
		}

		return new double[][][]{realGrid[0], imaginaryGrid[0]};
	}

	public void setRealGrid(double[][] realGrid, int i)
	{
		this.realGrid[i] = realGrid;
	}

	public void setImaginaryGrid(double[][] imaginaryGrid, int i)
	{
		this.imaginaryGrid[i] = imaginaryGrid;
	}

}
