/*
 * Class that implements a 2D FTT using Vector Radix two operations
 */
package fft2d;

import fftchannelizer.Complex;
import fftchannelizer.ComplexDouble;

/**
 *
 * @author aensor
 */
public class RadixTwo2DVectorRadix extends FFT2D
{
    private int[] bitReversedIndices;
    
    public RadixTwo2DVectorRadix(int numChannels)
    {
        super(numChannels);
        this.bitReversedIndices = calcBitReversedIndices(numChannels);
    }
    
    private int[] calcBitReversedIndices(int n)
    {
        int[] indices = new int[n];
        for (int k = 0; k < n; k++)
        {
            // calculate index r to which k will be moved
            int kPrime = k;
            int r = 0;
            for (int j = 1; j < n; j *= 2)
            {
                int b = kPrime & 1;
                r = (r << 1) + b;
                kPrime = (kPrime >>> 1);
            }
            indices[k] = r;
        }
        return indices;
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
        // copy original to a working array and bit reverse its entries
        Complex[][] working = new Complex[n][n];
        for (int row=0; row<n; row++)
        {
            for (int col=0; col<n; col++)
            {
                working[row][col] = original[bitReversedIndices[row]][bitReversedIndices[col]];
            }
        }
//        // precalculate omega powers
//        Complex[] omegaPowers = new Complex[n];
//        Complex omega = original[0][0].getRootOfUnity(n);
//        omegaPowers[0] = original[0][0].getUnit();
//        for (int i=1; i<n; i++)
//        {
//            omegaPowers[i] = omegaPowers[i-1].multiply(omega);
//        }
        // use butterfly operations on working to find the DFT of original
        for (int m = 2; m <= n; m *= 2)
        {
            Complex omegaM = original[0][0].getRootOfUnity(m);
            for (int k = 0; k < n; k += m)
            {
                for (int l = 0; l < n; l += m)
                {
                    Complex x = original[0][0].getUnit();
                    for (int i = 0; i < m / 2; i++)
                    {
                        Complex y = original[0][0].getUnit();
                        for (int j = 0; j < m / 2; j++)
                        {
                            // perform 2D butterfly operation in-place at (k+j, l+j)
                            Complex in00 = working[k+i][l+j];
                            Complex in01 = working[k+i][l+j+m/2].multiply(y);
                            Complex in10 = working[k+i+m/2][l+j].multiply(x);
                            Complex in11 = working[k+i+m/2][l+j+m/2].multiply(x).multiplyInPlace(y);
                        
                            Complex temp00 = in00.add(in01);
                            Complex temp01 = in00.subtract(in01);
                            Complex temp10 = in10.add(in11);
                            Complex temp11 = in10.subtract(in11);
                        
                            working[k+i][l+j] = temp00.add(temp10);
                            working[k+i][l+j+m/2] = temp01.add(temp11);
                            working[k+i+m/2][l+j] = temp00.subtract(temp10);
                            working[k+i+m/2][l+j+m/2] = temp01.subtract(temp11);
                            y.multiplyInPlace(omegaM);
                        }
                        x.multiplyInPlace(omegaM);
                    }
                }
            }
        }
        return working;
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
        System.out.println("Starting RadixTwo2DVectorRadix");
        int numChannels = 8;
        RadixTwo2DVectorRadix fft = new RadixTwo2DVectorRadix(numChannels);
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
        System.out.println("Ending RadixTwo2DVectorRadix"); 
    }
    
}
