package graphics.states;

import java.awt.Graphics2D;

import graphics.Window;
import graphics.states.ministates.HostGameState;
import graphics.states.ministates.JoinGameState;
import main.Game;
import ui.Button;
import ui.DefaultTheme;
import ui.Text;
import ui.UIManager;

public class MenuState extends StateManager {
	
	private Text titleText;
	private Button hostButton, joinButton, quitButton;
	
	private static float currentX = 0.0f, currentY = 0.0f;
	private static float gap = 50;

	public MenuState(Game game) {
		super(game);
		
		titleText = new Text(currentX, currentY, "Multipayer Shooter", DefaultTheme.defaultXLFont, 
				DefaultTheme.warmWhite, game);
		titleText.setX(Window.WIDTH/2 - titleText.getWidth()/2);
		titleText.setY(Window.HEIGHT/2 - titleText.getHeight()/2 - 200);
		
		currentX = titleText.getX();
		currentY = titleText.getY();
		
		currentY += titleText.getHeight() + gap*2;
		hostButton = new Button(currentX, currentY, "Host Game", DefaultTheme.defaultMediumFont, game);
		
		currentY += hostButton.getHeight() + gap;
		joinButton = new Button(currentX, currentY, "Join Game", DefaultTheme.defaultMediumFont, game);
		
		currentY += joinButton.getHeight() + gap;
		quitButton = new Button(currentX, currentY, "Quit", DefaultTheme.defaultMediumFont, game);
		
		UIManager.addButton(hostButton);
		UIManager.addButton(joinButton);
		UIManager.addButton(quitButton);
	}

	@Override
	public void update() {
		UIManager.update();
		
		if(hostButton.isMouseHold()) {
			UIManager.clearAll();
			
			StateManager.changeState(new HostGameState(game));
		}
		if(joinButton.isMouseHold()) {
			UIManager.clearAll();
			
			StateManager.changeState(new JoinGameState(game));
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		titleText.render(g2d);
		hostButton.render(g2d);
		joinButton.render(g2d);
		quitButton.render(g2d);
	}

}
