/*
 * Class that represents a Blackman window with alpha = 0.16
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class BlackmanWindow extends Window
{
    private final double ALPHA = 0.16;
    
    public BlackmanWindow(int size)
    {
        super(size);
    }
    
    protected double calculateEntry(double i, int windowWidth)
    {
        return (1 - ALPHA) / 2 - 0.5 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
                    + ALPHA / 2 * Math.cos(4 * Math.PI * i / (windowWidth - 1));
//            return 0.42659 - 0.49656*Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                    + 0.076849*Math.cos(4 * Math.PI * i / (windowWidth - 1)); // exact Blackman
    }
}
