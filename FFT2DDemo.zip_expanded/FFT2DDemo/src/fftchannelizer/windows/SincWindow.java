/*
 * Class that represents a sinc window that has a total of 2*scale zero crossings
 * Particularly used with PolyphaseFFT where scale = numTaps/2
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class SincWindow extends Window
{
    private double scale;
    
    public SincWindow(int size, double scale)
    {
        super(size);
        this.scale = scale;
    }
    
    protected double calculateEntry(double i, int windowWidth)
    {
        double midPoint = (windowWidth - 1) / 2.0;
        if (i == midPoint)
            return 1.0; // if odd size then include y intercept of sinc function
        else
        {
            if (i > midPoint)
            {
                i = windowWidth-1-i;
            }
            double x = 2 * Math.PI * (i - midPoint) / (windowWidth - 1) * scale;
            return Math.sin(x) / x;
        }
    }
}
