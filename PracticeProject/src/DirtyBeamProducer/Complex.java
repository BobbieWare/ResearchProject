package DirtyBeamProducer;

/*
 *  Data type for complex numbers
 */
public class Complex
{
	private double real;
	private double imag;

    public Complex()
    {
        this(0, 0);
    }

    public Complex(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }

    public Complex add(Complex other)
    {
        return new Complex(this.real + other.getReal(), this.imag + other.getImaginary());
    }
    
    public Complex addInPlace(Complex other)
    {
        this.real += other.getReal();
        this.imag += other.getImaginary();
        return this;
    }

    public Complex subtract(Complex other)
    {
        return new Complex(this.real - other.getReal(), this.imag - other.getImaginary());
    }

    public Complex multiply(Complex other)
    {
        return new Complex(this.real * other.getReal() - this.imag * other.getImaginary(), this.real * other.getImaginary() + this.imag * other.getReal());
    }

    public Complex multiplyInPlace(Complex other)
    {
        double newReal = this.real * other.getReal() - this.imag * other.getImaginary();
        double newImag = this.real * other.getImaginary() + this.imag * other.getReal();
        this.real = newReal;
        this.imag = newImag;
        return this;
    }

    public double getReal()
    {
        return this.real;
    }

    public double getImaginary()
    {
        return this.imag;
    }
    
    public Complex getConjugate()
    {
    	double newReal = this.real;
    	double newImag = -this.imag;
    	return new Complex(newReal, newImag);
    }
    
    public Complex abs()
    {
    	return new Complex(Math.abs(this.real), Math.abs(this.imag));
    }
}
