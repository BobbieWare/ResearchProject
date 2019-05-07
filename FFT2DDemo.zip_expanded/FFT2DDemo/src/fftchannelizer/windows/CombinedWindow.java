/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class CombinedWindow extends Window
{

    private Window windowA, windowB;

    public CombinedWindow(Window windowA, Window windowB)
    {
        super(windowA.getSize());
        if (windowA.getSize() != windowB.getSize())
            throw new IllegalArgumentException("Combined windows must have same size");
        this.windowA = windowA;
        this.windowB = windowB;
    }

    protected boolean precalculateEntries()
    {
        if (windowA.precalculateEntries() || windowB.precalculateEntries())
            this.precalculated = false; // force a recalculation
        return super.precalculateEntries();
    }
    
    protected double calculateEntry(double i, int windowWidth)
    {
        // presumes entries in both windows have already been precalculated
        double invScale = 1.0*size/windowWidth;
        int index = (int)Math.round((i-this.getLargeWindowOffset())*invScale);
        return windowA.values[index] * windowB.values[index];
    }

    public String toString()
    {
        return "Combined window of " + windowA + " with " + windowB;
    }
}
