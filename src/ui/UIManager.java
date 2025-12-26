package ui;

import java.util.ArrayList;

public class UIManager {
	
	private static ArrayList<Button> buttons = new ArrayList<Button>();
	private static ArrayList<InputBox> inputBoxes = new ArrayList<InputBox>();
	
	public static void addButton(Button button) {
		buttons.add(button);
	}
	public static void addInputBox(InputBox inputBox) {
		inputBoxes.add(inputBox);
	}
	
	public static void update() {
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).update();
		}
		for(int i = 0; i < inputBoxes.size(); i++) {
			inputBoxes.get(i).update();
		}
	}
	
	public static void clearAll() {
		buttons.clear();
		inputBoxes.clear();
	}
}
