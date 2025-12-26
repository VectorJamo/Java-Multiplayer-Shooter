package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private static final int MAX_KEYS = 300;
	
	public static boolean[] keyState = new boolean[MAX_KEYS];

	private static char lastKeyTyped = 0; // For input boxes

	@Override
	public void keyTyped(KeyEvent e) {
		lastKeyTyped = e.getKeyChar();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyState[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyState[e.getKeyCode()] = false;
	}
	
	public static char popLastKeyTyped() {
		char keyChar = lastKeyTyped;
		lastKeyTyped = 0;
		
		return keyChar;
	}
}
