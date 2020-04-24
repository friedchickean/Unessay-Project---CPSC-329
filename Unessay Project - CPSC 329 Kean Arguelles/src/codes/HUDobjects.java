package codes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Kean Arguelles
 * Unessay Project for CPSC 329
 *
 */
public class HUDobjects extends Rectangle {
	private Image objectImage;
	
	public HUDobjects(int x, int y, int w, int h, String image) {
		// creating a new rectangle
		super(w, h);
		changeSprite(image);
		setPosition(x, y);
	}
	
	public void changeSprite(String imageName) {
		imageName = "/codes/sprites/" + imageName;
		if (imageName != null) {
			this.objectImage = new Image(imageName);
			ImageView iv = new ImageView(objectImage);
			this.setFill(new ImagePattern(this.objectImage));
		}
	}
	
	public void setPosition(int x, int y) {
		setTranslateX(x);
		setTranslateY(y);
	}
}
