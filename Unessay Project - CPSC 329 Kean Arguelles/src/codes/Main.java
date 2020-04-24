package codes;


import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * 
 * @author Kean Arguelles
 * Unessay Project for CPSC 329
 *
 */
public class Main extends Application {
	
	private final int WIDTH = 1280;
	private final int HEIGHT = 720;
	
	// Create menu buttons
	private ArrayList<Scene> scenes = new ArrayList<>(); // 0 = menu screen, 1 = game screen.
	private Button newGameButton = new Button("Challenge #1");
	private Button newGameButton1 = new Button("Challenge #2");
	private Button newGameButton2 = new Button("Diffie-Hellman Simulation");
	private Button backToMenuButton = new Button();
	
	// Set Background
	private BackgroundImage bImage = new BackgroundImage(new Image("/codes/sprites/button.png"),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
			new BackgroundSize(newGameButton.getWidth(), newGameButton.getHeight(), true, true, true, false));
	private Background buttonBg = new Background(bImage);
	
	@Override
	public void start(Stage stage) throws Exception {
		createScreens();
		stage.setScene(scenes.get(0));
		
		((MenuScreen) scenes.get(0)).addNode(newGameButton);
		newGameButton.setOnMouseClicked(e -> {
			newGame();
			stage.setScene(scenes.get(1));
		});
		newGameButton.setTranslateX(900);
		newGameButton.setTranslateY(200);
		newGameButton.setPrefSize(250, 100);
		newGameButton.setBackground(buttonBg);
		newGameButton.setFont(new Font("Times", 30));
		
		((MenuScreen) scenes.get(0)).addNode(newGameButton1);
		newGameButton1.setOnMouseClicked(e -> {
			newGame1();
			stage.setScene(scenes.get(2));
		});
		newGameButton1.setTranslateX(900);
		newGameButton1.setTranslateY(300);
		newGameButton1.setPrefSize(250, 100);
		newGameButton1.setBackground(buttonBg);
		newGameButton1.setFont(new Font("Times", 30));
		
		((MenuScreen) scenes.get(0)).addNode(newGameButton2);
		newGameButton2.setOnMouseClicked(e -> {
			newGame2();
			stage.setScene(scenes.get(3));
		});
		newGameButton2.setTranslateX(900);
		newGameButton2.setTranslateY(400);
		newGameButton2.setPrefSize(250, 100);
		newGameButton2.setBackground(buttonBg);
		newGameButton2.setFont(new Font("Times", 18));
		
		// button to return to main menu
		backToMenuButton.setOnMouseClicked(e -> {
			stage.setScene(scenes.get(0));
		});
		backToMenuButton.setTranslateX(4); 
		backToMenuButton.setTranslateY(HEIGHT - 74); 
		backToMenuButton.setPrefSize(160, 110);
		backToMenuButton.setBackground(new Background(new BackgroundImage(new Image("/codes/sprites/backtomain.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(backToMenuButton.getWidth(), backToMenuButton.getHeight(), true, true, true, false))));
		
		stage.setResizable(false);
		stage.setTitle("Unessay Project");
		stage.show();
	}
	
	/**
	 * Creates the main menu and the game screen
	 */
	private void createScreens() {
		scenes.add(new MenuScreen(WIDTH, HEIGHT));
		scenes.add(new GameScreen(WIDTH, HEIGHT));
		scenes.add(new GameScreen1(WIDTH, HEIGHT));
		scenes.add(new GameScreen2(WIDTH, HEIGHT));
		
	}
	
	private void newGame() {
		scenes.set(1, new GameScreen(WIDTH, HEIGHT));
		((GameScreen) scenes.get(1)).addNode(backToMenuButton);
	}
	private void newGame1() {
		scenes.set(2, new GameScreen1(WIDTH, HEIGHT));
		((GameScreen1) scenes.get(2)).addNode(backToMenuButton);
	}
	private void newGame2() {
		scenes.set(3, new GameScreen2(WIDTH, HEIGHT));
		((GameScreen2) scenes.get(3)).addNode(backToMenuButton);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
