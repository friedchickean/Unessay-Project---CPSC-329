package codes;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Icon extends Button {
	

	/**
	 * Creates an "Icon" with specified size and position
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param imgName
	 */
	public Icon(int x, int y, int width, int height, String imgName) {
		setTranslateX(x);
		setTranslateY(y);
		setPrefSize(width, height);
		setBackground(new Background(new BackgroundImage(new Image("/codes/sprites/" + imgName),
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
		new BackgroundSize(getWidth(), getHeight(), true, true, true, false))));
		
	}

}
