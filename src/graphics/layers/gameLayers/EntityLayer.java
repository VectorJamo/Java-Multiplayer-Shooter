package graphics.layers.gameLayers;

import java.awt.*;
import java.awt.event.KeyEvent;

import entities.*;
import graphics.layers.Layer;
import input.*;
import main.*;

public class EntityLayer extends Layer {
	
	private Player player;
	private AnotherPlayer anotherPlayer;
	
	public EntityLayer(Game game) {
		super(game, "entityLayer");
		
		player = new Player(100.0f, 100.0f, 50.0f, 50.0f, game, ENTITIES.Player);
		anotherPlayer = new AnotherPlayer(100.0f,100.0f, 50.0f, 50.0f, game, ENTITIES.Player);
	}

	@Override
	public void update() {
		player.update();
	}

	@Override
	public void render(Graphics2D g2d) {
		player.render(g2d);
	}
}
