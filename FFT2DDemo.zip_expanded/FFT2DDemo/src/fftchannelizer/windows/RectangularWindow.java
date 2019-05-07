/*
 * Class that represents a rectangular window that is 1 only between
 * the specified start and end (inclusive) entries
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class RectangularWindow extends Window
{

    private int start, end;

    public RectangularWindow(int size, int start, int end)
    {
        super(size);
        this.start = start;
        this.end = end;
    }

    protected double calculateEntry(double i, int windowWidth)
    {
        double scale = 1.0 * windowWidth / size;
        if (i >= start * scale && i <= end * scale)
        {
            return 1.0;
        } else
        {
            return 0.0;
        }
    }
}
