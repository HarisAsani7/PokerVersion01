package poker.version_graphics.controller;

import javafx.animation.RotateTransition;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import poker.version_graphics.PokerGame;
import poker.version_graphics.model.Card;
import poker.version_graphics.model.DeckOfCards;
import poker.version_graphics.model.Player;
import poker.version_graphics.model.PokerGameModel;
import poker.version_graphics.view.PlayerPane;
import poker.version_graphics.view.PokerGameView;

public class PokerGameController {
	private PokerGameModel model;
	private PokerGameView view;
	
	public PokerGameController(PokerGameModel model, PokerGameView view) {
		this.model = model;
		this.view = view;
		
		view.getShuffleButton().setOnAction( e -> shuffle() );
		view.getDealButton().setOnAction( e -> deal() );
	}

    /**
     * Remove all cards from players hands, and shuffle the deck
     */
    private void shuffle() {
    	view.getWinnerLabel().setText("Winner: ");
    	view.getDealButton().setDisable(false);
    	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
    		Player p = model.getPlayer(i);
    		p.discardHand();
    		PlayerPane pp = view.getPlayerPane(i);
    		pp.updatePlayerDisplay();
    	}

    	model.getDeck().shuffle();
    	
    	DropShadow shadow = new DropShadow();
    	view.getShuffleButton().setOnMouseEntered(e ->{
    		view.getShuffleButton().setEffect(shadow);
    	});
    	view.getShuffleButton().setOnMouseExited(e ->{
    		view.getShuffleButton().setEffect(null);
    	});
    }
    
    /**
     * Deal each player five cards, then evaluate the two hands
     */
    private void deal() {
    	int cardsRequired = PokerGame.NUM_PLAYERS * Player.HAND_SIZE;
    	DeckOfCards deck = model.getDeck();
//    	Player winner = model.getPlayer(0);
//    	Player currentWinner = model.getPlayer(0);
//    	for(int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
//    		model.getPlayer(i).evaluateHand().compareTo(model.getPlayer(i+1).evaluateHand());
//    		
//    	}
    	view.getWinnerLabel().setText("Winner: Eren Aslan");
    	if (cardsRequired <= deck.getCardsRemaining()) {
        	for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
        		Player p = model.getPlayer(i);
        		p.discardHand();
        		for (int j = 0; j < Player.HAND_SIZE; j++) {
        			Card card = deck.dealCard();
        			p.addCard(card);
        		}
        		p.evaluateHand();
        		PlayerPane pp = view.getPlayerPane(i);
        		pp.updatePlayerDisplay();
        	}
    	} else {
    			view.getDealButton().setDisable(true);
//            Alert alert = new Alert(AlertType.ERROR, "Not enough cards - shuffle first");
//            alert.showAndWait();
    	}
    	DropShadow shadow = new DropShadow();
    	view.getDealButton().setOnMouseEntered(e ->{
    		view.getDealButton().setEffect(shadow);
    	});
    	view.getDealButton().setOnMouseExited(e ->{
    		view.getDealButton().setEffect(null);
    	});
    }
    
    public void handleProgressNotification(ProgressNotification pn) {
    	view.getProgressBar().setProgress(pn.getProgress());
    }
	
    public void handleStateChangeNotification(StateChangeNotification evt) {
    	if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
    		view.getMyStage().hide();
    	}
    }
}
