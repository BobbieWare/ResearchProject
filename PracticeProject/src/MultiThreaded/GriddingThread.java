package MultiThreaded;

import java.util.ArrayList;
import java.util.List;

public class GriddingThread implements Runnable
{
	private final static int gridSize = 1024;

	private int support = 7;
	private int start;
	private int end;
	public int arrayToAccess;

	private ArrayList<double[]> visibilities;
	private double[][] gridProlateSpheroidal;

	public double[][] realGrid;
	public double[][] imaginaryGrid;

	public GriddingThread(int start, int end, ArrayList<double[]> visibilities, double[][] gridProlateSpheroidal)
	{
		this.start = start;
		this.end = end;
		this.visibilities = visibilities;
		this.gridProlateSpheroidal = gridProlateSpheroidal;
		this.realGrid = new double[gridSize][gridSize];
		this.imaginaryGrid = new double[gridSize][gridSize];
	}

	@Override
	public void run()
	{
		// Visibilities
		List<double[]> visibilitiesToGrid = visibilities.subList(start, end);

		for (double[] visibility : visibilitiesToGrid)
		{
			double[] trueGridPoint = UVtoGrid(visibility[0], visibility[1]);
			int[] nearestGridPoint = { (int) trueGridPoint[0], (int) trueGridPoint[1] };

			int xMin = -(support - 1) / 2;
			int xMax = (support - 1) / 2;
			int yMin = -(support - 1) / 2;
			int yMax = (support - 1) / 2;

			for (int i = xMin; i <= xMax; i++)
			{
				for (int j = yMin; j <= yMax; j++)
				{
					double deltaX = (nearestGridPoint[0] + i) - trueGridPoint[0];
					int kernelX = inKernel(deltaX);
					double deltaY = (nearestGridPoint[1] + j) - trueGridPoint[1];
					int kernelY = inKernel(deltaY);
					double kernelValue = gridProlateSpheroidal[Math.abs(kernelX)][Math.abs(kernelY)];
					realGrid[nearestGridPoint[0] + i][nearestGridPoint[1] + j] += visibility[2] * kernelValue;
					imaginaryGrid[nearestGridPoint[0] + i][nearestGridPoint[1] + j] += visibility[3] * kernelValue;
				}
			}
		}
	}

	/*
	 * Take the u,v location and using the round function returns its grid location.
	 */
	private static double[] UVtoGrid(double u, double v)
	{
		double[] gridPoint = new double[2];

		// From data
		double cellSize = 0.00000484813681109536;
		double UVScale = gridSize * cellSize;

		double wavelengthsToMeters = 300000000.0 / 299792458.0; // frequencyHZ / speed of light

		gridPoint[0] = ((-u * wavelengthsToMeters) * UVScale) + gridSize / 2;
		gridPoint[1] = ((v * wavelengthsToMeters) * UVScale) + gridSize / 2;

		return gridPoint;
	}

	private static int inKernel(double d)
	{
		return (int) ((d / 4) * 14);
	}

}
