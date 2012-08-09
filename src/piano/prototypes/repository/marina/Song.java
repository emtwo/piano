package piano.prototypes.repository.marina;

public class Song {
	private static final String FILE_EXTENSION = ".mid";
	
	public final String midiPath;
	public final String imagePath;
	public final String title;
	public final String author;
	public final String category;
	
	public Song(String midi, String image, String title, String author, String category) {
		this.midiPath  = midi;
		this.imagePath = image;
		this.title     = title;
		this.author    = author;
		this.category  = category;
	}
	
	public static String getFileName(String midiPath) {
		int slashIndex = midiPath.lastIndexOf("/");
		String file = midiPath.substring(slashIndex + 1);
		return file;
	}
	
	public static String getSongTitle(String midiPath) {
		String fileName = getFileName(midiPath);
		String choppedExtension = fileName.replaceAll(FILE_EXTENSION, "");
		return choppedExtension.replaceAll("_", " ");
	}
}
