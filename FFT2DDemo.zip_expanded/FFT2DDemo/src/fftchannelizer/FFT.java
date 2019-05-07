/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fftchannelizer;

import fftchannelizer.windows.Window;
import java.util.Arrays;

/**
 *
 * @author aensor
 */
public abstract class FFT
{

    protected int numChannels; // number of channels this FFT produces
    protected Window window; // an optional window applied before FFT

    public FFT(int numChannels)
    {
        this(numChannels, null);
    }

    public FFT(int numChannels, Window window)
    {
        this.numChannels = numChannels;
        this.window = window;
    }
    
    public int getNumChannels()
    {
        return numChannels;
    }
    
    public Window getWindow()
    {
        return window;
    }

    // perform an FFT without modifying original array nor its complex entries
    public abstract Complex[] transform(Complex[] original);
}
