/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fftchannelizer;

/**
 *
 * @author aensor
 */
public abstract class Complex {
    
    public abstract Complex add(Complex other);
    
    public abstract Complex addInPlace(Complex other);
    
    public abstract Complex subtract(Complex other);
    
    public abstract Complex subtractInPlace(Complex other);
    
    public abstract Complex multiply(Complex other);
    
    public abstract Complex multiply(double scalar);
    
    public abstract Complex multiplyInPlace(Complex other);
    
    public abstract Complex multiplyInPlace(double scalar);
    
    public abstract double getReal();
    
    public abstract double getImaginary();
    
    public double modulus()
    {
        return Math.sqrt(getReal()*getReal()+getImaginary()*getImaginary());
    }
    
    public abstract Complex getUnit();
    
    public abstract Complex getRootOfUnity(double order);
    
    public abstract Complex getCopy();

    public abstract Complex getNew(double real, double imag);

    // multiplies entry i of array by factor^i
    public static void inPlaceMult(Complex[] array, Complex factor)
    {
        Complex mult = factor.getUnit(); // return 1+i0 in same format as factor
        for (int i=0; i<array.length; i++)
        {   
            array[i] = array[i].multiply(mult);
            mult = mult.multiplyInPlace(factor);
        }
    }
    
    public String toString()
    {
        if (getImaginary()>0)
        {   
            return getReal() + "+" + getImaginary() + "i";
        }
        else if (getImaginary()<0)
        {
            return getReal() + "-" + (-getImaginary()) + "i";
        }
        else
        {
            return getReal() + "+0i";
        }
    }
    
    public String toString(int accuracy)
    {
        double roundedReal = Math.round(getReal()*accuracy)/(1.0*accuracy);
        double roundedImag = Math.round(getImaginary()*accuracy)/(1.0*accuracy);
        if (roundedImag>0)
        {   
            return roundedReal + "+" + roundedImag + "i";
        }
        else if (roundedImag<0)
        {
            return roundedReal + "-" + (-roundedImag) + "i";
        }
        else
        {
            return roundedReal + "+0i";
        }
    }
    
    public static String toString(Complex[] values)
    {
        StringBuilder builder = new StringBuilder("{");
        for (int i=0; i<values.length; i++)
        {
            if (i>0)
            {
                builder.append(",");
            }
            builder.append(values[i].toString());
        }        
        builder.append("}");
        return builder.toString();
    }
    
    public static String toString(Complex[] values, int accuracy)
    {
        StringBuilder builder = new StringBuilder("{");
        for (int i=0; i<values.length; i++)
        {
            if (i>0)
            {
                builder.append(",");
            }
            builder.append(values[i].toString(accuracy));
        }        
        builder.append("}");
        return builder.toString();
    }
    
    // transposes a matrix presumed to be rectangular
    public static Complex[][] transpose(Complex[][] original)
    {
        int numOriginalRows = original.length;
        int numOriginalColumns = original[0].length;
        Complex[][] transpose = new Complex[numOriginalColumns][numOriginalRows];
        for (int i=0; i<original[0].length; i++)
        {
            for (int j=0; j<original.length; j++)
            {
                transpose[i][j] = original[j][i];
            }
        }
        return transpose;
    }
    
    // converts a 1D array into a 2D presuming length divisible by numColumns
    public static Complex[][] makeMatrix(Complex[] original, int numColumns)
    {
        int numRows = original.length/numColumns;
        Complex[][] matrix = new Complex[numRows][numColumns];
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numColumns; j++)
            {
                matrix[i][j] = original[i*numColumns+j];
            }
        }
        return matrix;
    }
    
    // converts a 2D array into a 1D
    public static Complex[] flatten(Complex[][] original)
    {
        int numRows = original.length;
        int numColumns = original[0].length;
        Complex[] flattened = new Complex[numRows*numColumns];
        for (int i=0; i<numRows; i++)
        {
            for (int j=0; j<numColumns; j++)
            {
                flattened[i*numColumns+j] = original[i][j];
            }
        }
        return flattened;
    }
}
