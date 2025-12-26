package main;

import java.awt.*;
import java.awt.image.BufferStrategy;

import graphics.Window;
import graphics.states.*;
import input.KeyManager;
import input.MouseManager;
import networking.client.Client;
import networking.server.Server;
import networking.utilities.GeneralInfo;
import ui.DefaultTheme;

public class Game extends Canvas implements Runnable {
	
	public static final String GAME_TITLE = "Multiplayer Shooter";
	private Window window;
	private BufferStrategy bs;
	
	private Thread gameThread;
	private boolean running = true;
	
	private double lastTime = 0.0, deltaTime = 0.0; // In seconds
	private double fpsLogTimer = 0.0;
	
	public Game() {
		this.setMinimumSize(new Dimension(Window.WIDTH, Window.HEIGHT));
		this.setPreferredSize(new Dimension(Window.WIDTH, Window.HEIGHT));
		this.setMaximumSize(new Dimension(Window.WIDTH, Window.HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new KeyManager());
		this.addMouseListener(new MouseManager());
		this.addMouseMotionListener(new MouseManager());
		
		window = new Window();
		
		window.add(this);
		window.pack();
		
		startGame();
	}
	
	private void startGame() {
		if(gameThread != null)
			return;
		
		this.createBufferStrategy(2);
		bs = this.getBufferStrategy();
				
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stopGame() {
		if(gameThread == null)
			return;
		
		running = false;
		System.exit(0);
	}
	
	public void init() {
		StateManager.currentState = new MenuState(this);
	}
	
	@Override
	public void run() {
		init();
		lastTime = System.nanoTime()/1000000000.0;
		while(running) {
			double currentTime = System.nanoTime()/1000000000.0;
			deltaTime = currentTime - lastTime;
			lastTime = currentTime;
			
			update();
			
			render();
		}
	}

	private void update() {
		StateManager.currentState.update();
		
		if(GeneralInfo.isServer) {
			Server.update();
		}else {
			Client.update();
		}
		
		//logFPS(2);
	}
	
	private void render() {
		if(StateManager.hasStateChanged) {
			StateManager.hasStateChanged = false;
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		
		// Enable anti-aliasing for text
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Optional: enable general anti-aliasing for shapes too
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
				
		// Clear the back buffer
		g2d.setColor(DefaultTheme.warmBlack);
		g2d.clearRect(0, 0, Window.WIDTH, Window.HEIGHT);
		
		StateManager.currentState.render(g2d);
		
		g2d.dispose();
		
		// Swap buffers
		bs.show();
	}
	
	public double getDeltaTime() {
		return deltaTime;
	}
	public int getFPS() {
		return (int)(1.0 / deltaTime);
	}
	
	public void logFPS(int frequencyInSeconds) {
		fpsLogTimer += deltaTime;
		if(fpsLogTimer >= frequencyInSeconds) {
			System.out.println("FPS: " + getFPS());
			fpsLogTimer = 0.0;
		}
	}
}
