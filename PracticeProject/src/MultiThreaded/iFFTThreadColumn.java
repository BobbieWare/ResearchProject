package MultiThreaded;

import java.util.ArrayList;
import java.util.List;

public class iFFTThreadColumn implements Runnable
{
	double[][] transformedReal;
	double[][] transformedImag;
	
	double[][] inputReal;
	double[][] inputImag;
	
	private int start;
	private int end;
	
	private final static int n = 1024;
	
	/**
	 * The Fast Fourier Transform
	 *
	 * @param inputReal
	 *            an array of length n, the real part
	 * @param inputImag
	 *            an array of length n, the imaginary part
	 * @return a new array of length 2n
	 */
	public static double[][] ifft(double[] inputReal, double[] inputImag)
	{
		int n = inputReal.length;

		double[] reversedReal = bitReverse(inputReal);
		double[] reversedImag = bitReverse(inputImag);

		for (int m = 2; m <= n; m *= 2)
		{
			double omegaReal = Math.cos((-2 * Math.PI) / m);
			double omegaImag = -Math.sin((-2 * Math.PI) / m);

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

	private static double[] bitReverse(double[] array)
	{
		double[] reversed = new double[array.length];
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
	
	public double[][] getTransformedReal()
	{
		return inputReal;
	}

	public double[][] getTransformedImag()
	{
		return inputImag;
	}	

	public iFFTThreadColumn(int start, int end, double[][] inputReal, double[][] inputImag)
	{
		this.inputReal = inputReal;
		this.inputImag = inputImag;
		
		this.transformedReal = new double[1024][1024];
		this.transformedImag = new double[1024][1024];
		
		this.start = start;
		this.end = end;
	}

	@Override
	public void run()
	{
		for (int column = start; column < end; column++)
		{
			// create temporary array for the column
			double[] realColumn = new double[n];
			double[] imagColumn = new double[n];

			for (int row = 0; row < n; row++)
			{
				realColumn[row] = inputReal[row][column];
				imagColumn[row] = inputImag[row][column];
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
	}
}
