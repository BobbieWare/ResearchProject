package UsingComplex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PipelineUsingComplex
{
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
		
		Complex[][] grid = GridderUsingComplex.grid();

		Complex[][] transformedGrid = iFFTUsingComplex.twoDimensionifft(grid);

		DecimalFormat df = new DecimalFormat("0.000000");

		try
		{
			PrintWriter pw = new PrintWriter("test3.csv");

			for (int i = 0; i < grid.length; i++)
			{
				pw.print(df.format(transformedGrid[i][0].getReal()));
				for (int j = 1; j < grid.length; j++)
				{
					pw.print("," + df.format(transformedGrid[i][j].getReal()));
				}
				pw.print("\n");
			}
			pw.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

}
