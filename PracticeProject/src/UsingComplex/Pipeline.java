package UsingComplex;

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
			Complex[][] grid = Gridder.grid();

			Complex[][] transformedGrid = iFFT.twoDimensionifft(grid);
		}
		
		System.out.println(System.nanoTime()-time);

		// DecimalFormat df = new DecimalFormat("0.000000");
		//
		// try
		// {
		// PrintWriter pw = new PrintWriter("test3.txt");
		//
		// for (int i = 0; i < transformedGrid.length; i++)
		// {
		// pw.print(df.format(transformedGrid[i][0].getReal()));
		// for (int j = 1; j < transformedGrid.length; j++)
		// {
		// pw.print("," + df.format(transformedGrid[i][j].getReal()));
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
