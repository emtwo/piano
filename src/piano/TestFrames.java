package piano;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import piano.ui.PianoUI;

public class TestFrames {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Piano Like a Pro");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    TestJPanelChild panel1 = new TestJPanelChild(Color.RED);
    panel1.setOpaque(true);
    panel1.setPreferredSize(new Dimension(800, 800));

    TestJPanelChild panel2 = new TestJPanelChild(Color.BLUE);
    panel2.setOpaque(true);
    panel2.setPreferredSize(new Dimension(800, 800));

    TestJPanelChild panel3 = new TestJPanelChild(Color.GREEN);
    panel3.setOpaque(true);
    panel3.setPreferredSize(new Dimension(800, 800));

    frame.setContentPane(panel1);
    //frame.getContentPane().add(panel2);
    //frame.getContentPane().add(panel3);

    System.out.println("frame stack length " + frame.getContentPane().countComponents());

    //frame.getContentPane().remove(2);
    System.out.println("frame stack length " + frame.getContentPane().countComponents());
    frame.validate();

    //Display the window.
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.repaint();
	}
}
