package Using2grids;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Pipeline
{
	public static void main(String[] args)
	{

		long time = System.nanoTime();

		for (int i = 0; i < 10; i++)
		{
			double[][][] grid = Gridder.grid();

			double[][][] transformedGrid = iFFT.twoDimensionifft(grid[0], grid[1]);
		}

		System.out.println(System.nanoTime() - time);

//		double[][] real = grid[0];

		// DecimalFormat df = new DecimalFormat("0.000000");
		//
		// try
		// {
		// PrintWriter pw = new PrintWriter("test2.txt");
		//
		// for (int i = 0; i < real.length; i++)
		// {
		// pw.print(df.format(real[i][0]));
		// for (int j = 1; j < real.length; j++)
		// {
		// pw.print("," + df.format(real[i][j]));
		// }
		// pw.print("\n");
		// }
		// pw.close();
		// } catch (FileNotFoundException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

}
