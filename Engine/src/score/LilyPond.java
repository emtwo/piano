package score;

import score.Score;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LilyPond {
	private LilyPond() {}
	
	public static void invokeLilyPond(String name) {
		String fileLocation = "data/out/" + name + ".ps";
        File ScoreFile = new File(fileLocation);
        if (!ScoreFile.exists()) {
    		try {
                String command = "lilypond --png -dresolution=72 --ps --output=data/out/" + name + " data/ly/" + name + ".ly";
                System.out.println("Invoking Lilypond: " + command);
				Process p = Runtime.getRuntime().exec(command);
				p.waitFor();
			} 
			catch (java.io.IOException e1) {
				System.out.println(e1.getMessage());
			}
			catch(InterruptedException e2) {
				System.out.println(e2.getMessage());
			}
    		ScoreFile = new File(fileLocation);
    		if (!ScoreFile.exists()) {
    			System.err.println("Failed to create score image");
    		}
        }
	}
	
	public static Score parseScore(String name) {
		//Remove unnecessary data from the PS and order the notes
		Score S = new Score(name);
		try {
			
			//parse lilypond
			BufferedReader LYIn = new BufferedReader(new FileReader("data/ly/" + name + ".ly"));
			while (LYIn.ready()) {
				S.parseLY(LYIn.readLine());
			}
			S.finishLy();
			LYIn.close();
			
			//parse postscript
			BufferedReader PSIn = new BufferedReader(new FileReader("data/out/" + name + ".ps"));
			//skip header
			while (PSIn.ready()) {
                String line = PSIn.readLine();
				if (line.contains("EndSetup")) {
                    S.finishHeader();
					break;
				} else {
					S.parseHeader(line);
				}
			}

			while (PSIn.ready()) {
				S.parsePS(PSIn.readLine());
			}
			S.finishPS();
			
			PSIn.close();
			
			//parse midi
	        Sequence sequence = MidiSystem.getSequence(new File("data/out/" + name + ".midi"));
			S.parseMidi(sequence);
            S.finishMidi();
		
		}
		catch (java.io.FileNotFoundException e) {}
		catch (java.io.IOException e) {}
	    catch (InvalidMidiDataException e) {}
    

		return S;
	}

}
