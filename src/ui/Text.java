package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import main.Game;

public class Text extends UIElement {
	
	private String text;

	private Font font;
	private FontMetrics fontMetrics;
	private Color color;

	public Text(float x, float y, String text, Font font, Color color, Game game) {
		super(x, y, game);
		
		this.text = text;
		this.font = font;
		this.color = color;
		
		fontMetrics = (game.getBufferStrategy().getDrawGraphics()).getFontMetrics(font);
		
		this.width = fontMetrics.stringWidth(text);
		this.height = fontMetrics.getAscent() + fontMetrics.getDescent();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.setFont(font);
		
		int ascent = fontMetrics.getAscent();
		g2d.drawString(text, (int)this.x, (int)(this.y + ascent));
	}
	
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		fontMetrics = (game.getBufferStrategy().getDrawGraphics()).getFontMetrics(font);

		this.width = fontMetrics.stringWidth(text);
		this.height = fontMetrics.getHeight();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;

		this.width = fontMetrics.stringWidth(text);
	}
}
