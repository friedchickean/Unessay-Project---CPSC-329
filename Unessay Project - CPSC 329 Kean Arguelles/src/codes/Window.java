package codes;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * 
 * @author Kean Arguelles
 * Unessay Project for CPSC 329
 *
 */
public class Window extends HUDobjects{
	private Button close;
	private int x;
	private int y;
	private boolean open = false;
	private boolean showContents;
	private ArrayList<Node> contents = new ArrayList<Node>(); // list of all the contents in the window
	
	/**
	 * Creates a window with a default close button position
	 * @param x
	 * @param y
	 * @param image
	 */
	public Window(int x, int y, int w, int h, int xb, int yb, int bsx, int bsy, String image, boolean show) {
		super(x, y, w, h, image);
		
		this.x = x;
		this.y = y;
		this.setShowContents(show);
		
		close = new Button("");
		close.setTranslateX(this.x + xb); 
		close.setTranslateY(this.y + yb); 
		close.setPrefSize(bsx, bsy);
		close.setStyle("-fx-background-color: transparent;");
	}
	

	public Button getClose() {
		return close;
	}

	public void moveWindow(int x, int y) {
		setTranslateX(x);
		setTranslateY(y);
	}

	public void addContent(Node n) {
		contents.add(n);
	}
	
	public ArrayList<Node> getContents() {
		return contents;
	}
	public void resetContents() {
		ArrayList<Node> temp = new ArrayList<>(contents);
		System.out.println(temp.size());
		contents.clear();
		System.out.println(contents.size());
		
		for(int i = 0; i < 2; i++) {
			contents.add(temp.get(i));
		}
	}


	public boolean isOpen() {
		return open;
	}


	public void setOpen(boolean open) {
		this.open = open;
	}


	public boolean isShowContents() {
		return showContents;
	}


	public void setShowContents(boolean showContents) {
		this.showContents = showContents;
	}
	
	
	
}
