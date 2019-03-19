package firstTryIFFT;

/*
 *  Data type for complex numbers
 */
public class Complex
{
	private double real; // real part
	private double imaginary; // imaginary part

	// create a new object using both parts
	public Complex(double real, double imaginary)
	{
		this.real = real;
		this.imaginary = imaginary;
	}

	// returns real part of the complex number
	public double re()
	{
		return real;
	}

	// returns imaginary part of the complex number
	public double im()
	{
		return imaginary;
	}

	// gives a new Complex object of the sum of this and b
	public Complex add(Complex b)
	{
		Complex a = this;
		double real = a.real + b.real;
		double imag = a.imaginary + b.imaginary;
		return new Complex(real, imag);
	}

	// gives a new Complex object of the difference of this and b
	public Complex minus(Complex b)
	{
		Complex a = this;
		double real = a.real - b.real;
		double imag = a.imaginary - b.imaginary;
		return new Complex(real, imag);
	}

	// gives a new Complex object of the product of this and b
	public Complex times(Complex b)
	{
		Complex a = this;
		double real = a.real * b.real - a.imaginary * b.imaginary;
		double imag = a.real * b.imaginary + a.imaginary * b.real;
		return new Complex(real, imag);
	}
}
