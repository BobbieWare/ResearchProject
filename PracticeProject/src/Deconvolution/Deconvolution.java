/**
 * This class is responsible for carrying out the CLEAN algorithm
 * A factor of 0.1 is used as the fraction to be taken away from
 * the dirty image.
 *
 * @author Bobbie Ware
 */
package Deconvolution;

public class Deconvolution
{
	/*
	 * Carries out the process of deconvolution for a given amount of iterations 
	 */
	public static Complex[][] deconvolute(Complex[][] grid, int iterations)
	{
		// Location of brightest pixel
		int[] brightestPixel;
		
		Complex[][] cleaned = new Complex[1024][1024];
		
		// Loads in the dirty beam of psf (point spread function)
		Complex[][] beam = LoadBeam.loadBeam();
		
		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid.length; column++)
			{
				cleaned[row][column] = new Complex();
			}
		}
		
		// Carried out i times
		for (int i = 0; i < iterations; i++)
		{
			brightestPixel = findBrightest(grid);
			
			int xMin = -2;
			int xMax = 2;
			int yMin = -2;
			int yMax = 2;

			// Using the shape of the dirty beam, 0.1 of the beam is taken
			// away from the image and placed onto the 'CLEAN' image
			for (int row = xMin; row <= xMax; row++)
			{
				for (int column = yMin; column <= yMax; column++)
				{
					int xCoordinate = (brightestPixel[0] + row);
					int yCoordinate = (brightestPixel[1] + column);
					
					double factor = 0.1;
					double ammount = beam[2 + row][2 + column].getReal() * factor;
					
					// Adds the point to the cleaned grid
					Complex newValue = new Complex(ammount, 0);
					cleaned[xCoordinate][yCoordinate].addInPlace(newValue);
					grid[xCoordinate][yCoordinate].subtractInPlace(newValue);
				}
			}			
		}
		
		// this value can be changed to grid to return the 
		// residuals from the 'CLEAN'
		return cleaned;
		
	}
	
	/*
	 * Used to find the strongest signal in the image
	 */
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
