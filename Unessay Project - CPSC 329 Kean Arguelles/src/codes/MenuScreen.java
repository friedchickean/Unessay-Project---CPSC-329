package codes;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MenuScreen extends Scene {
	// Create a pane
	static Pane root = new Pane();
	
	
	/**
	 * Create a menu screen object that will show until the player decides to start a new game/ (load game?)/ or exit
	 * TODO: Make a quit game button, and a load screen?
	 */
	public MenuScreen(int width, int height) {
		super(root);
		root.setPrefSize(width, height);
		
		// changing the background to the main menu
		root.setStyle("-fx-background-image: url(/codes/sprites/mainMenu.png); " 
				+ "-fx-background-size: cover;");
		
	}
	
	/**
	 * 
	 * @param n
	 */
	public void addNode(Node n) {
		root.getChildren().add(n);
	}

}
