package chamberChoir;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @desc A way of storing a song name and a relative location for ease of use.
 *       Keeps name, filepath, and notes in one place.
 * 
 * @author Jake Grosse
 *
 */
public class Song {
	// instance variables
	private final String name;
	private final String filePath;
	private List<BellNote> notes = new ArrayList<BellNote>();

	// boolean flag of whether the song can be played
	private boolean validSong = true;

	/**
	 * @desc Constructor which takes a song name and file path.
	 * 
	 * @param name     the display name of the song
	 * @param filePath name of the file in the relative filesystem without
	 *                 containing folders or extension
	 */
	Song(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
		this.notes = readSong(filePath);
	}

	// method to read a song file in to a list of bellnotes
	// checks for validity
	private List<BellNote> readSong(String filename) {
		// initialize the empty song to be added to
		List<BellNote> song = new ArrayList<BellNote>();

		// create a list of all the notes possible to verify note input
		List<String> allNotes = new ArrayList<String>();
		for (Note n : Note.values()) {
			allNotes.add(n.name());
		}

		// flags to determine whether the song is valid
		boolean invalidLengthFlag = false;
		boolean invalidNoteFlag = false;

		// initialize length to null so it may be replaced
		NoteLength length = null;

		// initialize variables for scanner
		String nextLine;
		String[] splitLine;
		int lineNum = 0;

		// open a scanner to read lines of the file
		try (Scanner betterThanBR = new Scanner(new File(filename))) {
			// I know it's not actually better than a Buffered Reader, but it's a great joke
			// Ensure there is another line in the file
			while (betterThanBR.hasNextLine()) {
				// get the line as a String
				nextLine = betterThanBR.nextLine();
				// ignore empty lines
				if (!nextLine.isEmpty()) {
					lineNum++;
					// get note and length separated
					splitLine = nextLine.split(" ");

					// Account for flat notation by reassigning them to sharps
					// I know there is probably a more efficient way, but this works :)
					String note = null;
					switch (splitLine[0].toString()) {
					case "B0F":
						note = "A0S";
						break;
					case "D0F":
						note = "C0S";
						break;
					case "E0F":
						note = "D0S";
						break;
					case "G0F":
						note = "F0S";
						break;

					// can't use find and replace
					case "A1F":
						note = "G0S";
						break;

					case "B1F":
						note = "A1S";
						break;
					case "D1F":
						note = "C1S";
						break;
					case "E1F":
						note = "D1S";
						break;
					case "G1F":
						note = "F1S";
						break;

					// can't use find and replace
					case "A2F":
						note = "G1S";
						break;

					case "B2F":
						note = "A2S";
						break;
					case "D2F":
						note = "C2S";
						break;
					case "E2F":
						note = "D2S";
						break;
					case "G2F":
						note = "F2S";
						break;

					// can't use find and replace
					case "A3F":
						note = "G2S";
						break;

					case "B3F":
						note = "A3S";
						break;
					case "D3F":
						note = "C3S";
						break;
					case "E3F":
						note = "D3S";
						break;
					case "G3F":
						note = "F3S";
						break;

					// can't use find and replace
					case "A4F":
						note = "G3S";
						break;

					case "B4F":
						note = "A4S";
						break;
					case "D4F":
						note = "C4S";
						break;
					case "E4F":
						note = "D4S";
						break;
					case "G4F":
						note = "F4S";
						break;

					// can't use find and replace
					case "A5F":
						note = "G4S";
						break;

					case "B5F":
						note = "A5S";
						break;
					case "D5F":
						note = "C5S";
						break;
					case "E5F":
						note = "D5S";
						break;
					case "G5F":
						note = "F5S";
						break;

					// can't use find and replace
					case "A6F":
						note = "G5S";
						break;

					case "B6F":
						note = "A6S";
						break;
					case "D6F":
						note = "C6S";
						break;
					case "E6F":
						note = "D6S";
						break;
					case "G6F":
						note = "F6S";
						break;

					// can't use find and replace
					case "A7F":
						note = "G6S";
						break;

					case "B7F":
						note = "A7S";
						break;
					case "D7F":
						note = "C7S";
						break;
					case "E7F":
						note = "D7S";
						break;
					case "G7F":
						note = "F7S";
						break;

					case "A8F":
						note = "G7S";
						break;

					case "B8F":
						note = "A8S";
						break;

					default:
						note = splitLine[0].toString();
						break;
					}

					/////////////////////////////////////////
					// FIRST SWTICH ENDS SECOND SWITCH BEGINS
					/////////////////////////////////////////
					// Get note length in terms of a NoteLength variable
					switch (splitLine[1].toString()) {
					case "1":
						length = NoteLength.WHOLE;
						break;
					case "2":
						length = NoteLength.HALF;
						break;
					case "D2":
						length = NoteLength.DOTHALF;
						break;
					case "4":
						length = NoteLength.QUARTER;
						break;
					case "D4":
						length = NoteLength.DOTQUARTER;
						break;
					case "8":
						length = NoteLength.EIGTH;
						break;
					case "D8":
						length = NoteLength.DOTEIGTH;
						break;
					case "16":
						length = NoteLength.SIXTEENTH;
						break;
					default:
						// if there is not a valid input for NoteLength, flag that the song is invalid
						// and error print
						invalidLengthFlag = true;
						System.err.println("Invalid note length at line " + lineNum + " in " + filename);
					}

					// if the note in question is not a valid note notation, flag as invalid and
					// error print
					if (!allNotes.contains(note.toString())) {
						invalidNoteFlag = true;
						System.err.println("Invalid note at line " + lineNum + " in " + filename);
					}

					// add a new song with the corrected note string and the note length
					song.add(new BellNote(Note.valueOf(note), length));
				}
				// if the song file is empty, warn the user via console
				if (song.isEmpty()) {
					System.err.println("WARNING: This song file is empty and will not play any sound.");
				}
			}
			// if there is an invalid note or invalid note length, mark the song as invalid
			if (invalidLengthFlag || invalidNoteFlag) {
				validSong = false;
			}
			// return the song
			return song;
		} catch (IOException e) {
			// catch for file not found
			System.err.println("ERROR: Invalid File Path");
		}
		// if the file is not found, set song invalid and return null for the list of
		// bellnotes
		validSong = false;
		return null;
	}

	/**
	 * @desc Getter for the name of the song
	 * 
	 * @return The title of the song
	 */
	public String getName() {
		return name;
	}

	/**
	 * @desc Getter for the path of the song
	 * 
	 * @return The relative path of the song
	 */
	public String getRelativePath() {
		return filePath;
	}

	/**
	 * @desc Getter for the list of notes in the song so it may be played.
	 * 
	 * @return The list of bellnotes to be played.
	 */
	public List<BellNote> getBellNotes() {
		return notes;
	}

	/**
	 * @desc Getter for whether the song is valid.
	 * 
	 * @return whether or not the song is valid
	 */
	public boolean isValid() {
		return validSong;
	}
}
