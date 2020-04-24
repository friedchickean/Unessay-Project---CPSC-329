package codes;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * 
 * @author Kean Arguelles
 * Unessay Project for CPSC 329
 *
 */
public class BrowserTab extends HUDobjects{
	
	private Button home = new Button();
	private ArrayList<Button> interactions = new ArrayList<Button>();
	private boolean open = false;
	private boolean showContents = true;
	
	public BrowserTab(int x, int y, int w, int h, String image, Window ffox) {
		super(x, y, w, h, image);
		// TODO Auto-generated constructor stub
		home.setTranslateX(x + 900); 
		home.setTranslateY(y + 20); 
		home.setPrefSize(50, 50);
		home.setBackground(new Background(new BackgroundImage(new Image("/codes/sprites/b2home.png"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(getWidth(), getHeight(), true, true, true, false))));
		
		
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public Button getHome() {
		return home;
	}
	
	public void addInteraction(Button b, int x, int y, int width, int height, boolean hide) {
		b.setTranslateX(x); 
		b.setTranslateY(y); 
		b.setPrefSize(width, height);
		if(hide) {
			b.setStyle("-fx-background-color: transparent;");			
		}
		this.interactions.add(b);
	}
	
	public ArrayList<Button> getInteractions() {
		return this.interactions;
	}

	public boolean isShowContents() {
		return showContents;
	}

	public void setShowContents(boolean showContents) {
		this.showContents = showContents;
	}

}
