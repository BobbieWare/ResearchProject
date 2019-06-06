package UsingComplex;


public class iFFTUsingComplex
{
	public static Complex[][] twoDimensionifft(Complex[][] grid)
	{
		int n = grid.length;

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

		return shift(transformed);
	}

	/**
	 * The Fast Fourier Transform
	 *
	 * @param inputReal
	 *            an array of length n, the real part
	 * @param inputImag
	 *            an array of length n, the imaginary part
	 * @return a new array of length 2n
	 */
	public static Complex[] ifft(Complex[] input)
	{
		int n = input.length;

		Complex[] reversedArray = bitReverse(input);

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

	private static Complex[] bitReverse(Complex[] array)
	{
		Complex[] reversed = new Complex[array.length];
		for (int k = 0; k < array.length; k++)
		{
			// calculate postion of k in new array
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
