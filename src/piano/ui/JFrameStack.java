package piano.ui;

import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrameStack {
	private static JFrame frame;
	private static Stack<JPanel> stack = new Stack<JPanel>();

	private static JFrameStack frameStack;

	private JFrameStack(JFrame frame) {
		JFrameStack.frame = frame;
	}

	public static void setJFrameStack(JFrame frame) {
		if (frameStack == null && frame != null) {
			frameStack = new JFrameStack(frame);
		}
	}

	public static JFrameStack getJFrameStack() {
		return frameStack;
	}

	public static void pushPanel(JPanel nextPanel) {
		stack.push(nextPanel);
		nextPanel.setFocusable(true);
		nextPanel.requestFocusInWindow();
		nextPanel.setOpaque(true);
		frame.getContentPane().add(nextPanel, 0);
		//frame.setContentPane(nextPanel);
		validateNewView();
	}

	public static JPanel popPanel() {
		JPanel returnPanel = stack.pop();
		JPanel nextPanel = stack.peek();
		nextPanel.setFocusable(true);
		nextPanel.requestFocusInWindow();
		nextPanel.setOpaque(true);
		frame.getContentPane().add(nextPanel, 0);
		//frame.setContentPane(nextPanel);
		validateNewView();
		return returnPanel;
	}

	public static JFrame getFrame() {
		return frame;
	}

	private static void validateNewView() {
    //newContentPane.setOpaque(true);
    frame.validate();

    //Display the window.
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.repaint();
	}
}
