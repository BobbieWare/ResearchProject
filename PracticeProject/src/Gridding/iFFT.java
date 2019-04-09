package Gridding;

public class iFFT
{

	/**
	 * The Fast Fourier Transform (generic version, with NO optimizations).
	 *
	 * @param inputReal
	 *            an array of length n, the real part
	 * @param inputImag
	 *            an array of length n, the imaginary part
	 * @param DIRECT
	 *            TRUE = direct transform, FALSE = inverse transform
	 * @return a new array of length 2n
	 */
	public static double[] fft(final double[] inputReal, double[] inputImag, boolean DIRECT)
	{
		// - n is the dimension of the problem
		// - nu is its logarithm in base e
		int n = inputReal.length;

		// Declaration and initialization of the variables
		// logBase2OfN should be an integer so no information lost in the cast
		int logBase2OfN = (int) (Math.log(n) / Math.log(2.0));
		int m;

		double omegaReal;
		double omegaImag;
		int n2 = n / 2;
		int nu1 = logBase2OfN - 1;
		double tReal, tImag, xReal, xImag, uReal, uImag;

		double constant = -2 * Math.PI;

		// First phase - calculation
		int s = 1;
		for (int l = s; l <= logBase2OfN; l++)
		{
			m = (int) Math.pow(2, s);

			omegaReal = Math.cos((2 * Math.PI) / m);
			omegaImag = -Math.sin((2 * Math.PI) / m);

			for (int k = 0; k < (n * m); k++)
			{
				xReal = 1;
				xImag = 1;
				for (int j = 0; j < m / 2; j++)
				{
					tReal = (xReal * inputReal[k + j + m / 2]) - (xImag * inputImag[k + j + m / 2]);
					tImag = (xReal * inputImag[k + j + m / 2]) + (xImag * inputImag[k + j + m / 2]);

					uReal = inputReal[k + j];
					uImag = inputImag[k + j];

					inputReal[k + j] = tReal + uReal;
					inputImag[k + j] = tImag + uImag;

					inputReal[k + j + m / 2] = tReal - uReal;
					inputImag[k + j + m / 2] = tImag - uImag;

					xReal = (xReal * omegaReal) - (xImag * omegaImag);
					xImag = (xReal * omegaImag) + (xImag * omegaReal);
				}
			}
		}
	}

	/**
	 * The reference bitreverse function.
	 */
	private static double[] bitreverseReference(double[] array)
	{
		for (int k = 0; k < array.length; k++)
		{
			int r = 0;
			for (int j = 0; j < array.length; j++)
			{

			}
		}

	}
}
