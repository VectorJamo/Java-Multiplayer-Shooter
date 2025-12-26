package graphics.states;

import java.awt.Graphics2D;

import audio.audioHandler.SoundEffects;
import graphics.layers.gameLayers.EntityLayer;
import graphics.layers.gameLayers.ObjectLayer;
import graphics.layers.gameLayers.UILayer;
import main.Game;

public class GameState extends StateManager {
	
	public EntityLayer entityLayer;
	public ObjectLayer objectLayer;
	public UILayer uiLayer;
	
	public GameState(Game game) {
		super(game);
		
		SoundEffects.initSounds();
		
		entityLayer = new EntityLayer(game);
		objectLayer = new ObjectLayer(game);
		uiLayer = new UILayer(game);
	}

	@Override
	public void update() {
		entityLayer.update();
		objectLayer.update();
		uiLayer.update();
	}

	@Override
	public void render(Graphics2D g2d) {
		entityLayer.render(g2d);
		objectLayer.render(g2d);
		uiLayer.render(g2d);
		
		
	}
}
