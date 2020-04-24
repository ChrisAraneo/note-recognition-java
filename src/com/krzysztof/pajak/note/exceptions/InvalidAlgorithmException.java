/**
 * InvalidAlgorithmException.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.exceptions;

/**
 * InvalidAlgorithmException is thrown when there is no algorithm
 * selected to convert samples from time domain to frequency domain
 * or this algorithm is not a subclass of AbstractAlgorithm.
 * 
 * @author Krzysztof Paj¹k
 */
public class InvalidAlgorithmException extends Exception
{  
	private static final long serialVersionUID = -667961869755513825L;

	public InvalidAlgorithmException(String s)
	{  
		super(s);  
	}  
}  