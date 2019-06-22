/**
 * This class is responsible for containing a complex number
 * It also has all the necessary operations required for 
 * the processes
 *
 * @author Bobbie Ware
 */
package Deconvolution;

public class Complex
{
	// Complex number consists of a real number and a imaginary number
	private double real;
	private double imag;

    public Complex()
    {
        this(0, 0);
    }

    /*
     * Creates a new object with the given values
     */
    public Complex(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }
    
    /*
     * Returns a new Complex object of the sum of two objects
     */
    public Complex add(Complex other)
    {
        return new Complex(this.real + other.getReal(), this.imag + other.getImaginary());
    }
    
    /*
     * Adds the value of another Complex object to this one
     */
    public Complex addInPlace(Complex other)
    {
        this.real += other.getReal();
        this.imag += other.getImaginary();
        return this;
    }

    /*
     * Returns a new Complex object of the difference of two objects
     */
    public Complex subtract(Complex other)
    {
        return new Complex(this.real - other.getReal(), this.imag - other.getImaginary());
    }
    
    /*
     * Subtracts the value of another Complex object to this one
     */
    public Complex subtractInPlace(Complex other)
    {
    	this.real -= other.getReal();
        this.imag -= other.getImaginary();
        return this;
    }

    /*
     * Returns a new Complex object of the product of two of objects
     */
    public Complex multiply(Complex other)
    {
        return new Complex(this.real * other.getReal() - this.imag * other.getImaginary(), this.real * other.getImaginary() + this.imag * other.getReal());
    }

    /*
     * Multiplies the value of another Complex object to this one
     */
    public Complex multiplyInPlace(Complex other)
    {
        double newReal = this.real * other.getReal() - this.imag * other.getImaginary();
        double newImag = this.real * other.getImaginary() + this.imag * other.getReal();
        this.real = newReal;
        this.imag = newImag;
        return this;
    }

    /*
     * Returns the real part of Complex number
     */
    public double getReal()
    {
        return this.real;
    }

    /*
     * Returns the imaginary part of Complex number
     */
    public double getImaginary()
    {
        return this.imag;
    }

    
    
}
