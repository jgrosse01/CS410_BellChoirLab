package chamberChoir;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * @desc Class which is created to manage bellmen. Handles the assigning of
 *       bells and cues bellmen to play their notes.
 * 
 * @author Jake Grosse
 *
 */
public class Conductor {

	private final Song song;
	private final AudioFormat af;

	/**
	 * @desc Constructor for Conductor
	 * 
	 * @param song A Song object (list of BellNotes) to be played by this conductor
	 */
	Conductor(Song song) {
		// make the audioformat suggested, just in a slightly different place.
		this.af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, false);
		// assign the song
		this.song = song;
	}

	/**
	 * @desc A Conductor method to play the song. Assigns bellmen their bells then
	 *       cues them when they are to play and for how long.
	 */
	void playSong() {
		// Can the song be played
		if (song.isValid()) {
			// try to open the source data line from the audio format in use
			try (final SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
				// allow the line to accept data
				line.open();
				line.start();

				// make an empty array of bellmen for each note in Note.java
				final Bellman[] bellmen = new Bellman[Note.values().length];
				// assign these bellmen based on note and pass in the data line
				for (Note note : Note.values()) {
					bellmen[note.ordinal()] = new Bellman(note, line);
				}

				// for each note in the song
				for (BellNote noot : song.getBellNotes()) {
					// extract note and length from the bellnote
					Note note = noot.note;
					NoteLength len = noot.length;
					// the bellman which was assigned the note is given a turn for the note's length
					// of time
					bellmen[note.ordinal()].yourTurn(len);
				}

				// stop them, no need to kill the threads because the daemon value defined in
				// the Bellman constructor will force them to commit sepuku automagically! YAY!
				for (Bellman b : bellmen) {
					b.stopBellman();
				}

				// throw away the song when it's done being played
				line.drain();
			} catch (LineUnavailableException e) {
				// the AF for the dataline doesn't work, exit the program
				System.err.println("Fatal Error: Invalid SourceDataLine");
				System.exit(0);
			}
		} else {
			// print that the song is invalid if it was not valid
			System.out.println(
					"This song file is not valid. Choose another song or fix the errors in the file and restart the program.");
		}
	}

}
