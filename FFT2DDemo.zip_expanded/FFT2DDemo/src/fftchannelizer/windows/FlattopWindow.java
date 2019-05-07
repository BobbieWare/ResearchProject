/*
 * Class that represents a Flattop window
 */
package fftchannelizer.windows;

/**
 *
 * @author aensor
 */
public class FlattopWindow extends Window
{

    public enum NAME
    {

        SR785, HFT70, HFT248D, FTHP, FD3FTC1, FD4FTC1, FD5FTC1, FD5FTC4, MS3FTC1, MS3FTC2, MS3FTC3, MS4FTC1, MS4FTC4, MS5FTC1, MS5FTC4
    }
    private double[] coefficients;
    private NAME name;

    public FlattopWindow(int size, NAME name)
    {
        super(size);
        this.name = name;
        switch (name)
        {
            case SR785:
                coefficients = new double[]
                {
                    1.0, -1.93, 1.29, -0.388, 0.028
                };
                break;
            case HFT70:
                coefficients = new double[]
                {
                    1.0, -1.90796, 1.07349, -0.18199
                };
                break;
            case HFT248D:
                coefficients = new double[]
                {
                    1.0, -1.985844164102, 1.791176438506,
                    -1.282075284005, 0.667777530266,
                    -0.240160796576, 0.056656381764,
                    -0.008134974479, 0.000624544650,
                    -0.000019808998, 0.000000132974
                };
                break;
            case FTHP:
                coefficients = new double[]
                {
                    1.0, -1.912510941, 1.079173272, -0.1832630879
                };
                break;
            case FD3FTC1: // also called SFT3F Salvatore flat-top window
                coefficients = new double[]
                {
                    0.26526, -0.5, 0.23474, -0.0, 0.0
                };
                break;
            case FD4FTC1: // also called SFT4F Salvatore flat-top window
                coefficients = new double[]
                {
                    0.21706, -0.42103, 0.28294, -0.07897, 0.0
                };
                break;
            case FD5FTC1:
                coefficients = new double[]
                {
                    0.1881, -0.36923, 0.28702, -0.13077, 0.02488
                };
                break;
            case FD5FTC4:
                coefficients = new double[]
                {
                    0.189449, -0.370309, 0.285941, -0.129691, 0.024610
                };
                break;
            case MS3FTC1:
                coefficients = new double[]
                {
                    0.28235, -0.52105, 0.19660, -0.0, 0.0
                };
                break;
            case MS3FTC2:
                coefficients = new double[]
                {
                    0.285848, -0.520456, 0.193696, -0.0, 0.0
                };
                break;
            case MS3FTC3:
                coefficients = new double[]
                {
                    0.28653, -0.5204, 0.19307, -0.0, 0.0
                };
                break;
            case MS4FTC1:
                coefficients = new double[]
                {
                    0.241906, -0.460841, 0.255381, -0.041872, 0.0
                };
                break;
            case MS4FTC4:
                coefficients = new double[]
                {
                    0.244823, -0.461511, 0.252535, -0.041131, 0.0
                };
                break;
            case MS5FTC1:
                coefficients = new double[]
                {
                    0.209671, -0.407331, 0.281225, -0.092669, 0.009104
                };
                break;
            case MS5FTC4:
                coefficients = new double[]
                {
                    0.211598, -0.408422, 0.279432, -0.091578, 0.008970
                };
                break;
            default:
                coefficients = new double[]
                {
                    1.0, 0.0, 0.0, 0.0, 0.0
                };
                break;
        }
    }

    protected double calculateEntry(double i, int windowWidth)
    {
//        if (name == NAME.HFT248D)
//        {
//            double sum = coefficients[0];
//            double term = 2 * Math.PI / (windowWidth - 1);
//            for (int freq = 1; freq < coefficients.length; freq++)
//            {
//                sum += coefficients[freq] * Math.cos(term * freq * i);
//            }
//            return sum;
//
//        }
        double sum = coefficients[0];
        double term = 2 * Math.PI / (windowWidth - 1);
        for (int freq = 1; freq < coefficients.length; freq++)
        {
            sum += coefficients[freq] * Math.cos(term * freq * i);
        }
        return sum;
//        return 1 - 1.93 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 1.29 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.388 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.028 * Math.cos(8 * Math.PI * i / (windowWidth - 1));

        // Flat Top from wikipedia Window Function
        // -3dB at 1.85, -60dB at 4.5 channels (ratio=2.43)
//        return 1 - 1.93 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 1.29 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.388 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.028 * Math.cos(8 * Math.PI * i / (windowWidth - 1));

        // FD-3FT C=1 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.575, -60dB at 8.5 channels, first sidelobe -31.76dB and 3.4 channels
//        return 0.26526 - 0.5 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.23474 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.0 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.0 * Math.cos(8 * Math.PI * i / (windowWidth - 1));

        // FD-4FT C=1 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.88, -60dB at 5.65 channels, first sidelobe -45dB and 4.35 channels
//        return 0.21706 - 0.42103 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.28294 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.07897 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.0 * Math.cos(8 * Math.PI * i / (windowWidth - 1));

        // FD-5FT C=1 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 2.15, -60dB at 4.925 channels, first sidelobe -57dB and 5.35 channels
//        return 0.1881 - 0.36923 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.28702 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.13077 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.02488 * Math.cos(8 * Math.PI * i / (windowWidth - 1));
        // FD-5FT C=4 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 2.125, -60dB at 4.925 channels, first sidelobe -57.4dB and 5.35 channels
//        return 0.189449 - 0.370309 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.285941 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.129691 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.024610 * Math.cos(8 * Math.PI * i / (windowWidth - 1));

        // MS-3FT C=1 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.47, -60dB at 48 channels, first sidelobe -45.18dB at 3.25dB
//        return 0.28235 - 0.52105 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.19660 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.0 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.0 * Math.cos(8 * Math.PI * i / (windowWidth - 1));
        // MS-3FT C=2 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.43, -60dB at 44 channels, first sidelobe -45dB at 3.25 channels
//        return 0.285848 - 0.520456 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.193696 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.0 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.0 * Math.cos(8 * Math.PI * i / (windowWidth - 1));
        // MS-3FT C=3 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.45, -60dB at 44 channels, first sidelobe -45dB at 3.25 channels
//        return 0.28653 - 0.5204 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.19307 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.0 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.0 * Math.cos(8 * Math.PI * i / (windowWidth - 1));

        // MS-4FT C=1 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.675, -60dB at 3.88 channels (ratio=2.3)
//        return 0.241906 - 0.460841 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.255381 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.041872 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.0 * Math.cos(8 * Math.PI * i / (windowWidth - 1));
        // MS-4FT C=4 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.65, -60dB at 3.88 channels (ratio=2.35)
//        return 0.244823 - 0.461511 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.252535 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.041131 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.0 * Math.cos(8 * Math.PI * i / (windowWidth - 1));

        // MS-5FT C=1 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.925, -60dB at 4.7 channels (ratio=2.44)
//        return 0.209671 - 0.407331 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.281225 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.092669 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.009104 * Math.cos(8 * Math.PI * i / (windowWidth - 1));
        // MS-5FT C=4 from Extremely Flat-Top Windows for Harmonic Analysis
        // -3dB at 1.9, -60dB at 4.675 channels (ratio=2.46)
//        return 0.211598 - 0.408422 * Math.cos(2 * Math.PI * i / (windowWidth - 1))
//                + 0.279432 * Math.cos(4 * Math.PI * i / (windowWidth - 1))
//                - 0.091578 * Math.cos(6 * Math.PI * i / (windowWidth - 1))
//                + 0.008970 * Math.cos(8 * Math.PI * i / (windowWidth - 1));
    }
}
