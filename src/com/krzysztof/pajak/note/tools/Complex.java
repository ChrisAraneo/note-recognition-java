/**
 * Complex.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.tools;

/**
 * Basic implementation of complex numbers
 * and basic arithmetic operations on them.
 * 
 * @author Krzysztof Paj¹k
 */
public class Complex
{
	//Real part of complex number
    private double re;
    
    //Imaginary part of complex number
    private double im;
 
    /**
     * Constructor initialize number as 0+0i
     */
    public Complex()
    {
    	this(0.0, 0.0);
    }
    
    /**
     * @param re	Real part of complex number.
     * @param im	Imaginary part of complex number.
     */
    public Complex(double re, double im)
    {
        this.re = re;
        this.im = im;
    }
    
    /**
     * @return	Real part of the complex number.
     */
    public double re()
    {
    	return re;
    }
    
    /**
     * @return	Imaginary part of the complex number.
     */
    public double im()
    {
    	return im;
    }
    
    /**
     * @return	The absolute value (modulus) of complex number.
     */
    public double abs()
    {
    	return Math.hypot(re, im);
    }
 
    /**
     * Adding two complex numbers.
     * @param a		z1 = a + bi
     * @param b		z2 = c + di
     * @return		z1 + z2 = (a+c)+(b+d)i
     */
    public static Complex add(Complex a, Complex b)
    {
        return new Complex(a.re() + b.re(), a.im() + b.im());
    }
    
    /**
     * Subtracting two complex numbers.
     * @param a		z1 = a + bi
     * @param b		z2 = c + di
     * @return		z1 - z2 = (a-c)+(b-d)i
     */
    public static Complex sub(Complex a, Complex b)
    {
        return new Complex(a.re() - b.re(), a.im() - b.im());
    }
 
    /**
     * Multiplying two complex numbers.
     * @param a		z1 = a + bi
     * @param b		z2 = c + di
     * @return		z1 * z2 = (ac-bd)+(ad+ic)i
     */
    public static Complex mult(Complex a, Complex b)
    {
    	double re = a.re() * b.re() - a.im() * b.im();
    	double im = a.re() * b.im() + a.im() * b.re();
        return new Complex(re, im);
    }
    
    /**
     * Result of complex exponantion e^(x+iy) assuming x,y are real
     * e^(x+iy) = e^x * cos(y) + i(e^x * sin(y))
     * 
     * @param a		Real number
     * @return		Complex number
     */
    public static Complex cexp(Complex a)
    {
    	double e_x = Math.pow(Math.E, a.re());
    	double re = e_x * Math.cos(a.im());
    	double im = e_x * Math.sin(a.im());
    	return new Complex(re, im);
    }
 
    @Override
    public String toString()
    {
        return "(" + re + "," + im + ")";
    }
}