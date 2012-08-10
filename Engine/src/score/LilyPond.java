package score;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.MidiSystem;

public class LilyPond {
	private LilyPond() {}
	
	public static void invokeLilyPond(String name) {
		String fileLocation = "data/out/" + name + ".ps";
        File ScoreFile = new File(fileLocation);
        if (!ScoreFile.exists()) {
    		try {
    			System.out.println("Invoking Lilypond");
				Process p = Runtime.getRuntime().exec("lilypond --png -dresolution=72 --ps --output=data/out/" + name + " data/ly/" + name + ".ly");
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
				if (PSIn.readLine().contains("EndSetup")) {
					break;
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
		
		}
		catch (java.io.FileNotFoundException e) {}
		catch (java.io.IOException e) {}
	    catch (InvalidMidiDataException e) {}
    

		return S;
	}
	
	/*
	public static PSContext parsePS(String name) {
		//Remove unnecessary data from the PS and order the notes
		PSContext PS = new PSContext();
		try {
			String in = "data/out/" + name + ".ps";
			String out = "data/out/" + name + "~.ps";
			BufferedReader PSByteIn = new BufferedReader(new InputStreamReader(new FileInputStream(in)));
			BufferedReader PSCharIn = new BufferedReader(new FileReader(in));
			
			BufferedWriter PSOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
			
			while (PSCharIn.ready()) {
				String S = PSCharIn.readLine();
				PSOut.write(S);
				if (S.contains("EndSetup")) {
					PSOut.newLine();
					break;
				}
				PSByteIn.skip(S.length());
				int c = PSByteIn.read();
				PSOut.write(c);
				if (c == '\r') {
					PSByteIn.mark(1);
					c = PSByteIn.read();
					if (c != '\n') {
						PSByteIn.reset();
					} else {
						PSOut.write(c);
					}
				}
			}
			
			PSByteIn.close();
			
			PSOut.write("gsave 0 paper-height translate set-ps-scale-to-lily-scale\n");
			
			String S1 = "", S2;
			while (PSCharIn.ready()) {
				S2 = PSCharIn.readLine();
				if (S2.contains("noteheads") || S2.contains("rests")) {
					//PSOut.write(S1);
					//PSOut.newLine();
					PSOut.write(S2);
					PSOut.newLine();
					String S[] = S2.split(" ");
					L.add(new Note(Double.parseDouble(S[0]), Double.parseDouble(S[1])));
					//System.out.println(S2);
				} else if (S2.contains("accidentals")) {
					PSOut.write(S2);
					PSOut.newLine();
				} else if (S2.contains("showpage")) {
					break;
				}
				S1 = S2;
			}
			
			PSCharIn.close();
			PSOut.close();
		
		}
		catch(java.io.FileNotFoundException e) {}
		catch(java.io.IOException e) {}

		return P;
	}
	*/
}
