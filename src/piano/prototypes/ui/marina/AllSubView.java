package piano.prototypes.ui.marina;

import javax.swing.JFrame;

public class AllSubView extends SubView {
	
	public AllSubView(JFrame parentFrame) {
		super(null, parentFrame);
		currentView = songView;
	}
	
	@Override
	public boolean hasBack() {
		return false;
	}
}
