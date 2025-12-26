package ui;

import java.awt.Color;
import java.awt.Font;

public class DefaultTheme {
	
	// Colors
	public static final Color warmWhite = new Color(0.9f, 0.9f, 0.9f, 1.0f);
	public static final Color warmBlack = new Color(0.1f, 0.1f, 0.1f, 1.0f);
	public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color crimsonRed = new Color(0.9f, 0.05f, 0.1f);
	
	// Text
	public static final Font defaultSmallFont = new Font("Courier New", Font.BOLD, 18);
	public static final Font defaultMediumFont = new Font("Courier New", Font.BOLD, 26);
	public static final Font defaultLargeFont = new Font("Courier New", Font.BOLD, 36);
	public static final Font defaultXLFont = new Font("Courier New", Font.BOLD, 48);
	public static final Color defaultTextColor = warmWhite; 
	
	// Button
	public static final Color defaultButtonColor = warmWhite;
	public static final Color defaultButtonTextColor = warmBlack;
	public static final Color defaultButtonOutlineColor = gray;
	
	public static final Color defaultButtonHoverColor = warmBlack;
	public static final Color defaultButtonHoverTextColor = warmWhite;
	public static final Color defaultButtonHoverOutlineColor = gray;
	
	// Input box
	public static final Color defaultInputBoxColor = defaultButtonColor;
	public static final Color defaultInputBoxTextColor = defaultButtonTextColor;
	public static final Color defaultInputBoxOutlineColor = defaultButtonOutlineColor;
}
