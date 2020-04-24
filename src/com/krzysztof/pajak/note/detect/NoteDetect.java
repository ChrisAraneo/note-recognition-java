/**
 * NoteDetect.java
 * @author Krzysztof Paj¹k
 */

package com.krzysztof.pajak.note.detect;
import com.krzysztof.pajak.note.algorithms.AbstractAlgorithm;
import com.krzysztof.pajak.note.algorithms.IterativeFFT;
import com.krzysztof.pajak.note.algorithms.RecursiveFFT;
import com.krzysztof.pajak.note.exceptions.InputSamplesException;
import com.krzysztof.pajak.note.exceptions.InvalidAlgorithmException;
import com.krzysztof.pajak.note.exceptions.InvalidNoteException;
import com.krzysztof.pajak.note.processing.AmplitudeNormalizer;
import com.krzysztof.pajak.note.processing.HighPassFilter;
import com.krzysztof.pajak.note.processing.LowPassFilter;
import com.krzysztof.pajak.note.tools.ArrayMethods;
import com.krzysztof.pajak.note.tools.Complex;
import com.krzysztof.pajak.note.tools.NoteLookup;

/**
 * NoteDetect provides main library interface.
 * 
 * This class' goal is to provide quick and easy way
 * to use the most important library functions.
 * 
 * Constructor takes samples as an input. 
 * Samples should contain as few as possible distinct sounds
 * to give best results.
 * 
 * Method run() runs signal processing algorithms, such as filters
 * and FFT, and returns most probable notes played.
 * 
 * @author Krzysztof Paj¹k
 */
public class NoteDetect
{
	//Sampling rate
	int sampleRate = 44100;
	
	//Samples array contains input samples in time domain
	//Result array contains samples converted to frequency domain.
	double[] samples;
	double[] result;
	
	//Algorithm used to convert samples to frequency domain.
	AbstractAlgorithm algorithm = new IterativeFFT();
	
	//If average amplitude is lower than
	//this threshold then run method return null
	final static double silenceTreshold = 0.005;
	
	/**
	 * Class constructor specifying input samples.
	 * Samples should contain as few as possible distinct sounds
	 * to give best results.
	 * Sampling rate is set as default 44100 (Hz).
	 * 
	 * @param samples	Input sound samples, in time domain (standard representation).
	 */
	public NoteDetect(float[] samples)
	{
		int length = samples.length;
		this.samples = new double[length];
		for(int i=0; i<length; i++)
		{
			this.samples[i] = (double) samples[i];
		}
	}
	
	/**
	 * Class constructor specifying input samples and sampling rate.
	 * Samples should contain as few as possible distinct sounds
	 * to give best results.
	 * 
	 * @param samples		Input sound samples, in time domain (standard representation).
	 * @param sampleRate	Sampling rate.
	 */
	public NoteDetect(float[] samples, int sampleRate)
	{
		this(samples);
		this.sampleRate = sampleRate;
	}
	
	/**
	 * Class constructor specifying input samples.
	 * Samples should contain as few as possible distinct sounds
	 * to give best results.
	 * Sampling rate is set as default 44100 (Hz).
	 * 
	 * @param samples	Input sound samples, in time domain (standard representation).
	 */
	public NoteDetect(double[] samples)
	{
        this.samples = samples;
        this.result = null;
	}
	
	/**
	 * Class constructor specifying input samples and sampling rate.
	 * Samples should contain as few as possible distinct sounds
	 * to give best results.
	 * 
	 * @param samples		Input sound samples, in time domain (standard representation).
	 * @param sampleRate	Sampling rate.
	 */
	public NoteDetect(double[] samples, int sampleRate)
	{
		this(samples);
        this.sampleRate = sampleRate;
	}
	
	/**
	 * Changes sound sampling rate.
	 * @param sampleRate	Sampling rate, default 44100 (Hz).
	 */
	public void setSampleRate(int sampleRate)
	{
		this.sampleRate = sampleRate;
	}
	
	/**
	 * Changes samples for further calculations.
	 * @param samples	Input sound samples, in time domain (standard representation).
	 */
	public void setSamples(double[] samples)
	{
		this.samples = samples.clone();
		this.result = null;
	}
	
	/** 
	 * Changes algorithm that converts data from time domain
	 * to frequency domain (FFT or similar algorithms).
	 *  
	 * Parameter have to be a subclass of AbstractAlgorithm.
	 * It has to implement run() method.
	 *
	 * @param algorithmClass	Class that extends AbstractAlgorithm class.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T extends AbstractAlgorithm> void setAlgorithm(Class<T> algorithmClass) throws InstantiationException, IllegalAccessException
	{
		this.algorithm = (AbstractAlgorithm) algorithmClass.newInstance();
	}

	/**
	 * Main library interface and fast way to get estimated notes
	 * without in-depth study of other classes and methods provided
	 * in library.
	 * 
	 * @param tolerance		Tolerance for estimating, 1.0 is most strict, 0.0 least.
	 * @return Returns most probable notes played on input samples data.
	 * @throws InvalidAlgorithmException
	 * @throws InputSamplesException
	 * @throws InvalidNoteException
	 */
	public String[] run(double tolerance) throws InvalidAlgorithmException, InputSamplesException, InvalidNoteException
	{
		//Handling exceptions
		if(algorithm == null || algorithm.getClass().isAssignableFrom(AbstractAlgorithm.class))
		{
			throw new InvalidAlgorithmException("Invalid frequency domain algorithm chosen");
		}
		else if(samples == null)
		{
			throw new InputSamplesException("No samples given as an input");
		}
		
		// -------------------------------------------------------- //
		
		//Processing input data, converting data to frequency domain
		if(result == null)
		{			
			//If sound is too quiet then return null
			if(averageAmplitude(samples) < silenceTreshold)
			{
				return null;				
			}
			
			//Frequency filtering and sound normalizing
			int highCut = (int) NoteLookup.getFrequency("C3");
			int lowCut = (int) NoteLookup.getFrequency("C4");
			samples = AmplitudeNormalizer.run(samples);
			samples = HighPassFilter.run(samples, highCut, 1.4, this.sampleRate);
			samples = LowPassFilter.run(samples, lowCut, 0.5, this.sampleRate);
			samples = AmplitudeNormalizer.run(samples);
			
			//Calculating DFTs and stretching array
			Complex[] resultCpx = algorithm.run(samples);
			result = ArrayMethods.complexToDouble(resultCpx);
			result = ArrayMethods.stretchArray(result, this.sampleRate);
		}
		
		//Estimating notes played using previously calculated DFTs
		return NoteEstimate.getNotes(result);
	}
	
	/**
	 * Main library interface and fast way to get estimated notes
	 * without in-depth study of other classes and methods provided
	 * in library.
	 * 
	 * @return Returns most probable notes played on input samples data.
	 * @throws InvalidAlgorithmException
	 * @throws InputSamplesException
	 * @throws InvalidNoteException
	 */
	public String[] run() throws InvalidAlgorithmException, InputSamplesException, InvalidNoteException
	{
		return this.run(NoteEstimate.getDefaultTolerance());
	}
	
	/**
	 * Calculates average amplitude.
	 * @param samples	Samples in time domain.
	 * @return	Average amplitude
	 */
	private double averageAmplitude(double[] samples)
	{
		double sum = 0.0;
		for(int i=0; i<samples.length; i++)
		{
			sum += Math.abs(samples[i]);
		}
		return (sum / samples.length);
	}
}
