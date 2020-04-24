/**
 * ArrayMethods.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.tools;

/**
 * ArrayMethods contains few functions that are being
 * used in other classes.
 * 
 * @author Krzysztof Paj¹k
 */
public class ArrayMethods
{
	/**
	 * Gets input array and returns "stretched" array size m.
	 * Values in output array are calculated by using linear interpolation.
	 * 
	 * b[i] = a[i*(n-1)/(m-1)] + (i*(n-1)/(m-1) - k) * (a[i*(n-1)/(m-1)+1] - a[i*(n-1)/(m-1)]
	 * 
	 * @param a		Input array
	 * @param m		Size of output array
	 * @return		New array of size "m" with interpolated values
	 */
	public static double[] stretchArray(double[] a, int m)
	{
		int n = a.length;
		double[] b = new double[m];
		
		double scale = (double)(n-1)/(double)(m-1);
		for(int i=0; i<m; i++)
		{
			double x = i*scale;
			if(x <= 0)
			{
				b[i] = a[0];
			}
			else if(x >= n-1)
			{
				b[i] = a[n-1];
			}
			else
			{
				int x1 = (int)Math.floor(x);
				int x2 = x1+1;
				double y1 = a[x1];
				double y2 = a[x2];
				
				//Line equation
				//y = y1 + (x - x1)*(y2 - y1)/(x2 - x1)
				b[i] = y1 + (x - x1)*(y2 - y1);
			}	
		}
		return b;
	}
	
	/**
	 * Converting double array to complex array.
	 * @param input		Double array
	 * @return			Complex array
	 */
	public static Complex[] doubleToComplex(double[] input)
	{
		int length = input.length;
		Complex[] output = new Complex[length];
		
        for(int i=0; i<length; i++)
        output[i] = new Complex(input[i], 0.0);
        
        return output;
	}
	
	/**
	 * Converting complex array to double array.
	 * Values in double array are abs of complex values.
	 * @param input		Complex array
	 * @return			Double array
	 */
	public static double[] complexToDouble(Complex[] input)
	{
		int length = input.length;
		double[] output = new double[length];
		
		for(int i=0; i<length; i++)
		{
			output[i] = input[i].abs();
		}
		
		return output;
	}
}
