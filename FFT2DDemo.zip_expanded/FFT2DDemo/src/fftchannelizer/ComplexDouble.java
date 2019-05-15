/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fftchannelizer;

/**
 *
 * @author aensor
 */
public final class ComplexDouble extends Complex
{

    private double real, imag;
    public static ComplexDouble COMPLEX_TYPE = new ComplexDouble();

    public ComplexDouble()
    {
        this(0, 0);
    }

    public ComplexDouble(double real, double imag)
    {
        this.real = real;
        this.imag = imag;
    }

    public Complex add(Complex other)
    {
        return new ComplexDouble(this.real + other.getReal(), this.imag + other.getImaginary());
    }

    public Complex addInPlace(Complex other)
    {
        this.real += other.getReal();
        this.imag += other.getImaginary();
        return this;
    }

    public Complex subtract(Complex other)
    {
        return new ComplexDouble(this.real - other.getReal(), this.imag - other.getImaginary());
    }

    public Complex subtractInPlace(Complex other)
    {
        this.real -= other.getReal();
        this.imag -= other.getImaginary();
        return this;
    }

    public Complex multiply(Complex other)
    {
        return new ComplexDouble(this.real * other.getReal() - this.imag * other.getImaginary(), this.real * other.getImaginary() + this.imag * other.getReal());
    }

    public Complex multiply(double scalar)
    {
        return new ComplexDouble(this.real * scalar, this.imag * scalar);
    }

    public Complex multiplyInPlace(Complex other)
    {
        double newReal = this.real * other.getReal() - this.imag * other.getImaginary();
        double newImag = this.real * other.getImaginary() + this.imag * other.getReal();
        this.real = newReal;
        this.imag = newImag;
        return this;
    }

    public Complex multiplyInPlace(double scalar)
    {
        this.real *= scalar;
        this.imag *= scalar;
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

    public Complex getUnit()
    {
        return new ComplexDouble(1, 0);
    }

    public Complex getRootOfUnity(double order)
    {
        double angle = Math.PI * 2.0 / order;
        return new ComplexDouble(Math.cos(angle), Math.sin(angle));
    }

    public Complex getCopy()
    {
        return new ComplexDouble(this.real, this.imag);
    }

    public Complex getNew(double real, double imag)
    {
        return new ComplexDouble(real, imag);
    }

    public static void main(String[] args)
    {
        ComplexDouble x = new ComplexDouble(2, 3);
        ComplexDouble y = new ComplexDouble(4, 5);
        System.out.println("" + x + " plus " + y + " equals " + x.add(y));
        System.out.println("" + x + " times " + y + " equals " + x.multiply(y));
    }
}
