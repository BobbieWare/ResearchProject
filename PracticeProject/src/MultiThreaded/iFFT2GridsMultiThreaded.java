package MultiThreaded;

public class iFFT2GridsMultiThreaded
{
	public static double[][][] twoDimensionifft(double[][] inputReal, double[][] inputImag)
	{
		int n = inputReal.length;

		double[][] transformedReal = new double[n][n];
		double[][] transformedImag = new double[n][n];

		Thread[] threads = new Thread[4];
		iFFTThreadRow[] griddingThreads = new iFFTThreadRow[4];

		int quarterOfCount = 1024 / 4;

		for (int i = 0; i < threads.length; i++)
		{
			int start = quarterOfCount * i;
			int end = quarterOfCount * (i + 1);

			griddingThreads[i] = new iFFTThreadRow(start, end, inputReal, inputImag);
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
				transformedReal[j] = griddingThreads[i].transformedReal[i];
				transformedImag[j] = griddingThreads[i].transformedImag[i];
			}
		}
		
		Thread[] threads2 = new Thread[4];
		iFFTThreadColumn[] griddingThreads2 = new iFFTThreadColumn[4];

		for (int i = 0; i < threads2.length; i++)
		{
			int start = quarterOfCount * i;
			int end = quarterOfCount * (i + 1);

			griddingThreads2[i] = new iFFTThreadColumn(start, end, inputReal, inputImag);
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
				transformedReal[j] = griddingThreads2[i].transformedReal[i];
				transformedImag[j] = griddingThreads2[i].transformedImag[i];
			}
		}
		
		return new double[][][]{ transformedReal, transformedImag};
	}

}
