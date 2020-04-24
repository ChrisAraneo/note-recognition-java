/**
 * InputSampleException.java

 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.exceptions;

/**
 * InputSamplesException is thrown when there is no samples passed as parameter.
 * 
 * @author Krzysztof Paj¹k
 */
public class InputSamplesException extends Exception
{
	private static final long serialVersionUID = 7609052762684643879L;

	public InputSamplesException(String s)
	{  
		super(s);  
	}
}
