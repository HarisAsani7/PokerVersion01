package poker.version_graphics.view;


import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;

import java.awt.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
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
	
	private PokerGameModel model;
//	Who wins? (not finished)
//	public void evaluateWinner() {
//		Player winner = model.getPlayer(0);
//		Player currentWinner = model.getPlayer(0);
//		for(int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
//			if (currentWinner.compareTo(model.getPlayer(i)) < 0) {
//				currentWinner = model.getPlayer(0);
//				lblWinner.setText(model.getPlayer(i) + " lost");
//				
//			} else {
//				winner = currentWinner;
//				lblWinner.setText(model.getPlayer(i) + " wins");
//			}
//		}    	
//	}
	public PokerGameView(Stage stage, PokerGameModel model) {
		this.model = model;
		this.lblWinner = lblWinner;
		// Create all of the player panes we need, and put them into an HBox
		players = new HBox();
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			PlayerPane pp = new PlayerPane();
			pp.setPlayer(model.getPlayer(i)); // link to player object in the logic
			players.getChildren().add(pp);
			
		}
		
		
		MenuBar menuBar = new MenuBar();
		Menu setPlayer = new Menu("Player");
		MenuItem chosePlayer = new MenuItem("Chose Player");
		setPlayer.getItems().add(chosePlayer);
		Menu cardDeck = new Menu("Cards");
		menuBar.getMenus().addAll(setPlayer, cardDeck);
		
		// Create the control area
		controls = new ControlArea();
		controls.linkDeck(model.getDeck()); // link DeckLabel to DeckOfCards in the logic
		
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
	
}
