/**
 * InvalidNoteException.java
 * @author Krzysztof Paj�k
 */

package com.krzysztof.pajak.note.exceptions;

/**
 * InvalidNoteException is thrown when passed String is intended to be
 * in note notation but is not.
 * 
 * @author Krzysztof Paj�k
 */
public class InvalidNoteException extends Exception
{
	private static final long serialVersionUID = 2638274215116336704L;

	public InvalidNoteException(String s)
	{  
		super(s);  
	}  
}
