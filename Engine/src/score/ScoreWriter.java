package score;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ScoreWriter implements Runnable{
	
	public void run() {
        JFrame f = new JFrame ("Score Viewer");
        ScorePanel score = new ScorePanel(f, "prelude");
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        
        f.add(score);


        // arrange the components inside the window
        f.pack();
        //By default, the window is not visible. Make it visible.
        f.setVisible(true);

	}
	
	public static void main(String[] args) {

		ScoreWriter score = new ScoreWriter();
		SwingUtilities.invokeLater(score);

	}
}
