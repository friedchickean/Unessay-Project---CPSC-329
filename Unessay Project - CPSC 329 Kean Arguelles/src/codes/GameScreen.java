package codes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 
 * @author Kean Arguelles
 * Unessay Project for CPSC 329
 *
 */
public class GameScreen extends Scene {

	private AnimationTimer timer;
	private Icon firefox = new Icon(20, 20, 80, 80, "ffox.png");
	private Icon fileExpl = new Icon(20, 130, 80, 80, "fe.png");
	private Window ffoxWindow = new Window(150, 50, 1000, 600, 910, 10, 80, 40, "ffoxWindow.png", true);
	private Window fileWindow = new Window(150, 50, 1000, 600, 910, 10, 80, 40, "fileWindow.png", false);
	private Window vWindow = new Window(250, 50, 700, 600, 281, 522, 155, 30, "virusPopUp.png", true);
	// tabs in firefox
	private ArrayList<BrowserTab> tabsOpened = new ArrayList<>();
	private BrowserTab emails = new BrowserTab(165, 105, 970, 530, "email.png", ffoxWindow);
	private BrowserTab searchAv = new BrowserTab(165, 105, 970, 530, "searchPage.png", ffoxWindow);
	private BrowserTab avPage = new BrowserTab(165, 105, 970, 530, "avPage.png", ffoxWindow);
	private BrowserTab signUpPopUp = new BrowserTab(437, 155, 490, 430, "avPagePopUp.png", ffoxWindow);
	private Button searchAvButton = new Button();
	private Button signUp = new Button();
	private Button download = new Button();
	private Button[] phish = new Button[3];
	private BrowserTab[] phishOpen = new BrowserTab[3];
	private BrowserTab dlSuccess = new BrowserTab(625, 370, 350, 100, "download.png", ffoxWindow);
	private Icon vdinstaller = new Icon(185, 120, 150, 130, "VDicon.png");
	private int emailOpened = 0;
	private boolean rightFile = false;
	private Window results;
	
	private static Pane root;
	
	// icons in ffox
	private Icon mail = new Icon((int) (firefox.getTranslateX() + 340), (int) (firefox.getTranslateX() + 260), 200, 200, "mail.png");
	private Icon search = new Icon((int) (firefox.getTranslateX() + 750), (int) (firefox.getTranslateX() + 260), 200, 200, "search.png");
	
	// create a time in the taskbar
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private Label timeLabel = new Label("");

	/**
	 * Creates a game screen with a specified width and heighT
	 * 
	 * @param width
	 * @param height
	 */
	public GameScreen(int width, int height) {
		super(root = new Pane());
		root.setPrefSize(width, height);
		// changing the background to the main menu
		root.setStyle("-fx-background-image: url(/codes/sprites/gameScr.png); " + "-fx-background-size: cover;");

		// create icons and windows of the computer
		
		// add icons
		root.getChildren().add(firefox);
		root.getChildren().add(fileExpl);
		root.getChildren().add(vWindow);
		root.getChildren().add(vWindow.getClose());
		
		vWindow.setOpen(true);
		vWindow.getClose().setOnMouseClicked (e -> {
			openOrClose(vWindow);
		});
		firefox.setOnMouseClicked(e -> {
			openOrClose(ffoxWindow);
		});
		ffoxWindow.getClose().setOnMouseClicked (e -> {
			openOrClose(ffoxWindow);
		});
		
		fileExpl.setOnMouseClicked(e -> {
			openOrClose(fileWindow);
		});
		fileWindow.getClose().setOnMouseClicked (e -> {
			openOrClose(fileWindow);
		});
		
		ffoxWindow.addContent(mail);
		ffoxWindow.addContent(search);
		fileWindow.addContent(vdinstaller);
		
		vdinstaller.setOnMouseClicked(e -> {
			showResult();
		});
		
		// HOMEPAGE
		emails.setShowContents(false);
		mail.setOnMouseClicked(e -> {
			openTab(emails);
		});
		search.setOnMouseClicked(e -> {
			openTab(searchAv);
		});
		// TABS
		searchAvButton.setOnMouseClicked(e -> {
			openTab(avPage);
		});
		searchAv.addInteraction(searchAvButton, 340, 425, 645, 60, true);
		
		signUp.setOnMouseClicked(e -> {
			openTab(signUpPopUp);
			emails.setShowContents(true);
		});
		avPage.addInteraction(signUp, 645, 495, 240, 50, true);
		// create the emails
		for(int i = 0; i < 3; i++) {
			phish[i] = new Button();
			phish[i].setBackground(new Background(new BackgroundImage(new Image("/codes/sprites/phish" + i + ".png"),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					new BackgroundSize(getWidth(), getHeight(), true, true, true, false))));
			phishOpen[i] = new BrowserTab(473, 188, 632, 427, "phishOpen"+i+".png", ffoxWindow);
			phishOpen[i].addInteraction(download, 680, 490, 230, 50, true);
			// add the phishing button to email page
			emails.addInteraction(phish[i], 190, 160 + (i*120), 270, 170, false);
		}
		
		download.setOnMouseClicked(e -> {
			rightFile = isRightFile();
			openTab(dlSuccess);
			fileWindow.setShowContents(true);
			
		});
		// wish i could put this in the for loop :(
		phish[0].setOnMouseClicked(e -> {
			closeTabs();
			openTab(emails);
			openTab(phishOpen[0]);
			emailOpened = 0;
		});
		phish[1].setOnMouseClicked(e -> {
			closeTabs();
			openTab(emails);
			openTab(phishOpen[1]);
			emailOpened = 1;
		});
		phish[2].setOnMouseClicked(e -> {
			closeTabs();
			openTab(emails);
			openTab(phishOpen[2]);
			emailOpened = 2;
		});
		// End of icons and windows
		
		
		// make a clock! cause why not!
		timeLabel.setTranslateX(1100);
		timeLabel.setTranslateY(675);
		timeLabel.setFont(new Font("Times", 30));
		timeLabel.setTextFill(Color.WHITE);

		// An animation timer is used for smoother animations
		timer = new AnimationTimer() {
			long lastUpdate = 0;

			@Override
			public void handle(long now) {
				
				//update time
				if(now - lastUpdate >= 100_000_000) {
					lastUpdate = now;
					root.getChildren().remove(timeLabel);
					cal = Calendar.getInstance();
					timeLabel.setText(sdf.format(cal.getTime()));
					root.getChildren().add(timeLabel);
				}
				
			}
		};

		timer.start();
	}

	/**
	 * Adds a node to the root
	 * 
	 * @param n node to be added-
	 */
	public void addNode(Node n) {
		root.getChildren().add(n);
	}
	
	/**
	 * Opens or closes a window passed as a parameter
	 * @param winOpen determines if the window is open or not
	 * @param w window to be opened
	 * @return negation of winOpen
	 */
	private void openOrClose(Window w) {
		boolean winOpen = w.isOpen();
		if (!winOpen) {
			root.getChildren().add(w);
			root.getChildren().add(w.getClose());
			
			if (w.isShowContents()) {
				for(int i = 0; i < w.getContents().size(); i++) {
					root.getChildren().add(w.getContents().get(i));
				}				
			}
		} else {
			root.getChildren().remove(w);
			root.getChildren().remove(w.getClose());
			if (w.isShowContents()) {
				for(int i = 0; i < w.getContents().size(); i++) {
					root.getChildren().remove(w.getContents().get(i));
				}
			}
			closeTabs();
//			emails.setOpen(false); // this too
//			root.getChildren().remove(emails.getClose()); // fix this later
			if (w.getContents().size() >= 2) {
				w.resetContents();				
			}
		}
		w.setOpen(!winOpen);
	}
	
	private void openTab(BrowserTab bt) {
		boolean btOpen = bt.isOpen();
		if (!btOpen) {
			root.getChildren().add(bt);
			root.getChildren().add(bt.getHome());
			tabsOpened.add(bt);
			if(bt.isShowContents()) {
				for (int i = 0; i < bt.getInteractions().size(); i++) {
					root.getChildren().add(bt.getInteractions().get(i));
				}				
			}
			
			bt.getHome().setOnMouseClicked(e -> {
				closeTabs();
				openOrClose(ffoxWindow);	// basically just restart ffox
				openOrClose(ffoxWindow);
			});
			
			for(int i = 0; i < ffoxWindow.getContents().size(); i++) {
				root.getChildren().remove(ffoxWindow.getContents().get(i));
			}
		}
		bt.setOpen(!btOpen);
	}
	
	private void closeTabs() {
		for(int i = 0; i < tabsOpened.size(); i++) {
			tabsOpened.get(i).setOpen(false);
			root.getChildren().remove(tabsOpened.get(i));
			root.getChildren().remove(tabsOpened.get(i).getHome());
			for(int j = 0; j < tabsOpened.get(i).getInteractions().size(); j++) {
				root.getChildren().remove(tabsOpened.get(i).getInteractions().get(j));
			}
		}
		tabsOpened.clear();
	}
	
	private boolean isRightFile() {
		if(emailOpened == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	private void showResult() {
		if(rightFile) {
			results = new Window(255, 10, 690, 700, 281, 522, 155, 30, "ch1pass.png", false);
		} else {
			results = new Window(255, 10, 690, 700, 281, 522, 155, 30, "ch1fail.png", false);
		}
		
		openOrClose(results);
	}
	


}
