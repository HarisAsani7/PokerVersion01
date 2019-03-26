package poker.version_graphics.view;


import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Preloader.ProgressNotification;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.Card;
import poker.version_graphics.model.Player;
import poker.version_graphics.model.PokerGameModel;
import poker.version_graphics.view.*;

public class PokerGameView {
	private HBox players;
	private ControlArea controls;
	public Label lblWinner = new Label("");
	ProgressBar bar;
	Stage myStage;
	private PokerGameModel HandType;
	
	private Scene createLoadingScreen(){
		bar = new ProgressBar();
		BorderPane p = new BorderPane();
		p.setCenter(bar);
		return new Scene(p, 300, 150);
		
	}
	
	public void start (Stage myStage) throws Exception{
		this.myStage = myStage;
		myStage.setScene(createLoadingScreen());
		myStage.show();
	}

	private PokerGameModel model;
	
	public PokerGameView(Stage stage, PokerGameModel model) {
		this.model = model;
		// Create all of the player panes we need, and put them into an HBox
		players = new HBox();
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			PlayerPane pp = new PlayerPane();
			pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
			players.getChildren().add(pp);
			
		}
		// Create the control area
		controls = new ControlArea();
		controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in the logic
		
		MenuBar menuBar = new MenuBar();
		Menu setPlayer = new Menu("Player");
		final int maxPlayer = 10;
		final int maxCards = 4;
		for (int i = 1; i <=maxPlayer; i++) {
		MenuItem chosePlayer = new MenuItem("Player " + i);
		setPlayer.getItems().add(chosePlayer);
		}
		Menu cardDeck = new Menu("Cardstyle");
		for (int i = 1; i <= maxCards; i++) {
			MenuItem choseCards = new MenuItem("Cardstyle " + i);
			cardDeck.getItems().add(choseCards);
		}
		Menu info = new Menu ("Options");
		MenuItem exit = new MenuItem("Exit Game");
		MenuItem infoCards = new MenuItem("Card explained");
		MenuItem statistics = new MenuItem("Statistics");
		info.getItems().addAll(exit, infoCards, statistics);
		menuBar.getMenus().addAll(setPlayer, cardDeck, info);
		
		exit.setOnAction(e ->{
			System.exit(0);
		});
		statistics.setOnAction(e -> {
			
			Label HandtypeStats = new Label("Handtype");
			HandtypeStats.setId("HandtypeStats");
			Label HighCardStats = new Label("High-card: ");
			Label OnePairStats = new Label("One Pair: ");
			Label TwoPairStats = new Label("Two Pair: ");
			Label ThreeOfAKindStats = new Label("Three of a kind: ");
			Label FourOfAKindStats = new Label("Four of a kind: ");
			Label FullhouseStats = new Label("Full house: ");
			Label StraightStats = new Label("Straight: ");
			Label FlushStats = new Label("Flush: ");
			Label StraightFlushStats = new Label("Straight flush: ");
			
//			for(int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
//				if(HandType != null) 
//					if(HandType = model.getPlayer(i).evaluateHand().isOnePair()) {
//						
//					}
//				Label counterLabel = new Label(counter.toString())
//			}
			GridPane stats = new GridPane();
			stats.setId("Gridpane1");
			stats.add(HandtypeStats, 0, 0);
			stats.add(HighCardStats, 0, 1);
			stats.add(OnePairStats, 0, 2);
			stats.add(TwoPairStats, 0, 3);
			stats.add(ThreeOfAKindStats, 0, 4);
			stats.add(FourOfAKindStats, 0, 5);
			stats.add(FullhouseStats, 0, 6);
			stats.add(StraightStats, 0, 7);
			stats.add(FlushStats, 0, 8);
			stats.add(StraightFlushStats, 0, 9);
			
			Button back = new Button ("back");
			back.setOnAction(f ->{
				BorderPane root1 = new BorderPane();
				root1.setCenter(players);
				root1.setBottom(controls);
				root1.setTop(menuBar);
				
				Scene sceneBack = new Scene(root1);
				sceneBack.getStylesheets().add(
			                getClass().getResource("poker.css").toExternalForm());
				stage.setScene(sceneBack);
				stage.setTitle("Poker Miniproject");
				stage.show();
			});
			GridPane bottom = new GridPane();
			bottom.add(back, 2, 0);
			bottom.setId("back");
			
			BorderPane root1 = new BorderPane();
			root1.setCenter(stats);
			root1.setBottom(bottom);
			root1.setTop(menuBar);
			
			Scene sceneStats = new Scene(root1);
			sceneStats.getStylesheets().add(
		                getClass().getResource("poker.css").toExternalForm());
			stage.setScene(sceneStats);
			stage.setTitle("Statistics");
			stage.show();
			
		});
//		
		// Put players and controls into a BorderPane
		BorderPane root = new BorderPane();
		root.setCenter(players);
		root.setBottom(controls);
		root.setTop(menuBar);
		
		// Disallow resizing - which is difficult to get right with images
		stage.setResizable(false);

        // Create the scene using our layout; then display it
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("poker.css").toExternalForm());
        stage.setTitle("Poker Miniproject");
        stage.setScene(scene);
        stage.show();
        
        
	}

	public PlayerPane getPlayerPane(int i) {	
		return (PlayerPane) players.getChildren().get(i);
	}
	
	public Button getShuffleButton() {
		return controls.btnShuffle;
	}
	
	public Button getDealButton() {
		return controls.btnDeal; 
 
	}
	
	public ProgressBar getProgressBar() {
		return bar;
	}
	
	public Stage getMyStage () {
		return myStage;
	}
	public Label getWinnerLabel() {
		return controls.winner;
	}
}
