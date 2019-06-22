/*
 * This class is used to form the dirty beam, also known as
 * the point spread function
 * 
 * @author Bobbie Ware
 */
package DirtyBeamProducer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import UsingComplex.Complex;
import UsingComplex.iFFTUsingComplex;

public class DirtyBeamConstructor
{

	public static void main(String[] args)
	{
		UsingComplex.Complex[][] grid = GridderUsingComplexDirtyBeam.grid();

		Complex[][] transformedGrid = iFFTUsingComplex.twoDimensionifft(grid);

//		DecimalFormat df = new DecimalFormat("0.000000");
//
//		try
//		{
//			PrintWriter pw = new PrintWriter("DirtyBeam.csv");
//
//			for (int i = 510; i < 515; i++)
//			{
//				pw.print(df.format(transformedGrid[i][510].getReal()));
//				for (int j = 511; j < 515; j++)
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
