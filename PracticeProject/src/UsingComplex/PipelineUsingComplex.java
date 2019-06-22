/**
 * This class is responsible for calling the correct functions for image synthesis.
 * It begins with the gridding of visibility data, then the grid produced is inverse 
 * Fourier transformed, then the image can be passed on for deconvolution.
 * 
 *  This version uses a 2D array of Complex objects to store the image.
 *
 * @author Bobbie Ware
 */
package UsingComplex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PipelineUsingComplex
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
			Complex[][] grid = GridderUsingComplex.grid();

			//Complex[][] transformedGrid = iFFTUsingComplex.twoDimensionifft(grid);
			
		}
		System.out.println(System.nanoTime() - time);
	}

	public static void main(String[] args)
	{
		tenIterations();
		
		// Carries out gridding
		Complex[][] grid = GridderUsingComplex.grid();

		// Transforms the grid
		Complex[][] transformedGrid = iFFTUsingComplex.twoDimensionifft(grid);

		// Used to print the grid to a csv file
		DecimalFormat df = new DecimalFormat("0.000000");

//		try
//		{
//			PrintWriter pw = new PrintWriter("test3.csv");
//
//			for (int i = 0; i < grid.length; i++)
//			{
//				pw.print(df.format(transformedGrid[i][0].getReal()));
//				for (int j = 1; j < grid.length; j++)
//				{
//					pw.print("," + df.format(transformedGrid[i][j].getReal()));
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
