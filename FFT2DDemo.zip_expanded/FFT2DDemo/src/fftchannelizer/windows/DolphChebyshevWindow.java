/*
 * Class that represents a Dolph-Chebyshev window with side lobes at -20*alpha dB
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class DolphChebyshevWindow extends Window
{

    private double tenPowerAlpha;
    private int presumedWindowWidth;
    private double x0, midPoint, maxValue;

    public DolphChebyshevWindow(int size, double alpha)
    {
        super(size);
        this.tenPowerAlpha = Math.pow(10, alpha);
        this.presumedWindowWidth = size;
        calculateUtilityValues(presumedWindowWidth);
    }

    // note there are some inaccuracies with this method when size>=64 at either end
    protected double calculateEntry(double i, int windowWidth)
    {
        if (windowWidth != this.presumedWindowWidth)
        {
            calculateUtilityValues(windowWidth);
        }
        return getDolphChebyshevWindowEntry(i, windowWidth);
    }
    
    // utility method that precalculates some values required for computing
    // window
    private void calculateUtilityValues(int windowWidth)
    {
        this.presumedWindowWidth = windowWidth;
        this.x0 = Math.cosh(getCoshInverse(this.tenPowerAlpha) / (windowWidth - 1));
        this.midPoint = (windowWidth - 1) / 2.0;
        // calculate maximum value which is at the midPoint
        maxValue = 1.0;
        maxValue = getDolphChebyshevWindowEntry(midPoint, windowWidth);        
    }
    
    private double getDolphChebyshevWindowEntry(double i, int windowWidth)
    {
        // check whether fields need to be recalculated
        if (i > midPoint)
        {
            i = windowWidth - 1 - i;
        }
        double sum = 0;
        // accumulate terms from smaller to larger
        for (int j = (int)midPoint; j >= 1; j--)
        {
            double x = x0 * Math.cos(Math.PI * j / windowWidth);
            double term = getChebyshevPolynomial(x, windowWidth - 1)
                    * Math.cos(2 * Math.PI * (i - midPoint) * j / windowWidth);
            sum += term;
        }
        sum *= 2;
        sum = (sum + tenPowerAlpha) / size;
        return sum / maxValue;

    }

    private static double getChebyshevPolynomial(double x, int n)
    {
        if (x == 0)
        {
            if (n % 4 == 0)
                return 1.0;
            else if (n % 4 == 2)
                return -1.0;
            else
                return 0.0;
        } else if (x == 1)
        {
            return 1.0;
        }
        else if (Math.abs(x) < 1.0)
        {
            return Math.cos(n * Math.acos(x));
        } else
        {
            return Math.cosh(n * getCoshInverse(x));
        }
    }

    private static double getCoshInverse(double x)
    {
        return Math.log(x + Math.sqrt(x * x - 1));
    }

    public static void main(String[] args)
    {
        int windowWidth = 64; //512;
        Window dcWindow = new DolphChebyshevWindow(windowWidth, 3);
        for (int i = 0; i < windowWidth; i++)
        {
            System.out.println(i + ": " + dcWindow.getEntry(i));
        }
    }
}
