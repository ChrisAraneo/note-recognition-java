/**
 * NoteLookup.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.tools;
import java.util.ArrayList;
import com.krzysztof.pajak.note.exceptions.InvalidNoteException;

/**
 * NoteLookup class provides methods for getting frequency of particular note,
 * validating note notation and more. It is used inside other classes.
 * @author Krzysztof Paj¹k
 */
public class NoteLookup
{
	//Note notation regex
	final static String regex = "([ACDFG]#?|[BE])\\d";
	
	//Frequency of note C0 with lowest pitch
	//used for calculating frequencies of others
	final static double C0 = 16.35;
	
	//Twelth root of 2
	final static double diff = 1.059463094359295; //2^(1/12)
	
	//Valid letters
	final static String[] letters = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	
	//ArrayList for storing note strings and their frequencies
	final static ArrayList<String> noteStrings = new ArrayList<String>();
	final static ArrayList<Double> frequencies = new ArrayList<Double>();
	private static int size = 0;
	
	/**
	 * Validating note notation using regex.
	 * @param note	String
	 * @return		Valid or invalid
	 */
	public static boolean isNote(String note)
	{
		return note.matches(regex);
	}
	
	/**
	 * This method calculates frequencies of notes
	 * using frequency formula.
	 * @param size		Last pitch to be calculated.
	 */
	private static void calculateFrequencies(int size)
	{
		for(int i=noteStrings.size(); i<size; i++)
		{
			int octave = i/12;
			String letter = letters[i%12];
			noteStrings.add(letter + Integer.toString(octave));
		}
		
		if(frequencies.size() == 0)
		{
			frequencies.add(C0);
		}
		for(int i=frequencies.size(); i<size; i++)
		{
			double previous = frequencies.get(i-1);
			double next = previous * diff;
			frequencies.add(next);
		}
		
		NoteLookup.size = size;
	}
	
	private static int noteStringToPitch(String note) throws InvalidNoteException
	{
		if(!isNote(note))
		{
			throw new InvalidNoteException("String is not a proper note. It has to match regex ([ACDFG]#?|[BE])\\d");
		}
		
		int pitch = 0;
		if(note.length() == 2)
		{
			String letter = "" + note.charAt(0);
			int letterN = 0;
			for(int i=0; i<letters.length; i++)
			{
				if(letter.equals(letters[i]))
				{
					letterN = i;
					i = letters.length; //break
				}
			}
			
			int octave = Integer.parseInt(""+note.charAt(1));
			pitch = octave*12 + letterN;
		}
		else if(note.length() == 3)
		{
			String letter = "" + note.charAt(0) + note.charAt(1);
			int letterN = 0;
			for(int i=0; i<letters.length; i++)
			{
				if(letter.equals(letters[i]))
				{
					letterN = i;
					i = letters.length; //break
				}
			}
			
			int octave = Integer.parseInt(""+note.charAt(2));
			pitch = octave*12 + letterN;
		}
		return pitch;
	}
	
	/**
	 * Returns letter notation of note defined by semitones pitch.
	 * @param pitch		(Positive) number of semitones, counting from note C0
	 * @return			Note in letter notation 
	 * @throws InvalidNoteException 
	 */
	private static String pitchToNoteString(int pitch) throws InvalidNoteException
	{
		if(pitch < 0)
		{
			throw new InvalidNoteException("Negative note pitch");
		}
		
		int octave = pitch / 12;
		String letter = letters[pitch % 12];
		
		return letter + Integer.toString(octave);
	}
	
	/**
	 * Returns frequency of note defined by its pitch
	 * @param pitch		(Positive) number of semitones, counting from note C0
	 * @return			Returns frequency of note defined by pitch value
	 */
	public static double getFrequency(int pitch)
	{
		if(size <= pitch)
		calculateFrequencies(pitch+1);
		
		return frequencies.get(pitch);
	}

	/**
	 * Returns frequency of note defined by its letter notation
	 * @param pitch		(Positive) number of semitones, counting from note C0
	 * @return			Returns frequency of note defined by pitch value
	 */
	public static double getFrequency(String note) throws InvalidNoteException
	{
		return getFrequency(noteStringToPitch(note));
	}

	/**
	 * Returns pitch of note defined by its letter notation
	 * @param symbol	Note in letter notation
	 * @return			Note's pitch value
	 * @throws InvalidNoteException 
	 */
	public static int getPitch(String note) throws InvalidNoteException
	{
		return noteStringToPitch(note);
	}
	
	/**
	 * Returns letter notation of note defined by its pitch
	 * @param pitch		Note's pitch
	 * @return			Note in letter notation
	 * @throws InvalidNoteException 
	 */
	public static String getNoteString(int pitch) throws InvalidNoteException
	{
		return pitchToNoteString(pitch);
	}
	
	/**
	 * @return	The twelth root of 2, being the factor in frequency formula
	 */
	public static double getDiff()
	{
		return diff;
	}
	
	/**
	 * @return	Valid letters and combination with # in music notation
	 */
	public static String[] getLetters()
	{
		return letters;
	}
}
