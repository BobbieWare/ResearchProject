/*
 * Class that represents a Hanning window
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class HanningWindow extends Window
{
    public HanningWindow(int size)
    {
        super(size);
    }

    protected double calculateEntry(double i, int windowWidth)
    {
        return 0.5434783 - 0.4565217 * Math.cos(2 * Math.PI * i / (windowWidth - 1));
    }
}
