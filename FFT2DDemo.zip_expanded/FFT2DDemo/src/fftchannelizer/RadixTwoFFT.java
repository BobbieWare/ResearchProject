/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fftchannelizer;

import fftchannelizer.windows.Window;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author aensor
 */
public class RadixTwoFFT extends FFT
{
    
    public RadixTwoFFT(int numChannels)
    {
        this(numChannels, null);
    }

    public RadixTwoFFT(int numChannels, Window window)
    {
        super(numChannels, window);
    }

    // perform an FFT without modifying original array nor its complex entries
    public Complex[] transform(Complex[] original)
    {
        return ditTransform(original);
    }

    // perform a decimation in time FFT without modifying original array
    // nor the complex entries in it
    public Complex[] ditTransform(Complex[] original)
    {
        int n = original.length;
        if (n != numChannels)
        {
            throw new IllegalArgumentException("Invalid FFT size: " + n);
        }
        // copy original to a working array
        Complex[] working = Arrays.copyOf(original, n);
        if (window != null)
        {
            working = window.applyWindow(working);
        }
        Complex[] channels = bitReverseCopy(working);
        // use butterfly operations on working to find the DFT of original
        for (int m = 2; m <= n; m *= 2)
        {
            Complex omega = original[0].getRootOfUnity(m);
            for (int k = 0; k < n; k += m)
            {
                Complex x = original[0].getUnit();
                for (int j = 0; j < m / 2; j++)
                {
                    // perform butterfly operation in-place at k+j
                    Complex t = x.multiply(channels[k + j + m / 2]);
                    Complex u = channels[k + j];
                    channels[k + j] = u.add(t);
                    channels[k + j + m / 2] = u.subtract(t);
                    x.multiplyInPlace(omega);
                }
            }
        }
        return channels;
    }

    // perform a decimation in frequency FFT without modifying original array
    // nor the complex entries in it
    public Complex[] difTransform(Complex[] original)
    {
        int n = original.length;
        if (n != numChannels)
        {
            throw new IllegalArgumentException("Invalid FFT size: " + n);
        }
        // copy original to a working array
        Complex[] working = Arrays.copyOf(original, n);
        if (window != null)
        {
            working = window.applyWindow(working);
        }
        // use butterfly operations on working to find the DFT of original
        for (int m = n; m >= 2; m /= 2)
        {
            Complex omega = original[0].getRootOfUnity(m);
            for (int k = 0; k < n; k += m)
            {
                Complex x = original[0].getUnit();
                for (int j = 0; j < m / 2; j++)
                {
                    // perform butterfly operation in-place at k+j
                    Complex u = working[k + j];
                    working[k + j] = u.add(working[k + j + m / 2]);
                    working[k + j + m / 2] = u.subtract(working[k + j + m / 2]);
                    working[k + j + m / 2].multiplyInPlace(x);
                    x.multiplyInPlace(omega);
                }
            }
        }
        Complex[] channels = bitReverseCopy(working);
        return channels;
    }

    protected Complex[] bitReverseCopy(Complex[] original)
    {
        int n = original.length;
        Complex[] bitReversed = new Complex[n];
        for (int k = 0; k < n; k++)
        {
            // calculate index to which original[k] will be moved
            int kPrime = k;
            int r = 0;
            for (int j = 1; j < n; j *= 2)
            {
                int b = kPrime & 1;
                r = (r << 1) + b;
                kPrime = (kPrime >>> 1);
            }
            bitReversed[r] = original[k];
        }
        return bitReversed;
    }

    public static void main(String[] args)
    {
//        RadixTwoFFT fftSmall = new RadixTwoFFT(4);
//        ComplexFloat[] test =
//        {
//            new ComplexFloat(7, 0), new ComplexFloat(-3, 0),
//            new ComplexFloat(4, 0), new ComplexFloat(-1, 0)
//        };
//        System.out.println(ComplexFloat.toString(test));
//        Complex[] ditOutput = fftSmall.ditTransform(test);
//        System.out.println(Complex.toString(ditOutput));
//        Complex[] difOutput = fftSmall.ditTransform(test);
//        System.out.println(ComplexFloat.toString(difOutput));

        int numChannels = 64;
        FFT fft = new RadixTwoFFT(numChannels);
//        // create a wideband signal comprised of a superposition of complex sinusoidals
//        WidebandSignal signal = new PolytoneSignal(ComplexDouble.COMPLEX_TYPE,
//                new double[]{
//                    0, 2, 4, 5, 6, 8
//                }, new double[]
//                {
//                    0.1, 0.4, 1.6, 5, 20, 40
//                }, null);
//        Complex[] wideband = signal.getPureSignals(0, 1, numChannels);
//
//        // perform single stage FFT using decimation in time
//        Complex[] ditChannels = fft.transform(wideband);
//        // perform single stage FFT using decimation in frequency
//        Complex[] difChannels = ((RadixTwoFFT)fft).difTransform(wideband);
//
//        System.out.println("Wideband = " + Complex.toString(wideband));
//        System.out.println("DIT Channels =    " + Complex.toString(ditChannels));
//        System.out.println("DIF Channels =    " + Complex.toString(difChannels));
//
//        // compare ffts
//        double sumDifferences = 0;
//        for (int i = 0; i < wideband.length; i++)
//        {
//            sumDifferences += ditChannels[i].subtract(difChannels[i]).modulus();
//        }
//        System.out.println("Difference is " + sumDifferences);

        // create a wideband signal comprised of random float values
        Complex[] widebandFloat = new Complex[numChannels];
        Complex[] widebandDouble = new Complex[numChannels];
        Random generator = new Random();
        for (int i=0; i<numChannels; i++)
        {
            widebandFloat[i] = new ComplexFloat(generator.nextFloat(), generator.nextFloat());
            widebandDouble[i] = new ComplexDouble(widebandFloat[i].getReal(), widebandFloat[i].getImaginary());
        }

        // perform single stage FFT on float and double wideband signals
        Complex[] channelsFloat = fft.transform(widebandFloat);
        // perform single stage FFT using decimation in frequency
        Complex[] channelsDouble = fft.transform(widebandDouble);
        Complex[] channelsDoubleDIF = ((RadixTwoFFT)fft).difTransform(widebandDouble);

        System.out.println("Wideband = " + Complex.toString(widebandFloat));
        System.out.println("Float Channels =      " + Complex.toString(channelsFloat));
        System.out.println("Double Channels =     " + Complex.toString(channelsDouble));
        System.out.println("Double DIF Channels = " + Complex.toString(channelsDoubleDIF));

        // compare ffts
        double sumDifferences = 0;
        double sumDifferencesDIF = 0;
        for (int i = 0; i < widebandFloat.length; i++)
        {
            sumDifferences += channelsFloat[i].subtract(channelsDouble[i]).modulus();
            sumDifferencesDIF += channelsFloat[i].subtract(channelsDoubleDIF[i]).modulus();
        }
        System.out.println("Difference Float-Double is    " + sumDifferences);
        System.out.println("Difference Float-DoubleDIF is " + sumDifferencesDIF);
    }
}
