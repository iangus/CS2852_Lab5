/*
 * CS2852 - 041
 * Spring 2016
 * Lab 5
 * Name: Ian Guswiler
 * Created: 4/12/2016
 */

import edu.msoe.taylor.audio.SimpleAudio;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

import java.util.Random;
import javax.sound.sampled.LineUnavailableException;

import edu.msoe.taylor.audio.Note;


/**
 * The Guitar class generates guitar sounds based on user input.
 * 
 * In order to use this class correctly, one must create a Guitar
 * object, add all of the desired notes to the object, and then
 * call the play() method.  The play() method will generate a
 * list of samples for all of the notes to be played (by calling
 * an internal method (jaffeSmith())) and then send them to the
 * audio output stream.
 * @author t a y l o r@msoe.edu
 * @version 2016.04.08_2.2
 */
public class Guitar {
    /** 
     * Default sample rate in Hz 
     */
    private static final int DEFAULT_SAMPLE_RATE = 8000;
    
    /** 
     * Maximum sample rate in Hz
     */
    private static final int MAX_SAMPLE_RATE = 48000;

    private static final int MIN_SAMPLE_RATE = 8000;
    
    /** 
     * Default decay rate
     */
    private static final float DEFAULT_DECAY_RATE = 0.99f;
    
    /**
     * Queue of notes 
     */
    private Queue<Note> notes = new LinkedList<>();
    
    /**
     *  Sample rate in samples per second 
     */
    private int sampleRate;
    
    /** 
     * Decay rate 
     */
    private float decayRate;
        
    /**
     * Constructs a new Guitar object with the default sample rate
     * and decay rate.
     */
    public Guitar() {
        this.sampleRate = DEFAULT_SAMPLE_RATE;
        this.decayRate = DEFAULT_DECAY_RATE;
    }
    
    /**
     * Constructs a new Guitar object with the specified parameters.
     * If an invalid sampleRate or decayRate is specified, the
     * default value will be used and an error message is sent to
     * System.err.
     * @param sampleRate sample rate (between 8000 Hz and 48000 Hz)
     * @param decayRate decay rate (between 0.0f and 1.0f)
     */
    public Guitar(int sampleRate, float decayRate) {
        if(sampleRate > MAX_SAMPLE_RATE || sampleRate < MIN_SAMPLE_RATE){
            this.sampleRate = DEFAULT_SAMPLE_RATE;
            System.err.println("The specified sample rate was out of the required 8000Hz-48000Hz range. Sample Rate: " + sampleRate + "Hz");
        }else {
            this.sampleRate = sampleRate;
        }

        if(decayRate > 1.0f || decayRate < 0.0f){
            this.decayRate = DEFAULT_DECAY_RATE;
            System.err.println("The specified decay rate was out of the required 0.0-1.0 range. Decay Rate: " + decayRate);
        } else {
            this.decayRate = decayRate;
        }
    }
        
    /**
     * Adds the specified note to this Guitar.
     * @param note Note to be added.
     */
    public void addNote(Note note) {
        notes.offer(note);
    }
    
    /**
     * Generates the audio samples for the notes listed in the
     * current Guitar object by calling the jaffeSmith algorithm and
     * sends the samples to the speakers.
     * @throws LineUnavailableException If audio line is unavailable.
     * @throws IOException If any other input/output problem is encountered.
     */
    public void play() throws LineUnavailableException, IOException {
        try {
            SimpleAudio player = new SimpleAudio();
            player.play(jaffeSmith());
        } catch (LineUnavailableException e) {
            throw  new LineUnavailableException("The audio line is unavailable.");
        } catch (IOException e) {
            throw new IOException("An input/output problem has occurred.");
        }
    }

    /**
     * Uses the Jaffe-Smith algorithm to generate the audio samples.
     * <br />Implementation note:<br />
     * Use Jaffe-Smith algorithm described on the assignment page
     * to generate a sequence of samples for each note in the list
     * of notes.
     * 
     * @return List of samples comprising the pluck sound(s).
     */
    private List<Float> jaffeSmith() {
        List<Float> samples = new ArrayList<>();

        for(Note note : notes){
            int samplesPerPeriod = (int) (sampleRate/note.getFrequency());
            int numberOfSamples = (int) (sampleRate * ( note.getDuration() / 1000));
            float previousSample = 0;
            Queue<Float> periodSamples = new LinkedList<>();
            for(int i = 0; i < samplesPerPeriod; i++){
                Random gen = new Random();
                periodSamples.add((gen.nextFloat() * 2) - 1.0f);
            }

            for(int i = 0; i<numberOfSamples; i++){
                Float currentSample = periodSamples.poll();
                Float newSample = ((currentSample + previousSample)/2) * decayRate;
                samples.add(newSample);
                periodSamples.add(newSample);
                previousSample = currentSample;
            }
        }
        return samples;
    }

    /**
     * Returns an array containing all the notes in this Guitar.
     * OPTIONAL
     * @return An array of Notes in the Guitar object.
     */
    public Note[] getNotes() {
        throw new UnsupportedOperationException("Optional method not implemented");
    }
    
    /**
     * Creates a new file and writes to that file.
     * OPTIONAL
     * @param file File to write to.
     * @throws IOException If any other input/output problem is encountered.
     */
    public void write(File file) throws IOException {
        throw new UnsupportedOperationException("Optional method not implemented");
    }
}
