/*
 * Test class for comparing row-column and vector radix approaches
 * for two dimensional FFTs
 */
package fft2ddemo;

import fft2d.RadixTwo2DRowColumn;
import fft2d.RadixTwo2DVectorRadix;
import static fft2d.RadixTwo2DVectorRadix.output2DArray;
import fftchannelizer.Complex;
import fftchannelizer.ComplexDouble;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author aensor
 */
public class FFT2DDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("Starting FFT2DDemo");
        int numChannels = 1024;
        RadixTwo2DRowColumn fftRC = new RadixTwo2DRowColumn(numChannels);
        RadixTwo2DVectorRadix fftVR = new RadixTwo2DVectorRadix(numChannels);
        Complex[][] original = GridderUsingComplex.grid();
        Complex[][] rcTransformed = fftRC.transform(original);
        Complex[][] vrTransformed = fftVR.transform(original);
        
        DecimalFormat df = new DecimalFormat("0.000000");
        try
		{
			PrintWriter pw = new PrintWriter("test3.csv");

			for (int i = 0; i < rcTransformed.length; i++)
			{
				pw.print(df.format(rcTransformed[i][0].getReal()));
				for (int j = 1; j < rcTransformed.length; j++)
				{
					pw.print("," + df.format(rcTransformed[i][j].getReal()));
				}
				pw.print("\n");
			}
			pw.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // calculate differences
        Complex[][] differences = new Complex[numChannels][numChannels];
        double sumDiff = 0;
        for (int row=0; row<numChannels; row++)
        {
            for (int col=0; col<numChannels; col++)
            {
                differences[row][col] = rcTransformed[row][col].subtract(vrTransformed[row][col]);
                sumDiff += Math.abs(differences[row][col].modulus());
            }
        }
        System.out.format("Absolute value of differences is %f%n", sumDiff);
        System.out.println("Ending FFT2DDemo");
    }
    
}
