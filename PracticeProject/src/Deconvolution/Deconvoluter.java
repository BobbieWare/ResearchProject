package Deconvolution;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Deconvoluter
{

	public static void main(String[] args)
	{
		Complex[][] grid = LoadGrid.loadGrid();

		Complex[][] cleanedGrid = Deconvolution.deconvolute(grid, 2000);

		DecimalFormat df = new DecimalFormat("0.000000");

		try
		{
			PrintWriter pw = new PrintWriter("cleaned.csv");

			for (int i = 0; i < cleanedGrid.length; i++)
			{
				pw.print(df.format(cleanedGrid[i][0].getReal()));
				for (int j = 1; j < cleanedGrid.length; j++)
				{
					pw.print("," + df.format(cleanedGrid[i][j].getReal()));
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
