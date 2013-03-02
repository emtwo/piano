package piano.ui;

import piano.ui.buttons.ButtonType;

public interface ButtonClickCallback {
	public void informButtonClicked(ButtonType buttonType, int buttonId);
}
