package chamberChoir;

/**
 * @desc Enum to store all of the notes and their frequencies Also keeps sample
 *       rate, measure length, and the ability to sample.
 * 
 * @author Jake Grosse with influence from Nate Williams
 *
 */
public enum Note {
	// REST Must be the first 'Note'
	REST,
	// All notes on a keyboard
	A0, A0S, B0, C0, C0S, D0, D0S, E0, F0, F0S, G0, G0S, A1, A1S, B1, C1, C1S, D1, D1S, E1, F1, F1S, G1, G1S, A2, A2S,
	B2, C2, C2S, D2, D2S, E2, F2, F2S, G2, G2S, A3, A3S, B3, C3, C3S, D3, D3S, E3, F3, F3S, G3, G3S, A4, A4S, B4, C4,
	C4S, D4, D4S, E4, F4, F4S, G4, G4S, A5, A5S, B5, C5, C5S, D5, D5S, E5, F5, F5S, G5, G5S, A6, A6S, B6, C6, C6S, D6,
	D6S, E6, F6, F6S, G6, G6S, A7, A7S, B7, C7, C7S, D7, D7S, E7, F7, F7S, G7, G7S, A8, A8S, B8, C8;

	public static final int SAMPLE_RATE = 192 * 1024; // ~192KHz //HIGHER SAMPLE RATE BECAUSE BETTER SOUND QUALITY
	public static final int MEASURE_LENGTH_SEC = 2;

	// Circumference of a circle divided by # of samples
	private static final double step_alpha = (2.0d * Math.PI) / SAMPLE_RATE;

	// changed to be hz for A0
	private final double FREQUENCY_A_HZ = 27.5d;
	// lower max volume
	private final double MAX_VOLUME = 30.0d;

	private final byte[] sinSample = new byte[MEASURE_LENGTH_SEC * SAMPLE_RATE];

	// make a note
	private Note() {
		// order the note
		int n = this.ordinal();
		// if its not a rest, calculate the frequency
		if (n > 0) {
			// Calculate the frequency!
			final double halfStepUpFromA = n - 1;
			final double exp = halfStepUpFromA / 12.0d;
			final double freq = FREQUENCY_A_HZ * Math.pow(2.0d, exp);

			// Create sinusoidal data sample for the desired frequency
			final double sinStep = freq * step_alpha;
			for (int i = 0; i < sinSample.length; i++) {
				sinSample[i] = (byte) (Math.sin(i * sinStep) * MAX_VOLUME);
			}
		}
	}

	/**
	 * @desc Gets a sound sample byte array to play over speakers.
	 * 
	 * @return byte array of frequencies to play the note
	 */
	public byte[] sample() {
		return sinSample;
	}
}
