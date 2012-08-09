package piano.prototypes.ui.marina;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class View extends JPanel implements MouseMotionListener, MouseListener {

	public View() {
		super(new GridLayout(15,15));
		
		//Register for mouse events on blankArea and the panel.
    addMouseMotionListener(this);
    addMouseListener(this);
    
    setPreferredSize(new Dimension(800, 800));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	public void switchToView(JFrame parentFrame) {
		//Create and set up the content pane.
    JComponent newContentPane = this;
    newContentPane.setOpaque(true); //content panes must be opaque
    parentFrame.setContentPane(newContentPane);
     
    //Display the window.
    parentFrame.pack();
    parentFrame.setVisible(true);
	}

	
  void eventOutput(String eventDescription, MouseEvent e) {
    System.out.println(eventDescription + " detected on "
            + e.getComponent().getClass().getName()
            + ".");
	}
	 
	public void mousePressed(MouseEvent e) {
	    eventOutput("Mouse pressed (# of clicks: "
	            + e.getClickCount() + ")", e);
	}
	 
	public void mouseReleased(MouseEvent e) {
	    eventOutput("Mouse released (# of clicks: "
	            + e.getClickCount() + ")", e);
	}
	 
	public void mouseEntered(MouseEvent e) {
	    eventOutput("Mouse entered", e);
	}
	 
	public void mouseExited(MouseEvent e) {
	    eventOutput("Mouse exited", e);
	}
	 
	public void mouseClicked(MouseEvent e) {
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		eventOutput("Mouse exited", e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.repaint();
	}
}
