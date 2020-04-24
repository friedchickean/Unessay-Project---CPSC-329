package codes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 
 * @author Kean Arguelles
 * Unessay Project for CPSC 329
 *
 */
public class GameScreen1 extends Scene {

	private AnimationTimer timer;
	private static Pane root;
	private Icon firefox = new Icon(20, 20, 80, 80, "ffox.png");
	private Icon fileExpl = new Icon(20, 130, 80, 80, "fe.png");
	private Window ffoxWindow = new Window(150, 50, 1000, 600, 910, 10, 80, 40, "ffoxWindow.png", true);
	private Window fileWindow = new Window(150, 50, 1000, 600, 910, 10, 80, 40, "fileWindow.png", false);
	private Window vWindow = new Window(250, 50, 700, 600, 281, 522, 155, 30, "ch2Window.png", true);
	// tabs in firefox
	private ArrayList<BrowserTab> tabsOpened = new ArrayList<>();
	private BrowserTab emails = new BrowserTab(165, 105, 970, 530, "ch2email.png", ffoxWindow);
	private BrowserTab searchAv = new BrowserTab(165, 105, 970, 530, "searchPwChange.png", ffoxWindow);
	private BrowserTab avPage = new BrowserTab(165, 105, 970, 530, "avPageChangePw.png", ffoxWindow);
	private Button searchAvButton = new Button();

	private Window results = new Window(255, 10, 690, 700, 281, 522, 155, 30, "ch2pass.png", false);

	private TextField emailPw = new TextField();
	private TextField avPw = new TextField();
	private String newEmailPw;
	private String newavPw;
	private Label pwResult = new Label();
	private boolean[] emailAndAv = { false, false };

	private Button enter = new Button();
	private Button enter1 = new Button();

	// icons in ffox
	private Icon mail = new Icon((int) (firefox.getTranslateX() + 340), (int) (firefox.getTranslateX() + 260), 200, 200,
			"mail.png");
	private Icon search = new Icon((int) (firefox.getTranslateX() + 750), (int) (firefox.getTranslateX() + 260), 200,
			200, "search.png");

	// create a time in the taskbar
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private Label timeLabel = new Label("");

	public GameScreen1(int width, int height) {
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
		vWindow.getClose().setOnMouseClicked(e -> {
			openOrClose(vWindow);
		});
		firefox.setOnMouseClicked(e -> {
			openOrClose(ffoxWindow);
		});
		ffoxWindow.getClose().setOnMouseClicked(e -> {
			openOrClose(ffoxWindow);
			checkCompletion();
		});

		fileExpl.setOnMouseClicked(e -> {
			openOrClose(fileWindow);
		});
		fileWindow.getClose().setOnMouseClicked(e -> {
			openOrClose(fileWindow);
			checkCompletion();
		});

		ffoxWindow.addContent(mail);
		ffoxWindow.addContent(search);

		mail.setOnMouseClicked(e -> {
			openTab(emails);
			root.getChildren().add(emailPw);
		});
		search.setOnMouseClicked(e -> {
			openTab(searchAv);
		});

		// TABS
		searchAvButton.setOnMouseClicked(e -> {
			openTab(avPage);
			root.getChildren().add(avPw);
		});
		searchAv.addInteraction(searchAvButton, 340, 425, 645, 60, true);

		// fields
		emailPw.setPromptText("New Password");
		avPw.setPromptText("New Passowrd");
		emailPw.setTranslateX(595);
		emailPw.setTranslateY(455);
		emailPw.setPrefSize(190, 40);

		avPw.setTranslateX(650);
		avPw.setTranslateY(435);
		avPw.setPrefSize(190, 40);

		pwResult.setFont(new Font("Times", 24));

		enter.setOnMouseClicked(e -> {
			System.out.println("yes");
			newEmailPw = emailPw.getText();
			emailAndAv[0] = checkPassword(newEmailPw);
			displayResult(enter);

		});
		enter1.setOnMouseClicked(e -> {
			newavPw = avPw.getText();
			emailAndAv[1] = checkPassword(newavPw);
			displayResult(enter1);
		});

		emails.addInteraction(enter, 598, 505, 230, 40, true);
		avPage.addInteraction(enter1, 700, 489, 175, 40, true);

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

				// update time
				if (now - lastUpdate >= 100_000_000) {
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
	 * 
	 * @param winOpen determines if the window is open or not
	 * @param w       window to be opened
	 * @return negation of winOpen
	 */
	private void openOrClose(Window w) {
		boolean winOpen = w.isOpen();
		if (!winOpen) {
			root.getChildren().add(w);
			root.getChildren().add(w.getClose());

			if (w.isShowContents()) {
				for (int i = 0; i < w.getContents().size(); i++) {
					root.getChildren().add(w.getContents().get(i));
				}
			}
		} else {
			root.getChildren().remove(w);
			root.getChildren().remove(w.getClose());
			if (w.isShowContents()) {
				for (int i = 0; i < w.getContents().size(); i++) {
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
			if (bt.isShowContents()) {
				for (int i = 0; i < bt.getInteractions().size(); i++) {
					root.getChildren().add(bt.getInteractions().get(i));
				}
			}

			bt.getHome().setOnMouseClicked(e -> {
				closeTabs();
				openOrClose(ffoxWindow); // basically just restart ffox
				openOrClose(ffoxWindow);
				checkCompletion();
			});

			for (int i = 0; i < ffoxWindow.getContents().size(); i++) {
				root.getChildren().remove(ffoxWindow.getContents().get(i));
			}
		}
		bt.setOpen(!btOpen);
	}

	private void closeTabs() {
		for (int i = 0; i < tabsOpened.size(); i++) {
			tabsOpened.get(i).setOpen(false);
			root.getChildren().remove(tabsOpened.get(i));
			root.getChildren().remove(tabsOpened.get(i).getHome());
			for (int j = 0; j < tabsOpened.get(i).getInteractions().size(); j++) {
				root.getChildren().remove(tabsOpened.get(i).getInteractions().get(j));
			}
		}
		root.getChildren().remove(emailPw);
		root.getChildren().remove(avPw);
		root.getChildren().remove(pwResult);
		tabsOpened.clear();
	}

	private boolean checkPassword(String pw) {
		String temp;
		if (pw.length() == 0) {
			pwResult.setText("Please enter a password");
			pwResult.setTextFill(Color.RED);
			return false;
		} else {
			pwResult.setTextFill(Color.RED);
			temp = pw;
			// check for uppercase
			temp = temp.toLowerCase();
			if (pw.length() <= 4) {
				pwResult.setText("Password must be at least 5 characters");
				return false;
			} else if (temp == pw) {
				pwResult.setText("Password must have at least 1 uppercase letter");
				return false;
			} else {
				pwResult.setText("Password changed successfully!");
				pwResult.setTextFill(Color.GREEN);
				return true;
			}
		}
	}

	private void displayResult(Button b) {
		root.getChildren().remove(pwResult);
		pwResult.setTranslateX(b.getTranslateX() - 70);
		pwResult.setTranslateY(b.getTranslateY() + 40);
		root.getChildren().add(pwResult);
	}

	private void checkCompletion() {
		if (emailAndAv[0] && emailAndAv[1]) {
			if (!root.getChildren().contains(results)) {
				root.getChildren().add(results);
			}
		}
	}

}
