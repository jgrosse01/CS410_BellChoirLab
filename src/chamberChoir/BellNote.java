package chamberChoir;

/**
 * @desc Medium of storing notes and their lengths conveniently together in one
 *       object.
 * 
 * @author Jake Grosse
 *
 */
public class BellNote {
	// instance variables
	final Note note;
	final NoteLength length;

	/**
	 * @desc Constructor for a BellNote
	 * 
	 * @param note   The note value ranging from A0 to C8
	 * @param length The length of the note in relation to a measure
	 */
	BellNote(Note note, NoteLength length) {
		this.note = note;
		this.length = length;
	}
}
