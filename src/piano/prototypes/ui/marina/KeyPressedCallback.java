package piano.prototypes.ui.marina;

public interface KeyPressedCallback {
	public void informKeyPressed(String keyPressed);
	public void informExitLoop();
	public void clearKeys();
}
