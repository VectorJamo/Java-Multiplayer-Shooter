package graphics.states.ministates;

import java.awt.Graphics2D;
import java.io.IOException;
import java.net.SocketException;

import entities.Player;
import graphics.Window;
import graphics.states.GameState;
import graphics.states.MenuState;
import graphics.states.StateManager;
import main.Game;
import networking.client.Client;
import networking.utilities.GeneralInfo;
import ui.Button;
import ui.DefaultTheme;
import ui.InputBox;
import ui.Text;
import ui.UIManager;

public class JoinGameState extends StateManager {
	
	private Text titleText;
	
	private static float currentX = 0.0f, currentY = 0.0f;
	private static float gap = 50;
	
	private Text nameText;
	private InputBox nameBox;
	
	private Text ipText;
	private InputBox ipBox;

	private Button playButton, backButton;
	
	// Networking
	private Client client;

	public JoinGameState(Game game) {
		super(game);
		
		titleText = new Text(currentX, currentY, "Join Game", DefaultTheme.defaultXLFont, 
				DefaultTheme.warmWhite, game);
		titleText.setX(Window.WIDTH/2 - titleText.getWidth()/2);
		titleText.setY(Window.HEIGHT/2 - titleText.getHeight()/2 - 200);

		currentX = Window.WIDTH/2 - titleText.getWidth()/2;
		currentY = Window.HEIGHT/2 - titleText.getHeight()/2 - 200;
		
		currentY += titleText.getHeight() + gap;
		nameText = new Text(currentX, currentY, "Enter your name", DefaultTheme.defaultMediumFont, 
				DefaultTheme.warmWhite, game);
		
		currentY += nameText.getHeight() + gap/2;
		nameBox = new InputBox(currentX, currentY, 200.0f, DefaultTheme.defaultMediumFont, game);
		
				
		currentY += nameBox.getHeight() + gap;
		ipText = new Text(currentX, currentY, "Enter server's IP:", DefaultTheme.defaultMediumFont, 
				DefaultTheme.warmWhite, game);
		
		currentY += ipText.getHeight() + gap/2;
		ipBox = new InputBox(currentX, currentY, 200.0f, DefaultTheme.defaultMediumFont, game);
		
		currentY += ipBox.getHeight() + gap;
		playButton = new Button(currentX, currentY, "Play", DefaultTheme.defaultMediumFont, game);

		currentX += playButton.getWidth() + gap*2;
		backButton = new Button(currentX, currentY, "Back", DefaultTheme.defaultMediumFont, game);
		
		
		UIManager.addButton(backButton);
		UIManager.addInputBox(nameBox);
	}

	@Override
	public void update() {
		UIManager.update();
		
		if(backButton.isMouseHold()) {
			UIManager.clearAll();
			
			changeState(new MenuState(game));
		}
		if(playButton.isMouseHold()) {
			// Get the values
			String playerName = nameBox.getText();
			String serverIP = ipBox.getText();
			
			GeneralInfo.isServer = false;
			Player.setName(playerName);
			
			// Initiate connection with the server
			try {
				client = new Client();
			} catch (SocketException e) {
				e.printStackTrace();
			}
			try {
				System.out.println("Waiting for connection with the server....");
				client.initiateConnectionWithServer(playerName, serverIP); // Blocking
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			UIManager.clearAll();
			
			changeState(new GameState(game));
		}

	}

	@Override
	public void render(Graphics2D g2d) {
		titleText.render(g2d);
		
		nameText.render(g2d);
		nameBox.render(g2d);
		
		ipText.render(g2d);
		ipBox.render(g2d);
		
		playButton.render(g2d);
		backButton.render(g2d);
	}

}
