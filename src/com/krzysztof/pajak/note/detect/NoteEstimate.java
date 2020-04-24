/**
 * NoteEstimate.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.detect;
import java.util.ArrayList;

import com.krzysztof.pajak.note.exceptions.InvalidNoteException;
import com.krzysztof.pajak.note.tools.NoteLookup;

/**
 * NoteEstimate class uses (relatively) simple methods to
 * detect the most probable note pitch played in sound sample
 * represented in frequency domain input data.
 * 
 * @author Krzysztof Paj¹k
 */
public class NoteEstimate
{
	final static double defaultTolerance = 0.5;
	
	/**
	 * Detecting pitch of the most probable notes played
	 * 
	 * @param frequencies	Data in frequency domain.
	 * @return				Array of notes
	 * @throws InvalidNoteException 
	 */
	public static String[] getNotes(double[] frequencies) throws InvalidNoteException
	{
		return getNotes(frequencies, defaultTolerance);
	}
	
	/**
	 * Detecting pitch of the most probable notes played
	 * 
	 * @param frequencies	Data in frequency domain.
	 * @param tolerance		Tolerance factor in range [0.0,1.0] 1.0 the most strict, 0.0 the least.
	 * @return				Array of notes
	 * @throws InvalidNoteException 
	 */
	public static String[] getNotes(double[] frequencies, double tolerance) throws InvalidNoteException
	{
		String[] letters = NoteLookup.getLetters();
		double[] values = new double[letters.length];
		
		//Summing intensity of certain frequencies
		for(int i=0; i<letters.length; i++)
		{
			values[i] = getNotesIntensity(frequencies, letters[i]);
		}

		//Finding maximum
		double max = 0;
		for(int i=0; i<values.length; i++)
		{
			if(values[i] > max)
			{
				max = values[i];
			}
		}
		
		//Determining the most probable notes played
		//Converting tolerance factor to a number in range [0.0,1.0]
		tolerance = Math.min(1.0, Math.abs(tolerance));
		ArrayList<String> list = new ArrayList<String>();
		for(int i=0; i<values.length; i++)
		{
			if(values[i] > max * tolerance)
			{
				list.add(letters[i]);
			}
		}
		
		//If there is more than 5 notes detected
		//it means that the sound is probably noise
		if(list.size() > 5)
		{
			return null;
		}

		//ArrayList -> String[]
		String[] array = new String[list.size()];
		for(int i=0; i<array.length; i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * Sums intensity of certain frequencies.
	 * @param frequencies			Array with frequency domain data.
	 * @param symbolWithoutOctave	C,C#,D,D#,E,F,F#,G,G#,A,A# or B
	 * @return		Summed value used for further calculations.
	 * @throws InvalidNoteException 
	 */
	private static double getNotesIntensity(double[] frequencies, String symbolWithoutOctave) throws InvalidNoteException
	{
		double diff = NoteLookup.getDiff()*0.956;
		
		double[] pitch = new double[4];
		pitch[0] = NoteLookup.getFrequency(symbolWithoutOctave + "2");
		pitch[1] = NoteLookup.getFrequency(symbolWithoutOctave + "3");
		pitch[2] = NoteLookup.getFrequency(symbolWithoutOctave + "4");
		pitch[3] = NoteLookup.getFrequency(symbolWithoutOctave + "5");
		
		double sum = 0.0;
		for(int i=0; i<pitch.length; i++)
		{
			double max = 0.0;
			int start = Math.max(0, (int)(pitch[i]/diff));
			int end = Math.min(frequencies.length, (int)(pitch[i]*diff));
			for(int j=start; j<end; j++)
			{
				max = Math.max(max, frequencies[j]);
			}
			sum += max;
		}
		
		return sum;
	}
	
	/**
	 * @return Default tolerance.
	 */
	public static double getDefaultTolerance()
	{
		return defaultTolerance;
	}
}
