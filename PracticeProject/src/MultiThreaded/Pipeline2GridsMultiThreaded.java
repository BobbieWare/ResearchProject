package MultiThreaded;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import Using2grids.Gridder2Grids;
import Using2grids.iFFT2Grids;

public class Pipeline2GridsMultiThreaded
{
	private static void tenIterations()
	{		
		long time = System.nanoTime();

		for (int i = 0; i < 10; i++)
		{
			double[][][] grid = Gridder2GridsMultiThreaded.grid();

			//double[][][] transformedGrid = iFFT2GridsMultiThreaded.twoDimensionifft(grid[0], grid[1]);
			
		}
			System.out.println(System.nanoTime() - time);
	}

	public static void main(String[] args)
	{		
		tenIterations();
		
		double[][][] grid = Gridder2GridsMultiThreaded.grid();

		double[][][] transformedGrid = iFFT2GridsMultiThreaded.twoDimensionifft(grid[0], grid[1]);

		double[][] real = transformedGrid[0];

		DecimalFormat df = new DecimalFormat("0.000000");

		try
		{
			PrintWriter pw = new PrintWriter("test3.csv");

			for (int i = 0; i < real.length; i++)
			{
				pw.print(df.format(real[i][0]));
				for (int j = 1; j < real.length; j++)
				{
					pw.print("," + df.format(real[i][j]));
				}
				pw.print("\n");
			}
			pw.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
