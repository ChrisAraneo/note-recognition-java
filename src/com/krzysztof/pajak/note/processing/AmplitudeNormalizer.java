/**
 * AmplitudeNormalizer.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.processing;

/**
 * AmplitudeNormalizer's run method slightly amplifies
 * amplitude of input samples by factor related to maximum
 * absolute value found in this array.
 * 
 * @author Krzysztof Paj¹k
 */
public class AmplitudeNormalizer
{
	/**
	 * Finds maximum value in array and then multiplies
	 * every value in array by (1.0 / max) so
	 * that amplitude of sound wave represented by
	 * this array is slightly amplified without clipping.
	 * 
	 * @param samples	Samples to amplify
	 * @return			Amplified samples
	 */
	public static double[] run(double[] samples)
	{
		final int length = samples.length;
		final double max = findMax(samples);
		final double factor = 1.0 / max;
		
		for(int i=0; i<length; i++)
		{
			samples[i] = factor * samples[i];
		}
		
		return samples;
	}
	
	/**
	 * Finds and returns maximum absolute value in array.
	 * @param array		Array to analyze
	 * @return			Maximum absolute value in array
	 */
    private static double findMax(double[] array)
	{
        double max = 0;
        for(int i=0; i<array.length; i++)
        {
        	double abs = Math.abs(array[i]);
        	if(abs > max)
        	{
        		max = abs;
        	}
        }
        return max;
    }
}
