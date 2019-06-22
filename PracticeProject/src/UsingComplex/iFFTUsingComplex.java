/*
 * The Fourier Transform is used to show different parts of a continuous signal. 
 * However, for Interferometry an Inverse Fourier Transform is used as we are taking 
 * the data from the Fourier Plane and creating an image from it. 
 * 
 * This implementation is a radix 2 iFFT, it also a 2D Complex object array
 * 
 * @author Bobbie Ware
 */
package UsingComplex;

public class iFFTUsingComplex
{
	public static Complex[][] twoDimensionifft(Complex[][] grid)
	{
		int n = grid.length;

		// The grid is shifted to correctly center the grid
		Complex[][] transformed = shift(grid);

		// transforming along each row
		for (int row = 0; row < n; row++)
		{
			transformed[row] = ifft(transformed[row]);
		}

		// transforming down each column
		for (int column = 0; column < n; column++)
		{
			// create temporary array for the column

			Complex[] transformedColumn = new Complex[n];

			for (int row = 0; row < n; row++)
			{
				transformedColumn[row] = transformed[row][column];
			}

			transformedColumn = ifft(transformedColumn);

			for (int row = 0; row < n; row++)
			{
				transformed[row][column] = transformedColumn[row];
			}
		}

		// Output is shifted to re-center the grid
		return shift(transformed);
	}

	/**
	 * The transform for an array of complex numbers
	 */
	public static Complex[] ifft(Complex[] input)
	{
		int n = input.length;

		// A bit reversal is carried out on the array
		Complex[] reversedArray = bitReverse(input);

		// Decimation in time butterfly operations
		for (int m = 2; m <= n; m *= 2)
		{
			Complex omega = new Complex(Math.cos((-2 * Math.PI) / m), Math.sin((-2 * Math.PI) / m));

			for (int k = 0; k < n; k += m)
			{

				Complex x = new Complex(1, 0);

				for (int j = 0; j < m / 2; j++)
				{

					Complex t = x.multiply(reversedArray[k + j + m / 2]);

					Complex u = reversedArray[k + j];

					reversedArray[k + j] = u.add(t);

					reversedArray[k + j + m / 2] = u.subtract(t);

					x.multiplyInPlace(omega);
				}
			}
		}
		return reversedArray;
	}

	/*
	 *  A bit reversal is used to correct for the rearrangement of data in the transform
	 */
	private static Complex[] bitReverse(Complex[] array)
	{
		Complex[] reversed = new Complex[array.length];
		for (int k = 0; k < array.length; k++)
		{
			// calculate position of k in new array
			int kNew = k;
			int r = 0;
			for (int j = 1; j < array.length; j *= 2)
			{
				int b = kNew & 1;
				r = (r << 1) + b;
				kNew = (kNew >>> 1);
			}
			reversed[r] = array[k];
		}
		return reversed;
	}
	
	/*
	 * Carries out a shift on the grid rearrange the order of the points.
	 * It places the corners at the center of the image and move the other points around 
	 * accordingly.
	 */
	public static Complex[][] shift(Complex[][] grid)
	{
		Complex[][] shiftedGrid = new Complex[1024][1024];

		for (int row = 0; row < shiftedGrid.length; row++)
		{
			for (int column = 0; column < shiftedGrid.length; column++)
			{
				int xDiff, yDiff;

				if (row < 512)
				{
					xDiff = 512;
				}
				else
				{
					xDiff = -512;
				}
				if (column < 512)
				{
					yDiff = 512;
				}
				else
				{
					yDiff = -512;
				}

				shiftedGrid[row][column] = grid[row + xDiff][column + yDiff];
			}
		}
		return shiftedGrid;
	}
}
