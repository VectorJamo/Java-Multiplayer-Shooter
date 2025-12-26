package graphics.states;

import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;
import ui.UIManager;

public abstract class StateManager {
	
	public static StateManager currentState = null;
	public static boolean hasStateChanged = false;

	protected static Game game;
	
	public StateManager(Game game) {
		this.game = game;
	}
	
	public abstract void update();
	public abstract void render(Graphics2D g2d);
	
	public static void changeState(StateManager newState) {		
		hasStateChanged = true;
		currentState = newState;
	}
}
