/**
 * This class is responsible for calling the correct functions for image synthesis.
 * It begins with the gridding of visibility data, then the grid produced is inverse 
 * Fourier transformed, then the image can be passed on for deconvolution
 *
 * This version uses two 2D double arrays to store the image.
 *
 * @author Bobbie Ware
 */
package Using2grids;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Pipeline2Grids
{
	/**
	 * Function to carry out the gridder 10 times.
	 * Used to get runtimes for the gridder.
	 */
	private static void tenIterations()
	{		
		long time = System.nanoTime();

		for (int i = 0; i < 10; i++)
		{
			double[][][] grid = Gridder2Grids.grid();
		}
			System.out.println(System.nanoTime() - time);
	}

	public static void main(String[] args)
	{		
		//tenIterations();
		
		// Carries out gridding
		double[][][] grid = Gridder2Grids.grid();

		// Transforms the grid
		double[][][] transformedGrid = iFFT2Grids.twoDimensionifft(grid[0], grid[1]);

		double[][] real = transformedGrid[0];

		// Used to print the grid to a csv file
//		DecimalFormat df = new DecimalFormat("0.000000");
//
//		try
//		{
//			PrintWriter pw = new PrintWriter("test3.csv");
//
//			for (int i = 0; i < real.length; i++)
//			{
//				pw.print(df.format(real[i][0]));
//				for (int j = 1; j < real.length; j++)
//				{
//					pw.print("," + df.format(real[i][j]));
//				}
//				pw.print("\n");
//			}
//			pw.close();
//		} catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}

	}

}
