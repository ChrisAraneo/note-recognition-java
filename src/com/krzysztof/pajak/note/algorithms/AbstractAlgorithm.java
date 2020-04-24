/**
 * AbstractAlgorithm.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.algorithms;
import java.io.PrintStream;

import com.krzysztof.pajak.note.tools.Complex;

/**
 * AbstractAlgorithm is an abstract class
 * requiring subclass' run method that has to convert
 * input samples from time domain to frequency domain.
 * 
 * @author Krzysztof Paj¹k
 */
public abstract class AbstractAlgorithm
{
	/**
	 * Method converting samples from time domain
	 * to frequency domain.
	 * 
	 * @param input		Input sound samples in time domain.
	 * @return			Input converted to frequency domain.
	 */
	abstract public Complex[] run(double[] input);
	
	/**
	 * For debugging purposes, prints measured
	 * run time of calculations.
	 * @param input		Input sound samples in time domain.
	 * @param PS		Stream to print time.
	 * @return			Input converted to frequency domain.
	 */
	public Complex[] runDebug(double[] input, PrintStream PS)
	{
		long start = System.currentTimeMillis();
		Complex[] result = this.run(input);
		long end = System.currentTimeMillis();
		PS.println(this.getClass().getName() + ": " + (end - start) + "ms");
		return result;
	}
}
