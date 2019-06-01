package Deconvolution;


public class Deconvolution
{
	public static Complex[][] deconvolute(Complex[][] grid, int iterations)
	{
		int[] brightestPixel;
		
		Complex[][] cleaned = new Complex[1024][1024];
		Complex[][] beam = LoadBeam.loadBeam();
		
		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid.length; column++)
			{
				cleaned[row][column] = new Complex();
			}
		}
		
		for (int i = 0; i < iterations; i++)
		{
			brightestPixel = findBrightest(grid);
			double strength = grid[brightestPixel[0]][brightestPixel[1]].getReal();
			
			int xMin = -7;
			int xMax = 7;
			int yMin = -7;
			int yMax = 7;

			for (int row = xMin; row <= xMax; row++)
			{
				for (int column = yMin; column <= yMax; column++)
				{
					int xCoordinate = (brightestPixel[0] + row);
					int yCoordinate = (brightestPixel[1] + column);
					
					double factor = 0.01;
					double ammount = beam[7 + row][7 + column].getReal() * factor * strength;
					
					Complex newValue = new Complex(ammount, 0);
					cleaned[xCoordinate][yCoordinate].addInPlace(newValue);
					grid[xCoordinate][yCoordinate].subtractInPlace(newValue);
				}
			}			
		}
		
		
		return cleaned;
		
	}
	
	private static int[] findBrightest(Complex[][] grid)
	{
		int xLocation = 0;
		int yLcoation = 0;
		
		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid.length; column++)
			{
				if (grid[row][column].getReal() > grid[xLocation][yLcoation].getReal())
				{
					xLocation = row;
					yLcoation = column;
				}
			}
		}
		return new int[] {xLocation, yLcoation};
	}
}
