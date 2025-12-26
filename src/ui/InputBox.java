package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import input.KeyManager;
import input.MouseManager;
import main.Game;

public class InputBox extends UIElement {
	
	private float paddingX, paddingY;
	private Text inputText;
	private boolean isActive = false;
	
	private Color inputBoxColor, inputBoxTextColor, inputBoxOutlineColor;
	private Color inputBoxActiveColor, inputBoxActiveTextColor, inputBoxActiveOutlineColor;
	private int outlineThickness = 2;

	public InputBox(float x, float y, float width, Font font, Game game) {
		super(x, y, width, 0, game);
		
		paddingX = 5.0f;
		paddingY = 5.0f;

		inputText = new Text(x + paddingX, y + paddingY, "", font, DefaultTheme.warmBlack, game);
		this.height = inputText.getHeight() + 2*paddingY;
		this.width = this.width + 2*paddingX;
		
		inputBoxColor = DefaultTheme.defaultInputBoxColor;
		inputBoxTextColor = DefaultTheme.defaultInputBoxTextColor;
		inputBoxOutlineColor = DefaultTheme.defaultInputBoxOutlineColor;
		inputBoxActiveColor = DefaultTheme.defaultInputBoxColor;
		inputBoxActiveTextColor = DefaultTheme.defaultInputBoxTextColor;
		inputBoxActiveOutlineColor = DefaultTheme.defaultInputBoxOutlineColor;
		
		inputText.setColor(inputBoxTextColor);
		
		UIManager.addInputBox(this);
	}


	@Override
	public void update() {
		if(MouseManager.mouseX > this.x && MouseManager.mouseX < this.x + this.width
				&& MouseManager.mouseY > this.y && MouseManager.mouseY < this.y + this.height) {
			
			if(MouseManager.buttonState[MouseEvent.BUTTON1]) {
				isActive = true;
				inputText.setColor(inputBoxActiveTextColor);
			}
		}else {
			if(MouseManager.buttonState[MouseEvent.BUTTON1]) {
				isActive = false;
				inputText.setColor(inputBoxTextColor);
			}
		}
		
		if(isActive) {
			char typedChar = KeyManager.popLastKeyTyped();
			if(typedChar != 0 && typedChar != '\b') {
				if(inputText.getWidth()+inputText.getFont().getSize()+paddingX*2 < this.width) {
					String currentText = inputText.getText();
					currentText += typedChar;
					inputText.setText(currentText);
				}
			}else if(typedChar == '\b') {
				if(inputText.getText().length() > 0) {
					String currentText = inputText.getText();
					currentText = currentText.substring(0, currentText.length()-1);
					inputText.setText(currentText);
				}
			}
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		if(isActive) {
			// Render the background
			g2d.setColor(inputBoxActiveColor);
			g2d.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
			
			// Render the text
			inputText.render(g2d);
			
			// Render the outline
			g2d.setStroke(new BasicStroke(outlineThickness));
			g2d.setColor(inputBoxActiveOutlineColor);
			g2d.drawRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
			
			// Render the text pointer
			g2d.fillRect((int)(inputText.getX() + inputText.getWidth()), (int)(this.y + paddingY), 5, (int)(this.height - 2*paddingY));
		}else {
			// Render the background
			g2d.setColor(inputBoxColor);
			g2d.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
			
			// Render the text
			inputText.render(g2d);
			
			// Render the outline
			g2d.setStroke(new BasicStroke(outlineThickness));
			g2d.setColor(inputBoxOutlineColor);
			g2d.drawRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
		}
	}
	
	public String getText() {
		return inputText.getText();
	}
}
