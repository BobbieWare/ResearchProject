/*
 * Class that represents a Bartlett window, which is a triangular window
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class BartlettWindow extends Window
{

    public BartlettWindow(int size)
    {
        super(size);
    }

    protected double calculateEntry(double i, int windowWidth)
    {
        double midPoint = (windowWidth - 1) / 2.0;
        return 1.0 - Math.abs(i - midPoint) * 2.0 / (windowWidth - 1);
    }
}
