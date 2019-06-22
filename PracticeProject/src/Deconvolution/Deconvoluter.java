/**
 * This class is responsible for deconvolution.
 * 
 * It carries out the processes of a Hogbom 'CLEAN'.
 * 
 * The process can be easily adjusted to output the residuals
 * by change the grid returned by the Deconvolution class.
 *
 * @author Bobbie Ware
 */
package Deconvolution;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Deconvoluter
{

	public static void main(String[] args)
	{
		// Loads the grid to be deconvoluted
		Complex[][] grid = LoadGrid.loadGrid();

		// Carries out a 
		Complex[][] cleanedGrid = Deconvolution.deconvolute(grid, 1000);

		// Used to print the grid to a csv file
//		DecimalFormat df = new DecimalFormat("0.000000");
//
//		try
//		{
//			PrintWriter pw = new PrintWriter("output_image.csv");
//
//			for (int i = 0; i < cleanedGrid.length; i++)
//			{
//				pw.print(df.format(cleanedGrid[i][0].getReal()));
//				for (int j = 1; j < cleanedGrid.length; j++)
//				{
//					pw.print("," + df.format(cleanedGrid[i][j].getReal()));
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
