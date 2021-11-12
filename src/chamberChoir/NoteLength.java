package chamberChoir;

/**
 * @desc Enum to store the note length. That's about it.
 * 
 * @author Jake Grosse
 *
 */
public enum NoteLength {
	// note length values
	WHOLE(1.0f), HALF(0.5f), DOTHALF(0.75f), QUARTER(0.25f), DOTQUARTER(0.375f), EIGTH(0.125f), DOTEIGTH(0.1875f), SIXTEENTH(0.0625f), THIRTYSECOND(0.03125f);

	// ms time of the note length value
	private final int timeMs;

	// constructs note length
	private NoteLength(float length) {
		timeMs = (int) (length * Note.MEASURE_LENGTH_SEC * 1000);
	}

	/**
	 * @desc Get the time in milliseconds that a note length will take to play.
	 * 
	 * @return The time in MS that a note with the given length will take to play.
	 */
	public int timeMs() {
		return timeMs;
	}
}
