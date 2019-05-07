/*
 * Abstract class that represents a DFT windowing function
 */
package fftchannelizer.windows;

import fftchannelizer.Complex;
import fftchannelizer.ComplexFloat;

/**
 *
 * @author aensor
 */
public abstract class Window
{

    protected int size;
    protected int offset; // offset applied to any entry i when using large window with width>size
    protected int windowWidth;
    protected double[] values;
    protected boolean precalculated; // true iff values has been precalculated

    protected Window(int size)
    {
        this.size = size;
        this.offset = 0;
        this.windowWidth = size;
        this.values = new double[size];
        this.precalculated = false;
    }
    
    public int getLargeWindowOffset()
    {
        return offset;
    }

    public int getLargeWindowWidth()
    {
        return windowWidth;
    }

    public void setLargeWindow(int offset, int windowWidth)
    {
        this.offset = offset;
        this.windowWidth = windowWidth;
        this.precalculated = false; // change in offset requires entry recalculation
    }

    // returns the entry at index+offset in the window
    public final double getEntry(int index)
    {
        if (index < 0 || index >= this.size)
        {
            throw new IllegalArgumentException("Invalid window index "
                    + index + " in window with size " + size);
        }
        precalculateEntries();
        return values[index];
    }

    // convenience method that precalculates entries to improve performance
    // particularly for computational expensive windows such as DolphChebyshev
    // returns whether the entries needed to be precalculated
    protected boolean precalculateEntries()
    {
        if (this.precalculated)
            return false;
        double scale = 1.0*windowWidth/size;
        for (int index = 0; index < this.size; index++)
        {
            this.values[index] = calculateEntry(index*scale + offset, windowWidth);
        }
        this.precalculated = true;
        return true;
    }

    // note for performance this method should only be called via precalculateEntries
    protected abstract double calculateEntry(double i, int windowWidth);

    public Complex[] applyWindow(Complex[] data)
    {
        Complex[] windowed = new Complex[data.length];
        for (int i = 0; i < data.length && i < this.size; i++)
        {
            windowed[i] = data[i].multiply(getEntry(i));
        }
        return windowed;
    }

    public void applyWindowInPlace(Complex[] data)
    {
        for (int i = 0; i < data.length && i < this.size; i++)
        {
            data[i].multiplyInPlace(getEntry(i));
        }
    }

    public int getSize()
    {
        return size;
    }

    public static void main(String[] args)
    {
        Complex[] input = new Complex[64];
        for (int i = 0; i < input.length; i++)
        {
            input[i] = new ComplexFloat(1.0, 0.0);
        }
        System.out.println("Input is: " + Complex.toString(input));
        Window window = new DolphChebyshevWindow(input.length, 5);
        window.applyWindowInPlace(input);
        System.out.println("Output is:" + Complex.toString(input));
    }
}
