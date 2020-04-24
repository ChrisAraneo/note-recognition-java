/**
 * RecursiveFFT.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.algorithms;
import com.krzysztof.pajak.note.tools.Complex;

/**
 * RecursiveFFT is an implementation of recursive "2-radix DIT FFT" algorithm.
 * It's run method converts samples to frequency domain.
 * 
 * This algorithm is sometimes called "The Cooley–Tukey algorithm",
 * named after J. W. Cooley and John Tukey who published paper
 * about this algorithm in 1965.
 * 
 * @author Krzysztof Paj¹k
 */
public class RecursiveFFT extends AbstractAlgorithm
{
	//Buffer array used by recursive calls
	private Complex[] buffer;
	
	@Override
	/**
	 * Method converting samples from time domain
	 * to frequency domain.
	 * 
	 * Time complexity: O(n*lg(n))
	 * Where n is size of input samples.
	 */
	public Complex[] run(double[] input)
	{
		//Getting smallest number N=2^i greater or equal input.length
		int N = 2;
		while(N < input.length) { N *= 2; }
		
		//Creating new buffer for further calculations
		//Buffer's size is x = 2^i
		buffer = new Complex[N];
		
		//Copying input samples to buffer
		//And padding remaining space with zeros
		for(int i=0; i<N; i++)
		{
			if(i<input.length)
			buffer[i] = new Complex(input[i], 0.0);
			else
			buffer[i] = new Complex(0.0, 0.0);
		}
		
		//Running recursive FFT algorithm
		FFT(0, buffer.length);
		
		return buffer;
	}
	
	/**
	 * Implementation of recursive FFT algorithm.
	 * All recursive calls uses single array.
	 * 
	 * Each function call moves array elements with even index
	 * to first half and moves array elements with odd index
	 * to second half. After this operation DFTs are calculated
	 * in a loop.
	 * 
	 * @param start		Beginning of array part that will be processed
	 * @param end		Ending of array part that will be processed
	 */
	private void FFT(int start, int end)
	{
		int length = end-start;
        if(length >= 2)
        {
        	//Moving elements with even index to the first half
        	//and elements with odd index to the second half.
            groupElements(start, end);
            
            //Recursive calls on each half
            FFT(start, start+length/2);
            FFT(start+length/2, end);
            
            //Constant
            final double arg = (-2.0*Math.PI)/length;
            
            //Calculating DFT
            for(int k=0; k<length/2; k++)
            {
            	Complex e = buffer[start + k];
				Complex o = buffer[start + k + length/2];
				
				Complex w = Complex.cexp(new Complex(0.0, arg*k));
				Complex wo = Complex.mult(w, o);

				buffer[start+k] = Complex.add(e,wo);
				buffer[start+k+length/2] = Complex.sub(e,wo);
            }
        }
	}
	
	/**
	 * Moves elements with even index to the first half
	 * and elements with odd index to the second half.
	 * 
	 * @param start
	 * @param end
	 */
	private void groupElements(int start, int end)
	{
		int length = end - start;
		Complex[] odd = new Complex[length/2];
		
        for(int i = 0; i < length/2; i++)
        odd[i] = buffer[start + i * 2 + 1];
        
        for(int i = 0; i < length/2; i++)
        buffer[start + i] = buffer[start + i * 2];
        
        for(int i = 0; i < length/2; i++)
        buffer[start + i + length/2] = odd[i];
	}

}
