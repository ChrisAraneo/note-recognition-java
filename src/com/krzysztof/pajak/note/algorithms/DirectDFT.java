/**
 * DirectDFT.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.algorithms;
import com.krzysztof.pajak.note.tools.Complex;

/**
 * DirectDFT is an INEFFICIENT way of calculating DFTs. 
 * It's run method converts samples to frequency domain directly from the equation.
 * It has been added only for test purposes and to show how much time it takes
 * when calculating DFTs directly from the equation.
 * 
 * >> DirectDFT should not be used in practical applications.
 * 
 * @author Krzysztof Paj¹k
 */
public class DirectDFT extends AbstractAlgorithm
{
	@Override
	/**
	 * Method converting samples from time domain
	 * to frequency domain.
	 * 
	 * Time complexity: O(n^2)
	 * Where n is size of input samples.
	 * 
	 * @param input		Input sound samples in time domain.
	 * @return			Input converted to frequency domain.
	 */
	public Complex[] run(double[] input)
	{
		final int N = input.length;
		final double PIconst = (2.0)*Math.PI/(double)N;
		
		Complex[] output = new Complex[N];
		
		double arg = 0.0;
		for(int k=0; k<N; k++)
		{
			for(int n=0; n<N; n++)
			{
				Complex x = new Complex(input[n], 0.0);
				Complex w = Complex.cexp(new Complex(0.0, (-1.0)*arg*k));
				output[k] = Complex.mult(x, w);
			}
			arg = arg + PIconst;
		}
		
		return output;
	}
}