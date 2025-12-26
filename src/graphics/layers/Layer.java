package graphics.layers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import main.Game;

public abstract class Layer {
	
	public static HashMap<String, Layer> currentLayers = new HashMap<String, Layer>();
	
	protected Game game;
	public int layerName;
	
	public Layer(Game game, String layerName) {
		this.game = game;
		
		currentLayers.put(layerName, this);
	};
	
	public abstract void update();
	public abstract void render(Graphics2D g2d);
}
