package codes;

import javafx.scene.control.Button;

public class Firefox extends HUDobjects {
	
	private Button close;
	private int x;
	private int y;
	
	public Firefox(int x, int y) {
		super(x, y, 1000, 600, "ffoxWindow.png");
		
		this.x = x;
		this.y = y;
		
		close = new Button("");
		close.setTranslateX(this.x + 910);
		close.setTranslateY(this.y+10);
		close.setPrefSize(80, 40);
		close.setStyle("-fx-background-color: transparent;");
	}
	
	public Button getClose() {
		return close;
	}
	
	public void moveWindow(int x, int y) {
		setTranslateX(x);
		setTranslateY(y);
	}

}
