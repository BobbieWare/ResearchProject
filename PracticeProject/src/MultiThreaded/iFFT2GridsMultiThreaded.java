package MultiThreaded;

public class iFFT2GridsMultiThreaded
{
	public static double[][][] twoDimensionifft(double[][] inputReal, double[][] inputImag)
	{
		int n = inputReal.length;

		double[][][] shiftedGrid = shift(inputReal, inputImag);

		double[][] transformedReal = new double[n][n];
		double[][] transformedImag = new double[n][n];

		Thread[] threads = new Thread[4];
		iFFTThreadRow[] griddingThreads = new iFFTThreadRow[4];

		int quarterOfCount = 1024 / 4;

		for (int i = 0; i < threads.length; i++)
		{
			int start = quarterOfCount * i;
			int end = quarterOfCount * (i + 1);

			griddingThreads[i] = new iFFTThreadRow(start, end, shiftedGrid[0], shiftedGrid[1]);
			threads[i] = new Thread(griddingThreads[i]);
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++)
		{
			try
			{
				threads[i].join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < griddingThreads.length; i++)
		{
			for (int j = (quarterOfCount * i); j < quarterOfCount * (i + 1); j++)
			{
				transformedReal[j] = griddingThreads[i].transformedReal[j];
				transformedImag[j] = griddingThreads[i].transformedImag[j];
			}
		}

		Thread[] threads2 = new Thread[4];
		iFFTThreadColumn[] griddingThreads2 = new iFFTThreadColumn[4];

		for (int i = 0; i < threads2.length; i++)
		{
			int start = quarterOfCount * i;
			int end = quarterOfCount * (i + 1);

			griddingThreads2[i] = new iFFTThreadColumn(start, end, transformedReal, transformedImag);
			threads2[i] = new Thread(griddingThreads2[i]);
			threads2[i].start();
		}

		for (int i = 0; i < threads2.length; i++)
		{
			try
			{
				threads2[i].join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 0; i < griddingThreads2.length; i++)
		{
			for (int j = (quarterOfCount * i); j < quarterOfCount * (i + 1); j++)
			{
				for (int j2 = 0; j2 < transformedReal.length; j2++)
				{
					transformedReal[j2][j] = griddingThreads2[i].transformedReal[j2][j];
					transformedImag[j2][j] = griddingThreads2[i].transformedImag[j2][j];
				}
			}
		}

		double[][][] shiftedGrids = shift(transformedReal, transformedImag);
		
		return shiftedGrids;
	}

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
