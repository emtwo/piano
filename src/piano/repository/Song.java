package piano.repository;

public class Song {
	private static final String FILE_EXTENSION = ".mid";

	public final String title;
	public final String subtitle;
	public final String composer;
	public final String style;
	public final String date;
	public final String copyright;
	public final String name;

	public Song(String title, String subtitle, String composer, String style, String date, String copyright, String name) {
		this.title  = title;
		this.subtitle = subtitle;
		this.composer = composer;
		this.style = style;
		this.date = date;
		this.copyright = copyright;
		this.name = name;
	}
}
