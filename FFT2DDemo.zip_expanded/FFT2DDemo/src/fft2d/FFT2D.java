/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fft2d;

import fftchannelizer.Complex;
import fftchannelizer.windows.Window;

/**
 *
 * @author aensor
 */
public abstract class FFT2D
{
    protected int numRows, numColumns; // number of channels this FFT produces
    protected Window window; // an optional window applied before FFT

    public FFT2D(int numChannels)
    {
        this(numChannels, numChannels, null);
    }

    public FFT2D(int numRows, int numColumns)
    {
        this(numRows, numColumns, null);
    }

    public FFT2D(int numRows, int numColumns, Window window)
    {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.window = window;
    }
    
    public int getNumRows()
    {
        return numRows;
    }
    
    public int getNumColumns()
    {
        return numColumns;
    }
    
    public Window getWindow()
    {
        return window;
    }

    // perform an FFT without modifying original array nor its complex entries
    public abstract Complex[][] transform(Complex[][] original);
    
}
