/**
 * This class is responsible for calling the correct functions for image synthesis.
 * It begins with the gridding of visibility data, then the grid produced is inverse 
 * Fourier transformed, then the image can be passed on for deconvolution.
 * 
 * This version using multi-threading to improve performance of the gridder and 
 * the Fourier Transform.
 *
 * @author Bobbie Ware
 */
package UsingMultipleThreads;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class PipelineMultiThreaded
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
			double[][][] grid = GridderMultiThreaded.grid();

			//double[][][] transformedGrid = iFFT2GridsMultiThreaded.twoDimensionifft(grid[0], grid[1]);
			
		}
			System.out.println(System.nanoTime() - time);
	}

	public static void main(String[] args)
	{		
		//tenIterations();
		
		// Carries out gridding
		double[][][] grid = GridderMultiThreaded.grid();

		// Transforms the grid
		double[][][] transformedGrid = iFFTMultiThreaded.twoDimensionifft(grid[0], grid[1]);

		// Applies the convolution correction to the image
		double[][] real = ConvolutionCorrection.correct(transformedGrid[0]);

		// Used to print the grid to a csv file
//		DecimalFormat df = new DecimalFormat("0.000000");
//
//		try
//		{
//			PrintWriter pw = new PrintWriter("output_image.csv");
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
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
