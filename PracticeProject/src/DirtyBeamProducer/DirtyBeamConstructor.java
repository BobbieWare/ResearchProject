package DirtyBeamProducer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DirtyBeamConstructor
{

	public static void main(String[] args)
	{
		Complex[][] grid = GridderUsingComplex.grid();;

		Complex[][] transformedGrid = iFFTUsingComplex.twoDimensionifft(grid);

		DecimalFormat df = new DecimalFormat("0.000000");

		try
		{
			PrintWriter pw = new PrintWriter("DirtyBeam.csv");

			for (int i = 510; i < 515; i++)
			{
				pw.print(df.format(transformedGrid[i][510].getReal()));
				for (int j = 511; j < 515; j++)
				{
					pw.print("," + df.format(transformedGrid[i][j].getReal()));
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
