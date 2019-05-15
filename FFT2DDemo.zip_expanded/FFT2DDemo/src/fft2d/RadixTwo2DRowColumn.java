/*
 * Class that implements a 2D FTT using Row-Column Radix two operations
 */
package fft2d;

import fftchannelizer.Complex;
import fftchannelizer.ComplexDouble;
import fftchannelizer.RadixTwoFFT;

/**
 *
 * @author aensor
 */
public class RadixTwo2DRowColumn extends FFT2D
{
    public RadixTwo2DRowColumn(int numChannels)
    {
        super(numChannels);
    }

    // perform an FFT without modifying original array nor its complex entries
    public Complex[][] transform(Complex[][] original)
    {
        int n = original.length;
        if (n != getNumRows())
        {
            throw new IllegalArgumentException("Invalid FFT size: " + n);
        }
        // Note: no windowing considered in this code
        if (window != null)
            throw new IllegalArgumentException("Sorry code doesn't yet support windowing");
        // copy original to a working array
        Complex[][] transformed = new Complex[n][];
        RadixTwoFFT fft1D = new RadixTwoFFT(n);
        // transform along each row
        for (int row=0; row<n; row++)
        {
            if (original[row].length != n)
            {
                throw new IllegalArgumentException("Not square FFT size in row: " + row);
            }
            transformed[row] = fft1D.transform(original[row]);
        }
        // transform down each column
        for (int col=0; col<n; col++)
        {
            // create a temporary array for the values down the column
            Complex[] columnValues = new Complex[n];
            for (int row=0; row<n; row++)
            {
                columnValues[row] = transformed[row][col];
            }
            Complex[] transformedColumn = fft1D.transform(columnValues);
            // copy the transformed values back into the column
            for (int row=0; row<n; row++)
            {
                transformed[row][col] = transformedColumn[row];
            }
        }
        return transformed;
    }
    
    public static void output2DArray(Complex[][] values)
    {
        for (int row=0; row<values.length; row++)
        {
            for (int col=0; col<values[row].length; col++)
            {
                System.out.format("%s; ", values[row][col]);
            }
            System.out.format("%n");
        }    
    }
    
    public static void main(String[] args)
    {
        System.out.println("Starting RadixTwo2DRowColumn");
        int numChannels = 8;
        RadixTwo2DRowColumn fft = new RadixTwo2DRowColumn(numChannels);
        Complex[][] original = new Complex[numChannels][numChannels];
        for (int row=0; row<numChannels; row++)
        {
            for (int col=0; col<numChannels; col++)
            {
                original[row][col] = new ComplexDouble(row, col);
//                        Math.cos(Math.PI*4*col/numChannels), Math.sin(Math.PI*4*col/numChannels));
            }
        }
        output2DArray(original);
        System.out.println();
        output2DArray(fft.transform(original));
        System.out.println("Ending RadixTwo2DRowColumn");   
    }
}
