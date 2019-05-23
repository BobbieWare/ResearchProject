package UsingComplex;

import java.util.Arrays;

public class testIFFT
{
	public static Complex[][] twoDimensionifft(Complex[][] input)
	{
		int n = input.length;

		Complex[][] transformed = new Complex[n][n];

		// transforming along each row
		for (int row = 0; row < n; row++)
		{
			transformed[row] = ifft(input[row]);
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

		Complex[][] shifted = new Complex[n][n];

		for (int row = 0; row < shifted.length; row++)
		{
			for (int column = 0; column < shifted.length; column++)
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

				shifted[row][column] = transformed[row + xDiff][column + yDiff];
			}
		}

		return shifted;
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

		int m;

		int i, j, k, n1, n2, a;
		Complex x, t2;

		// Lookup tables. Only need to recompute when size of FFT changes.
		double[] cos;
		double[] sin;

		n = 1024;
		m = (int) (Math.log(n) / Math.log(2));

		cos = new double[n / 2];
		sin = new double[n / 2];

		for (int p = 0; p < n / 2; p++)
		{
			cos[p] = Math.cos(2 * Math.PI * p / n);
			sin[p] = -Math.sin(2 * Math.PI * p / n);
		}

		n1 = 0;
		n2 = 1;

		for (i = 0; i < m; i++)
		{
			n1 = n2;
			n2 = n2 + n2;
			a = 0;

			for (j = 0; j < n1; j++)
			{
				x = new Complex(cos[a], sin[a]);
				a += 1 << (m - i - 1);

				for (k = j; k < n; k = k + n2)
				{
					t2 = x.multiply(reversedArray[k + n1]);
					reversedArray[k + n1] = reversedArray[k].subtract(t2);
					reversedArray[k] = reversedArray[k].add(t2);
				}
			}
		}

		return reversedArray;
	}

	private static Complex[] bitReverse(Complex[] array)
	{
		int n = array.length;
		Complex[] reversed = Arrays.copyOf(array, n);
		int i, j, n1, n2;
		Complex t1;

		// Bit-reverse
		j = 0;
		n2 = n / 2;
		for (i = 1; i < n - 1; i++)
		{
			n1 = n2;
			while (j >= n1)
			{
				j = j - n1;
				n1 = n1 / 2;
			}
			j = j + n1;

			if (i < j)
			{
				t1 = reversed[i];
				reversed[i] = reversed[j];
				reversed[j] = t1;
			}
		}
		return reversed;
	}
}
