package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import input.MouseManager;
import main.Game;

public class Button extends UIElement {
	
	private float paddingX, paddingY;
	private boolean isMouseOver = false;
	private boolean isHold = false;
	private boolean isClicked = false; 

	private Text buttonText;
	
	private Color buttonColor, buttonTextColor, buttonOutlineColor;
	private Color buttonHoverColor, buttonHoverTextColor, buttonHoverOutlineColor;
	private int outlineThickness = 2;

	public Button(float x, float y, String text, Font font, Game game) {
		super(x, y, game);
		
		// Text
		buttonText = new Text(x, y, text, font, DefaultTheme.defaultButtonTextColor, game);

		// Dimensions
		paddingX = 10.0f;
		paddingY = 10.0f;
		
		this.width = buttonText.getWidth() + paddingX*2;
		this.height = buttonText.getHeight() + paddingY*2;
		
		// Colors
		this.buttonColor = DefaultTheme.defaultButtonColor;
		buttonTextColor = DefaultTheme.defaultButtonTextColor;
		buttonOutlineColor = DefaultTheme.defaultButtonOutlineColor;
		buttonHoverColor = DefaultTheme.defaultButtonHoverColor;
		buttonHoverTextColor = DefaultTheme.defaultButtonHoverTextColor;
		buttonHoverOutlineColor = DefaultTheme.defaultButtonHoverOutlineColor;
		
		buttonText.setX(x + paddingX);
		buttonText.setY(y + paddingY);
		
		UIManager.addButton(this);
	}

	@Override
	public void update() {
		if(MouseManager.mouseX > this.x && MouseManager.mouseX < this.x + this.width
				&& MouseManager.mouseY > this.y && MouseManager.mouseY < this.y + this.height) {

			isMouseOver = true;
			if(MouseManager.buttonState[MouseEvent.BUTTON1]) {
				if(!isHold) {
					isClicked = true;
				}else {
					isClicked = false;
				}
				isHold = true;
			}else {
				isHold = false;
				isClicked = false;
			}
		}else {
			isMouseOver = false;
			isHold = false;
			isClicked = false;
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		if(!isMouseOver) {
			// Draw the background
			g2d.setColor(buttonColor);
			g2d.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
			
			// Draw the text
			buttonText.setColor(buttonTextColor);
			buttonText.render(g2d);
			
			// Draw the outline
			g2d.setColor(buttonOutlineColor);
			g2d.setStroke(new BasicStroke(outlineThickness));
			g2d.drawRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
		}else {
			// Draw the background
			g2d.setColor(buttonHoverColor);
			g2d.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
			
			// Draw the text
			buttonText.setColor(buttonHoverTextColor);
			buttonText.render(g2d);
			
			// Draw the outline
			g2d.setColor(buttonHoverOutlineColor);
			g2d.setStroke(new BasicStroke(outlineThickness));
			g2d.drawRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
		}
	}
	
	public boolean isMouseOver() {
		return isMouseOver;
	}
	public boolean isMouseHold() {
		return isHold;
	}	
	public boolean isMouseClicked() {
		return isClicked;
	}
	
	public Color getButtonColor() {
		return buttonColor;
	}
	
	public void setButtonHoverOutlineColor(Color buttonHoverOutlineColor) {
		this.buttonHoverOutlineColor = buttonHoverOutlineColor;
	}

	public void setButtonColorProfile(Color buttonColor, Color buttonTextColor, Color buttonOutlineColor) {
		setButtonColor(buttonColor);
		setButtonTextColor(buttonTextColor);
		setButtonOutlineColor(buttonOutlineColor);
	}
	public void setButtonHoverColorProfile(Color buttonHoverColor, Color buttonHoverTextColor, Color buttonHoverOutlineColor) {
		setButtonHoverColor(buttonHoverColor);
		setButtonHoverTextColor(buttonHoverTextColor);
		setButtonHoverOutlineColor(buttonHoverOutlineColor);
	}

	public void setButtonColor(Color buttonColor) {
		this.buttonColor = buttonColor;
	}

	public Color getButtonTextColor() {
		return buttonTextColor;
	}

	public void setButtonTextColor(Color buttonTextColor) {
		this.buttonTextColor = buttonTextColor;
		
		this.buttonText.setColor(this.buttonTextColor);
	}

	public Color getButtonOutlineColor() {
		return buttonOutlineColor;
	}

	public void setButtonOutlineColor(Color buttonOutlineColor) {
		this.buttonOutlineColor = buttonOutlineColor;
	}
	public Color getButtonHoverColor() {
		return buttonHoverColor;
	}

	public void setButtonHoverColor(Color buttonHoverColor) {
		this.buttonHoverColor = buttonHoverColor;
	}

	public Color getButtonHoverTextColor() {
		return buttonHoverTextColor;
	}

	public void setButtonHoverTextColor(Color buttonHoverTextColor) {
		this.buttonHoverTextColor = buttonHoverTextColor;
	}

	public Color getButtonHoverOutlineColor() {
		return buttonHoverOutlineColor;
	}
	public int getOutlineThickness() {
		return outlineThickness;
	}

	public void setOutlineThickness(int outlineThickness) {
		this.outlineThickness = outlineThickness;
	}
}
