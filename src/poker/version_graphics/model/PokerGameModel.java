package poker.version_graphics.model;

import java.util.ArrayList;

import digitalExamClock.ServiceLocator;
import javafx.concurrent.Task;
import poker.version_graphics.PokerGame;
import poker.version_graphics.controller.PokerGameController;
import poker.version_graphics.view.PokerGameView;

public class PokerGameModel {
	private final ArrayList<Player> players = new ArrayList<>();
	private DeckOfCards deck;
	private Player currentWinner;
	private PokerGameView view;
	
	public PokerGameModel() {
		for (int i = 0; i <= 4; i++) {
			players.add(new Player("Player " + i));
		}
		
		deck = new DeckOfCards();
	}

	
	  public void pickWinner() {
			currentWinner = players.get(0);
			for(int j = 0; j < PokerGameController.NUM_PLAYERS; j++) {
				if(currentWinner.compareTo(players.get(j)) < 0) {
					currentWinner = players.get(j);

				}	
//				else  if (currentWinner.compareTo(players.get(i)) == 0){
//						
//					}    	
			}
//			currentWinner.addWin();
//			System.out.println(currentWinner.getPlayerName());
		}
	  
	public Player getCurrentPlayer() {
		return currentWinner;
	}

	  
	public Player getPlayer(int i) {
		return players.get(i);
	}

	public DeckOfCards getDeck() {
		return deck;
	}
}
