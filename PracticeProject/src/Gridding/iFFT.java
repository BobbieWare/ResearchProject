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
		double tReal, tImag, x, t;

		double constant = -2 * Math.PI;

		// First phase - calculation
		int s = 1;
		for (int l = s; l <= logBase2OfN; l++)
		{
			m = (int) Math.pow(2, s);
			omegaReal = Math.cos((2 * Math.PI) / m);
			omegaImag = -Math.sin((2 * Math.PI) / m);
			
			for (int k = 0; k < (n*m); k++)
			{
				x = 1;
				for (int j = 0; j < m/2; j++)
				{
					tReal = x * inputReal[k+j+m/2];
				}
			}
			// while (s < n)
			// {
			// for (int i = 1; i <= n2; i++)
			// {
			// p = bitreverseReference(s >> nu1, logBase2OfN);
			// // direct FFT or inverse FFT
			// arg = constant * p / n;
			// cos = Math.cos(arg);
			// sin = Math.sin(arg);
			// tReal = inputReal[s + n2] * cos + inputImag[s + n2] * sin;
			// tImag = inputImag[s + n2] * cos - inputReal[s + n2] * sin;
			// inputReal[s + n2] = inputReal[s] - tReal;
			// inputImag[s + n2] = inputImag[s] - tImag;
			// inputReal[s] += tReal;
			// inputImag[s] += tImag;
			// s++;
			// }
			// s += n2;
			// }
			// s = 0;
			// nu1--;
			// n2 /= 2;
		}

		// Second phase - recombination
		s = 0;
		int r;
		while (s < n)
		{
			r = bitreverseReference(s, logBase2OfN);
			if (r > s)
			{
				tReal = inputReal[s];
				tImag = inputImag[s];
				inputReal[s] = inputReal[r];
				inputImag[s] = inputImag[r];
				inputReal[r] = tReal;
				inputImag[r] = tImag;
			}
			s++;
		}

		// Here I have to mix xReal and xImag to have an array (yes, it should
		// be possible to do this stuff in the earlier parts of the code, but
		// it's here to readibility).
		double[] newArray = new double[inputReal.length * 2];
		double radice = 1 / Math.sqrt(n);
		for (int i = 0; i < newArray.length; i += 2)
		{
			int i2 = i / 2;
			// I used Stephen Wolfram's Mathematica as a reference so I'm going
			// to normalize the output while I'm copying the elements.
			newArray[i] = inputReal[i2] * radice;
			newArray[i + 1] = inputImag[i2] * radice;
		}
		return newArray;
	}

	/**
	 * The reference bitreverse function.
	 */
	private static int bitreverseReference(int j, int nu)
	{
		int j2;
		int j1 = j;
		int k = 0;
		for (int i = 1; i <= nu; i++)
		{
			j2 = j1 / 2;
			k = 2 * k + j1 - 2 * j2;
			j1 = j2;
		}
		return k;
	}
}
