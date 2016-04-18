/*
 * CS2852 - 041
 * Spring 2016
 * Lab 5
 * Name: Ian Guswiler
 * Created: 4/12/2016
 */

import edu.msoe.taylor.audio.Note;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.sound.sampled.LineUnavailableException;

/**
 * Driver program for Data Structures lab assignment
 * @author t a y l o r@msoe.edu
 * @version 2014.02.19
 */
public class Lab5 {

    /**
     * Program that reads in notes from a text file and plays a song
     * using the Guitar class to generate the sounds which are then
     * played by a SimpleAudio object.
     * @param args Ignored
     */
    public static void main(String[] args) {
        Guitar guitar = new Guitar();

        try(Scanner fileScan = new Scanner(new File("test.txt"))){
            while(fileScan.hasNextLine()){
                String line = fileScan.nextLine();
                if(line.equals("")){
                    line = fileScan.nextLine();
                } else if(checkLineStart(line.charAt(0))){
                    guitar.addNote(parseNote(line));
                } else {
                    System.err.println("There was an unsupported line in the file and it was ignored.");
                }
            }
        }catch (FileNotFoundException e) {
            System.err.println("The text file could not be found.");
        } catch (NullPointerException e1){
            System.err.println("A note was in the incorrect format in the file and produced a null note.");
        } catch (IllegalArgumentException e2){
            System.err.println(e2.getMessage());
        } catch (NoSuchElementException e3){
            System.err.println(e3.getMessage());
        }

        try{
            guitar.play();
        } catch (LineUnavailableException e){
            System.err.println(e.getMessage());
        } catch (IOException e1){
            e1.getMessage();
        }
    }
    
    /**
     * Returns a new Note initialized to the value represented by the specified String
     * @param line Description of a note with scientific pitch followed by duration in milliseconds.
     * @return Note represented by the String passed in.  Returns null if it is unable to parse
     * the note data correctly.
     */
    private static Note parseNote(String line) {
        Note note;
        String spn = "";
        try{
            Scanner noteScanner = new Scanner(line);
            spn = noteScanner.next();
            Float duration = new Float(noteScanner.next());
            note = new Note(spn,duration);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("The input: " + spn + " is not recognized as a valid note");
        } catch (NoSuchElementException e1){
            throw new NoSuchElementException("The line \"" + line + "\" was not a valid input and was ignored");
        }
        return note;
    }

    private static boolean checkLineStart(char lineStart){
        boolean isAccepted = false;

        switch (lineStart){
            case 'A':
                isAccepted = true;
                break;
            case 'B':
                isAccepted = true;
                break;
            case 'C':
                isAccepted = true;
                break;
            case 'D':
                isAccepted = true;
                break;
            case 'E':
                isAccepted = true;
                break;
            case 'F':
                isAccepted = true;
                break;
            case 'G':
                isAccepted = true;
                break;
        }

        return isAccepted;
    }
}
