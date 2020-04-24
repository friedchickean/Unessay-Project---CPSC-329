package codes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

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
public class GameScreen2 extends Scene {

	private AnimationTimer timer;

	private static Pane root;
	private Icon chatIcon = new Icon(20, 20, 80, 80, "chatIcon.png");
	private Icon lockFile = new Icon(20, 130, 80, 80, "lock.png");
	private Window chatWindow = new Window(10, 10, 750, 500, 680, 10, 60, 20, "chat.png", true);
	private Window lockFileWindow = new Window(90, 50, 400, 300, 330, 10, 60, 20, "confirmKey.png", true);
	private Window calcWindow = new Window(750, 10, 500, 600, 730, 40, 80, 40, "moduloCalc.png", false);
	private ArrayList<Label> chatLogs = new ArrayList<>();
	private TextField gVal = new TextField();
	private TextField xVal = new TextField();
	private TextField pVal = new TextField();
	private TextField chatbox = new TextField();
	private TextField secretbox = new TextField();
	private Label calcResult = new Label();
	private Label secretResult = new Label();
	private Button calcButton = new Button();
	private Button sendChatButton = new Button();
	private Button confirmButton = new Button();

	private int bobSecretNum;
	private int g;
	private int p;
	private int bobResult;
	private int bobSecret;
	private boolean showChat = true;
	private Window results = new Window(255, 10, 690, 700, 281, 522, 155, 30, "ch3done.png", false);

	// create a time in the taskbar
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private Label timeLabel = new Label("");

	public GameScreen2(int width, int height) {
		super(root = new Pane());
		Random rand = new Random();
		this.bobSecretNum = rand.nextInt(15) + 1;
		this.g = rand.nextInt(9) + 1;
		while (true) {
			this.p = rand.nextInt(100) + 16;
			if (isPrime(p)) {
				break;
			}
		}
		this.bobResult = (int) (Math.pow(g, bobSecretNum) % p);

		addChat("From Bob: our key is " + Integer.toString(g) + " mod " + Integer.toString(p));
		addChat("From Bob: Pick a secret number but don't send it here.");
		addChat("              Use the calculator and enter your key in the x value");

		root.setPrefSize(width, height);
		// changing the background to the main menu
		root.setStyle("-fx-background-image: url(/codes/sprites/gameScr.png); " + "-fx-background-size: cover;");

		root.getChildren().add(chatIcon);
		root.getChildren().add(lockFile);
		root.getChildren().add(calcWindow);

		chatIcon.setOnMouseClicked(e -> {
			openOrClose(chatWindow);
			openOrCloseChatLogs();
		});
		chatWindow.getClose().setOnMouseClicked(e -> {
			openOrClose(chatWindow);
			openOrCloseChatLogs();
		});

		lockFile.setOnMouseClicked(e -> {
			openOrClose(lockFileWindow);
		});
		lockFileWindow.getClose().setOnMouseClicked(e -> {
			openOrClose(lockFileWindow);
			openOrCloseResult();
		});

		confirmButton.setTranslateX(200);
		confirmButton.setTranslateY(180);
		confirmButton.setPrefSize(180, 40);
		confirmButton.setStyle("-fx-background-color: transparent;");
		secretbox.setTranslateX(267);
		secretbox.setTranslateY(120);
		secretbox.setPrefSize(160, 40);
		confirmButton.setOnMouseClicked(e -> {
			checkIfSecretIsRight(secretbox.getText());
		});
		lockFileWindow.addContent(confirmButton);
		lockFileWindow.addContent(secretbox);

		secretResult.setTranslateX(190);
		secretResult.setTranslateY(230);
		secretResult.setFont(new Font("Times", 15));
		secretResult.setTextFill(Color.RED);

		calcResult.setTranslateX(940);
		calcResult.setTranslateY(440);
		calcResult.setFont(new Font("Times", 18));
		gVal.setTranslateX(1000);
		gVal.setTranslateY(240);
		gVal.setPrefSize(100, 30);
		root.getChildren().add(gVal);

		xVal.setTranslateX(1000);
		xVal.setTranslateY(285);
		xVal.setPrefSize(100, 30);
		root.getChildren().add(xVal);

		pVal.setTranslateX(1000);
		pVal.setTranslateY(330);
		pVal.setPrefSize(100, 30);
		root.getChildren().add(pVal);

		calcButton.setTranslateX(880);
		calcButton.setTranslateY(383);
		calcButton.setPrefSize(242, 35);
		calcButton.setStyle("-fx-background-color: transparent;");
		calcButton.setOnMouseClicked(e -> {
			calcMod();
		});
		root.getChildren().add(calcButton);

		chatbox.setTranslateX(45);
		chatbox.setTranslateY(417);
		chatbox.setPrefSize(549, 74);

		sendChatButton.setTranslateX(610);
		sendChatButton.setTranslateY(417);
		sendChatButton.setPrefSize(124, 74);
		sendChatButton.setStyle("-fx-background-color: transparent;");
		sendChatButton.setOnMouseClicked(e -> {
			if (chatLogs.size() >= 11) {
				for (int i = 0; i < chatLogs.size(); i++) {
					root.getChildren().remove(chatLogs.get(i));
				}
				chatLogs.clear();
			}
			addChat("You: " + chatbox.getText());
			checkNumberGiven(chatbox.getText());
			openOrCloseChatLogs();
			openOrCloseChatLogs();
		});

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
			if (w.getContents().size() >= 2) {
				w.resetContents();
			}
		}
		w.setOpen(!winOpen);
	}

	/**
	 * Adds a node to the root
	 * 
	 * @param n node to be added-
	 */
	public void addNode(Node n) {
		root.getChildren().add(n);
	}

	public void addChat(String s) {
		Label temp = new Label();
		temp.setText(s);
		temp.setFont(new Font("Times", 22));
		temp.setTranslateX(30);
		temp.setTranslateY(20 + (chatLogs.size() * 30));
		chatLogs.add(temp);
	}

	public void openOrCloseChatLogs() {
		if (showChat) {
			for (int i = 0; i < chatLogs.size(); i++) {
				root.getChildren().add(chatLogs.get(i));
			}
			root.getChildren().add(chatbox);
			root.getChildren().add(sendChatButton);
			showChat = false;
		} else {
			for (int i = 0; i < chatLogs.size(); i++) {
				root.getChildren().remove(chatLogs.get(i));
			}
			root.getChildren().remove(chatbox);
			root.getChildren().remove(sendChatButton);
			showChat = true;
		}
	}

	public void calcMod() {
		try {
			int gT = Integer.parseInt(gVal.getText());
			int xT = Integer.parseInt(xVal.getText());
			int pT = Integer.parseInt(pVal.getText());

			gT = (int) Math.pow(gT, xT);
			gT = gT % pT;

			calcResult.setTextFill(Color.BLACK);
			calcResult.setText(Integer.toString(gT));

		} catch (Exception e) {
			calcResult.setTextFill(Color.RED);
			calcResult.setText("1 or more values are invalid");
		}

		root.getChildren().remove(calcResult);
		root.getChildren().add(calcResult);
	}

	public void checkNumberGiven(String s) {
		int n;
		try {
			n = Integer.parseInt(s);
			addChat("From Bob: Great! here's my result: " + bobResult + ". Raise this to the power of your ");
			addChat("private number then mod it with p. And that is our secret!");
			addChat("From Bob: Try unlocking the file in your desktop with the secret number.");
			bobSecret = (int) (Math.pow(n, bobSecretNum));
			bobSecret = bobSecret % p;
			System.out.println("n: " + n + " bobN: " + bobSecretNum + " p: " + p);
		} catch (Exception e) {
			addChat("From Bob: Umm.. please enter only your calculated result");
		}
	}

	/**
	 * Need a prime number for modulo
	 */
	public boolean isPrime(int n) {
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public void checkIfSecretIsRight(String s) {
		System.out.println("bob secret:" + bobSecret);
		System.out.println("bob n: " + bobSecretNum + "  g: " + g + "  p:  " + p + "    s: " + s);
		int n;
		try {
			n = Integer.parseInt(s);
			if (n == bobSecret) {
				root.getChildren().add(results);
			} else {
				secretResult.setText("That's not correct, check your calculations");
				root.getChildren().remove(secretResult);
				root.getChildren().add(secretResult);
			}
		} catch (Exception e) {
			secretResult.setText("Input is invalid");
			root.getChildren().remove(secretResult);
			root.getChildren().add(secretResult);
		}
	}

	public void openOrCloseResult() {
		if (!lockFileWindow.isOpen()) {
			root.getChildren().remove(secretResult);
		} else {
			root.getChildren().add(secretResult);
		}
	}

}
