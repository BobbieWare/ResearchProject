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
        int numChannels = 64;
        RadixTwo2DRowColumn fftRC = new RadixTwo2DRowColumn(numChannels);
        RadixTwo2DVectorRadix fftVR = new RadixTwo2DVectorRadix(numChannels);
        Complex[][] original = new Complex[numChannels][numChannels];
        Random generator = new Random();
        for (int row=0; row<numChannels; row++)
        {
            for (int col=0; col<numChannels; col++)
            {
                original[row][col] = new ComplexDouble(generator.nextFloat(), generator.nextFloat());
//                        Math.cos(Math.PI*4*col/numChannels), Math.sin(Math.PI*4*col/numChannels));
            }
        }
        Complex[][] rcTransformed = fftRC.transform(original);
        Complex[][] vrTransformed = fftVR.transform(original);
        output2DArray(original);
        System.out.println();
        output2DArray(rcTransformed);
        System.out.println();
        output2DArray(vrTransformed);
        System.out.println();
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
