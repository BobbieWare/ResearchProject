/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fftchannelizer;

/**
 *
 * @author aensor
 */
public final class ComplexFloat extends Complex
{

    private float real, imag;
    public static ComplexFloat COMPLEX_TYPE = new ComplexFloat();

    public ComplexFloat()
    {
        this(0, 0);
    }

    public ComplexFloat(float real, float imag)
    {
        this.real = real;
        this.imag = imag;
    }

    public ComplexFloat(double real, double imag)
    {
        this((float) real, (float) imag);
    }

    public Complex add(Complex other)
    {
        return new ComplexFloat(this.real + other.getReal(), this.imag + other.getImaginary());
    }

    public Complex addInPlace(Complex other)
    {
        this.real += (float) other.getReal();
        this.imag += (float) other.getImaginary();
        return this;
    }

    public Complex subtract(Complex other)
    {
        return new ComplexFloat(this.real - other.getReal(), this.imag - other.getImaginary());
    }

    public Complex subtractInPlace(Complex other)
    {
        this.real -= (float) other.getReal();
        this.imag -= (float) other.getImaginary();
        return this;
    }

    public Complex multiply(Complex other)
    {
        return new ComplexFloat(this.real * other.getReal() - this.imag * other.getImaginary(), this.real * other.getImaginary() + this.imag * other.getReal());
    }

    public Complex multiply(double scalar)
    {
        return new ComplexFloat(this.real * (float) scalar, this.imag * (float) scalar);
    }

    public Complex multiplyInPlace(Complex other)
    {
        float newReal = this.real * (float) other.getReal() - this.imag * (float) other.getImaginary();
        float newImag = this.real * (float) other.getImaginary() + this.imag * (float) other.getReal();
        this.real = newReal;
        this.imag = newImag;
        return this;
    }

    public Complex multiplyInPlace(double scalar)
    {
        this.real *= (float) scalar;
        this.imag *= (float) scalar;
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
        return new ComplexFloat(1, 0);
    }

    public Complex getRootOfUnity(double order)
    {
        double angle = Math.PI * 2.0 / order;
        return new ComplexFloat((float) Math.cos(angle), -(float) Math.sin(angle));
    }

    public Complex getCopy()
    {
        return new ComplexFloat(this.real, this.imag);
    }

    public Complex getNew(double real, double imag)
    {
        return new ComplexFloat((float) real, (float) imag);
    }

    public String toString()
    {
        if (imag>0)
        {   
            return real + "+" + imag + "i";
        }
        else if (getImaginary()<0)
        {
            return real + "-" + (-imag) + "i";
        }
        else
        {
            return real + "+0i";
        }
    }
    
    public static void main(String[] args)
    {
        ComplexFloat x = new ComplexFloat(2, 3);
        ComplexFloat y = new ComplexFloat(4, 5);
        System.out.println("" + x + " plus " + y + " equals " + x.add(y));
        System.out.println("" + x + " times " + y + " equals " + x.multiply(y));
    }
}
