/**
 * IterativeFFT.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.algorithms;
import com.krzysztof.pajak.note.tools.Complex;

/**
 * IterativeFFT is an implementation of iterative "2-radix DIT FFT" algorithm.
 * It's run method converts samples to frequency domain.
 * 
 * This algorithm is sometimes called "The Cooley–Tukey algorithm",
 * named after J. W. Cooley and John Tukey who published paper
 * about this algorithm in 1965.
 * 
 * @author Krzysztof Paj¹k
 */
public class IterativeFFT extends AbstractAlgorithm
{
	@Override
	/**
	 * Method converting samples from time domain
	 * to frequency domain.
	 * 
	 * Time complexity: O(n*lg(n))
	 * Where n is size of input samples.
	 * 
	 * @param input		Input sound samples in time domain.
	 * @return			Input converted to frequency domain.
	 */
	public Complex[] run(double[] samples)
    {
		//Getting smallest number N=2^i greater or equal input.length
		int N = 2;
		while(N < samples.length) { N *= 2; }
		
		//Creating new buffer for further calculations
		//Buffer's size is x = 2^i
		Complex[] buffer = new Complex[N];
		
		//Copying input samples to buffer
		//And padding remaining space with zeros
		for(int i=0; i<buffer.length; i++)
		{
			if(i < samples.length)
			buffer[i] = new Complex(samples[i], 0.0);
			else
			buffer[i] = new Complex(0.0, 0.0);
		}

        //Bit reversal array permutation
		buffer = bitReverseCopy(buffer);

		//Calculating DFTs
		final double PI2 = (-2.0)*Math.PI;
        for(int l=2; l<=N; l=l+l) 
        {
            for(int k=0; k<l/2; k++)
            {
                double arg = (k*PI2) / l;
                Complex w = new Complex(Math.cos(arg), Math.sin(arg));
                
                for(int j=0; j<N/l; j++)
                {
                	Complex wo = Complex.mult(w, buffer[j*l+k + l/2]);
                	buffer[j*l + k + l/2] = Complex.sub(buffer[j*l + k], wo);
                	buffer[j*l + k] = Complex.add(buffer[j*l + k], wo);
                }
            }
        }
        
        return buffer;
    }
	
	/**
	 * Method performing bit reversal.
	 * Bit reversal is the array permutation where the data at an index n,
	 * written in binary with digits b4 b3 b2 b1 b0,
	 * is transferred to the index with reversed digits b0 b1 b2 b3 b4
	 * 
	 * @param array		Input array
	 * @return			'Bit reversal' permutation of input array
	 */
	private Complex[] bitReverseCopy(Complex[] array)
	{
		int N = array.length;
		int shift = Integer.numberOfLeadingZeros(N) + 1;
        for(int k=0; k<N; k++)
        {
            int j = Integer.reverse(k) >>> shift;
            if(j > k)
            {
                Complex t = array[j];
                array[j] = array[k];
                array[k] = t;
            }
        }
        return array;
	}
}
