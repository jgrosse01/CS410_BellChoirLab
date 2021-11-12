package chamberChoir;

import java.util.Scanner;
import java.io.File;

/**
 * @desc A "main-type" class which handles making conductors and importing
 *       songs. Acts as a console based jukebox.
 * 
 * @author Jake Grosse
 *
 */
public class Bellbox {
	// private list of songs
	private static Song[] songs;

	/**
	 * @desc Manages songs and their playing within the console.
	 * 
	 * @param args Unused
	 */
	public static void main(String[] args) {
		loadSongs();

		// flag to exit the program
		boolean exit = false;
		// create scanner
		Scanner userIn = new Scanner(System.in);

		System.out.println("Welcome to the Bellbox!");
		System.out.println("It's a jukebox, but for a bell choir!");

		// if the user doesn't want to exit, keep prompting for songs
		while (!exit) {
			int songNum = 0;
			System.out.println("------------------------------------------------");
			System.out.println("Pick a song to play (Enter a number to select): ");
			System.out.println("You may exit at any time by typing -1.");

			// list the songs in the songs array
			for (Song s : songs) {
				System.out.println(++songNum + ") " + s.getName());
			}

			// user prompt for song choice and input handling
			System.out.print("Enter your song choice: ");
			if (userIn.hasNextInt()) {
				int sIndex = userIn.nextInt();
				// exit condition
				if (sIndex == -1) {
					exit = true;
					break;
				}
				// valid number check
				else if (sIndex <= songs.length && sIndex > 0) {
					new Conductor(songs[sIndex - 1]).playSong();
				} else {
					System.out.println("Invalid Song Selection. Please Choose An Integer From The List.");
				}
			} else {
				System.out.println("Please input an INTEGER included in the list of songs.");
			}
		}
		// close scanner and goodbye message
		userIn.close();
		System.out.println("Shutting Down. Bye bye!");
	}

	/**
	 * @desc This method loads the songs into the list of songs for the "Bellbox"
	 *       This had influence from Jaden, but the code was not copied, just
	 *       explained briefly over voice chat. This took me a LOOOOONG time to get
	 *       working properly...
	 */
	protected static void loadSongs() {
		// go to the "songs" folder
		File directory = new File("songs");

		// if that folder doesn't exist, print error and exit
		if (!directory.exists()) {
			System.err.println("Missing songs directory. Please verify file integrity.");
			System.exit(0);
		}

		// make an array of the files in the folder
		File[] songFiles = directory.listFiles();
		// initialize the list of songs to an empty array of type Song that is the
		// length of the number of files in the folder
		songs = new Song[songFiles.length];

		// load the songs
		for (int i = 0; i < songFiles.length; i++) {
			// name and path stored
			String name = songFiles[i].getName();
			String path = songFiles[i].getPath();

			// final song name to create the Song variable
			String songName;

			// remove extension if there is one
			int extensionI = name.lastIndexOf(".");
			if (extensionI != -1) {
				songName = name.substring(0, extensionI);
			} else {
				songName = name;
			}

			// place the new song into the array of songs
			songs[i] = new Song(songName, path);
		}
	}

}
