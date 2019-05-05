package version1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Pipeline
{
	public static void main(String[] args)
	{
		double[][][] grid = Gridder.grid();

		double[][][] transformedGrid = iFFT.twoDimensionifft(grid[0], grid[1]);

		double[][] imaginary = transformedGrid[0];

		DecimalFormat df = new DecimalFormat("0.000000");
		
		try
		{
			PrintWriter pw = new PrintWriter("test2.txt");

			for (int i = 0; i < imaginary.length; i++)
			{
				pw.print(df.format(imaginary[i][0]));
				for (int j = 1; j < imaginary.length; j++)
				{
					pw.print("," + df.format(imaginary[i][j]));
				}
				pw.print("\n");
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
