To run the engine, you need to install lilypond and be able to invoke it from the command line. You can get it at lilypond.org

When creating the project, jfugue-4.1.0-SNAPSHOT.jar and log4j-1.2.17.jar need to be set at dependancies.

run ScoreWriter.java to start the engine. In it is a line:
ScorePanel score = new ScorePanel(f, "MUSICNAME");
change MUSICNAME to change the target of the engine. There needs to be a file MUSICNAME.ly in data/ly/ for this to work. 
After running this the first time, it will create some output files in data/out/. Running it again will simply read the output files,
so if you change the input file and want to regenerate the output files, you need to delete them from data/out/

The engine does not support ties or repeats, and probably does not handle tempo changes very well. To test it out, press p and make sure the notes are correctly highlighted.



