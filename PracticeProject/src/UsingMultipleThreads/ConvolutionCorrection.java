package UsingMultipleThreads;

public class ConvolutionCorrection
{
	public static double[][] correct(double[][] grid)
	{
		int size = grid.length;
		double halfSize = size / 2;
		double cellSize = Double.parseDouble("4.848136811095360e-06");
		double nuX = 0.0;
		double nuY = 0.0;
		double taperX = 0.0;
		double taperY = 0.0;
		double l = 0.0;
		double m = 0.0;

		for (int row = 0; row < grid.length; row++)
		{
			nuY = Math.abs((row - halfSize) / halfSize);
			taperY = getSpheriodal(nuY);
			m = Math.pow((row - halfSize) * cellSize, 2);
			
			for (int column = 0; column < grid.length; column++)
			{
				nuX = Math.abs((column - halfSize) / halfSize);
				taperX = taperY * getSpheriodal(nuX);

				if(Math.abs(taperX) > (1E-10))
				{
					l = Math.pow((column - halfSize) * cellSize, 2.0);
					grid[row][column] /= taperX * Math.sqrt(1.0 - l - m);
				}
				else
					grid[row][column] = 0.0;
			}
		}
		
		return grid;
	}

	private static double getSpheriodal(double nu)
	{
		double[] p = { 0.08203343, -0.3644705, 0.627866, -0.5335581, 0.2312756, 0.004028559, -0.03697768, 0.1021332,
				-0.1201436, 0.06412774 };
		double[] q = { 1.0, 0.8212018, 0.2078043, 1.0, 0.9599102, 0.2918724 };

		int part = 0;
		int sp = 0;
		int sq = 0;
		double nuend = 0.0;
		double delta = 0.0;
		double top = 0.0;
		double bottom = 0.0;

		if (nu >= 0.0 && nu < 0.75)
		{
			part = 0;
			nuend = 0.75;
		}
		else if (nu >= 0.75 && nu < 1.0)
		{
			part = 1;
			nuend = 1.0;
		}
		else return 0.0;

		delta = nu * nu - nuend * nuend;
		sp = part * 5;
		sq = part * 3;
		top = p[sp];
		bottom = q[sq];

		for (int i = 1; i < 5; i++)
			top += p[sp + i] * Math.pow(delta, i);
		for (int i = 1; i < 3; i++)
			bottom += q[sq + i] * Math.pow(delta, i);
		return (bottom == 0.0) ? 0.0 : top / bottom;
	}
}
