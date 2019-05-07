/*
 * Class that represents a Kaiser window with configurable alpha that specifies
 * primary beam half-width
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class KaiserWindow extends Window
{

    private double piAlpha;
    private double besselPiAlpha;
    private final double ACCURACY = 1E-12;

    public KaiserWindow(int size, double alpha)
    {
        super(size);
        this.piAlpha = Math.PI * alpha;
        this.besselPiAlpha = getZeroOrderModifiedBessel(piAlpha);
    }

    protected double calculateEntry(double i, int windowWidth)
    {
        double innerTerm = 2.0 * i / (windowWidth - 1) - 1;
        double x = piAlpha * Math.sqrt(1 - innerTerm * innerTerm);
        return getZeroOrderModifiedBessel(x) / besselPiAlpha;
    }

    private double getZeroOrderModifiedBessel(double x)
    {
        double sum = 0;
        double term = 1;
        int m = 0;
        do
        {
            // calculate term carefully to avoid overflows
            term = 1.0;
            for (int i = 1; i <= m; i++)
            {
                term *= x / (2.0 * i);
            }
            term *= term; // square term
            sum += term;
            m++;
        } while (Math.abs(term / sum) > ACCURACY);
        return sum;
    }

    public static void main(String[] args)
    {
        int windowWidth = 512 * 12;
        for (int resolution = 0; resolution < 10; resolution++)
        {
            Window dcWindow = new KaiserWindow(windowWidth, 5);
            double sum = 0;
            for (int i = 0; i < windowWidth; i++)
            {
//            System.out.println(i+": "+dcWindow.getEntry(i));
                sum += dcWindow.getEntry(i);
            }
        }
    }
}
