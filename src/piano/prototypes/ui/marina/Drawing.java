package piano.prototypes.ui.marina;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Drawing extends JPanel implements MouseMotionListener, MouseListener {

	String id;

  public Drawing() {
   this(800, 800);
  }

	public Drawing(int width, int height) {
		super(new GridLayout(15,15));

		//Register for mouse events on blankArea and the panel.
    addMouseMotionListener(this);
    addMouseListener(this);

    setPreferredSize(new Dimension(width, height));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	public void switchToView(JFrame parentFrame) {
		//Create and set up the content pane.
    JComponent newContentPane = this;
    newContentPane.setOpaque(true); //content panes must be opaque
    parentFrame.add(newContentPane, 0);
    parentFrame.validate();

    //Display the window.
    parentFrame.pack();
    parentFrame.setLocationRelativeTo(null);
    parentFrame.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.repaint();
	}
}
