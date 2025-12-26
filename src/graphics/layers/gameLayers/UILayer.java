package graphics.layers.gameLayers;

import java.awt.Color;
import java.awt.Graphics2D;

import entities.Player;
import graphics.layers.Layer;

import main.*;
import math.vec2;
import networking.client.HealthStates;
import networking.utilities.GeneralInfo;
import ui.DefaultTheme;
import ui.Text;
import ui.UIManager;

public class UILayer extends Layer {
	
	private Text text;
	
	private float healthBarX, healthBarY;
	private float healthBarWidth;
	private float healthBarHeight;

	public UILayer(Game game) {
		super(game, "uiLayer");
		
		text = new Text(5.0f, 10.0f, "Health:", DefaultTheme.defaultMediumFont, DefaultTheme.defaultTextColor, game);
		healthBarX = text.getWidth() + 10.0f;
		healthBarY = 10.0f;
		
		healthBarWidth = 100.0f;
		healthBarHeight = text.getHeight();
	}

	@Override
	public void update() {
		UIManager.update();
	}

	@Override
	public void render(Graphics2D g2d) {
		text.render(g2d);
		
		if(GeneralInfo.isServer) {
			g2d.setColor(DefaultTheme.crimsonRed);
			g2d.fillRect((int)healthBarX, (int)healthBarY, (int)Player.playerHealth, (int)healthBarHeight);
			
			g2d.setColor(DefaultTheme.warmWhite);
			g2d.drawRect((int)healthBarX, (int)healthBarY, (int)healthBarWidth, (int)healthBarHeight);
		}else {
			g2d.setColor(DefaultTheme.crimsonRed);
			g2d.fillRect((int)healthBarX, (int)healthBarY, (int)HealthStates.clientHealth, (int)healthBarHeight);
			
			g2d.setColor(DefaultTheme.warmWhite);
			g2d.drawRect((int)healthBarX, (int)healthBarY, (int)healthBarWidth, (int)healthBarHeight);
		}
	}
}
