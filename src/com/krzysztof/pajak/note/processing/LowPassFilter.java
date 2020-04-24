/**
 * LowPassFilter.java
 * @author Patrice Tarrabia
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.processing;

/**
 * Implementation of low pass filter, type biquad / butterworth.
 * 
 * Low pass filters remove frequencies above certain frequency
 * called cutoff frequency.
 * 
 * @author Patrice Tarrabia
 * @author Krzysztof Paj¹k
 */
public class LowPassFilter
{
	/**
	 * This method removes frequencies above certain frequency
	 * called cutoff frequency.
	 *
	 * @param input			Input samples.
	 * @param f				Cutoff frequency.
	 * @param r				Resonance amount.
	 * @param sampleRate	Sampling rate.
	 * @return				Returns filtered samples.
	 *
	 * @author Patrice Tarrabia
	 * @author Krzysztof Paj¹k
	 * @see http://musicdsp.org/showArchiveComment.php?ArchiveID=38
	 */
	public static double[] run(double[] input, double r, double f, int sampleRate)
	{
		final double c = 1.0 / Math.tan(Math.PI * f / sampleRate);
		
		final double a1 = 1.0 / ( 1.0 + r * c + c * c);
		final double a2 = 2* a1;
		final double a3 = a1;
		final double b1 = 2.0 * ( 1.0 - c*c) * a1;
		final double b2 = ( 1.0 - r * c + c * c) * a1;
		
		double[] output = new double[input.length];
		output[0] = input[0];
		output[1] = input[1];
		for(int i=2; i<output.length; i++)
		{
			output[i] = a1 * input[i] + a2 * input[i-1] + a3 * input[i-2] - b1*output[i-1] - b2*output[i-2];
		}
		
		return output;
	}
}
