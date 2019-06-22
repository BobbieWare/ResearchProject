/*
 * The Fourier Transform is used to show different parts of a continuous signal. 
 * However, for Interferometry an Inverse Fourier Transform is used as we are taking 
 * the data from the Fourier Plane and creating an image from it. 
 * 
 * This implementation is a radix 2 iFFT, it also uses two 2D double arrays
 * 
 * @author Bobbie Ware
 */

package Using2grids;

public class iFFT2Grids
{
	/*
	 * Carries out a 2D transform on a grid
	 */
	public static double[][][] twoDimensionifft(double[][] inputReal, double[][] inputImag)
	{
		int n = inputReal.length;
		
		// The grid is shifted to correctly center the grid
		double[][][] shiftedGrid = shift(inputReal, inputImag);

		double[][] transformedReal = new double[n][n];
		double[][] transformedImag = new double[n][n];

		// transforming along each row
		for (int row = 0; row < n; row++)
		{
			double[][] transformed = ifft(shiftedGrid[0][row], shiftedGrid[1][row]);
			transformedReal[row] = transformed[0];
			transformedImag[row] = transformed[1];
		}

		// transforming down each column
		for (int column = 0; column < n; column++)
		{
			// create temporary array for the column
			double[] realColumn = new double[n];
			double[] imagColumn = new double[n];
			
			for (int row = 0; row < n; row++)
			{
				realColumn[row] = transformedReal[row][column];
				imagColumn[row] = transformedImag[row][column];
			}
			
			double[][] transformed = ifft(realColumn, imagColumn);
			realColumn = transformed[0];
			imagColumn = transformed[1];
			
			for (int row = 0; row < n; row++)
			{
				transformedReal[row][column] = realColumn[row];
				transformedImag[row][column] = imagColumn[row];
			}
		}
		
		// Output is shifted to re-center the grid
		return shift(transformedReal, transformedImag);
	}

	/**
	 * The transform for an array of complex numbers
	 */
	public static double[][] ifft(double[] inputReal, double[] inputImag)
	{
		int n = inputReal.length;

		// A bit reversal is carried out on the arrays
		double[] reversedReal = bitReverse(inputReal);
		double[] reversedImag = bitReverse(inputImag);

		// Decimation in time butterfly operations
		for (int m = 2; m <= n; m *= 2)
		{
			double omegaReal = Math.cos((2 * Math.PI) / m);
			double omegaImag = Math.sin((2 * Math.PI) / m);

			for (int k = 0; k < n; k += m)
			{
				double xReal = 1;
				double xImag = 0;

				for (int j = 0; j < m / 2; j++)
				{
					double tReal = (xReal * reversedReal[k + j + m / 2]) - (xImag * reversedImag[k + j + m / 2]);
					double tImag = (xReal * reversedImag[k + j + m / 2]) + (xImag * reversedReal[k + j + m / 2]);

					double uReal = reversedReal[k + j];
					double uImag = reversedImag[k + j];

					reversedReal[k + j] = uReal + tReal;
					reversedImag[k + j] = uImag + tImag;

					reversedReal[k + j + m / 2] = uReal - tReal;
					reversedImag[k + j + m / 2] = uImag - tImag;
					
					double newXR = (xReal * omegaReal) - (xImag * omegaImag);
					double newXI = (xReal * omegaImag) + (xImag * omegaReal);
					
					xReal = newXR;
					xImag = newXI;
				}
			}
		}
		return new double[][]{ reversedReal, reversedImag };
	}

	/*
	 *  A bit reversal is used to correct for the rearrangement of data in the transform
	 */
	private static double[] bitReverse(double[] array)
	{
		double[] reversed = new double[array.length];
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
	public static double[][][] shift(double[][] inputReal, double[][] inputImag)
	{
		double[][] realShifted = new double[1024][1024];
		double[][] imagShifted = new double[1024][1024];

		for (int row = 0; row < realShifted.length; row++)
		{
			for (int column = 0; column < realShifted.length; column++)
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

				realShifted[row][column] = inputReal[row + xDiff][column + yDiff];
				imagShifted[row][column] = inputImag[row + xDiff][column + yDiff];
			}
		}
		return new double[][][]{ realShifted, imagShifted };
	}
}
