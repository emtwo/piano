package piano.prototypes.ui.marina;

public interface KeyPressedCallback {
	public void informKeyPressed(int keyPressed);
	public void informExitLoop();
	public void clearKeys();
}
