package piano.engine;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.*;

public class LilyPond {

	private LilyPond() {}

    private static void exec(String command) {
        try {
            System.out.println("Invoking: " + command);
            Process p = Runtime.getRuntime().exec(command);

            //get standard output
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();

            //get error output
            input = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();

            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

	public static void invokeLilyPond(String name) {
		String fileLocation = "data/out/" + name + ".ps";
        File ScoreFile = new File(fileLocation);
        if (!ScoreFile.exists()) {
            exec("convert-ly -e data/ly/" + name + ".ly");
            exec("lilypond --png -dresolution=72 --ps --output=data/out/" + name + " data/ly/" + name + ".ly");
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

            //Allow for alternate file extensions of midi files.
            File midiFile = new File("data/out/" + name + ".midi");
            File altFile = new File("data/out/" + name + ".mid");

            if (!midiFile.exists() && altFile.exists()) {
                midiFile = altFile;
            }

			//parse midi
	        Sequence sequence = MidiSystem.getSequence(midiFile);
			S.parseMidi(sequence);
            S.finishMidi();

		} catch (Exception e) {
            System.out.println("Parsing the score from Lilypond's output has failed. Error: ");
            e.printStackTrace();
        }

		return S;
	}
}
