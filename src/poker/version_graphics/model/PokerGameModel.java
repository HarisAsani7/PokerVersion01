package poker.version_graphics.model;

import java.util.ArrayList;

import digitalExamClock.ServiceLocator;
import javafx.concurrent.Task;
import poker.version_graphics.PokerGame;
import poker.version_graphics.view.PokerGameView;

public class PokerGameModel {
	private final ArrayList<Player> players = new ArrayList<>();
	private DeckOfCards deck;
	private Player player;
	private PokerGameView view;
	
	public PokerGameModel() {
		for (int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
			players.add(new Player("Player " + i));
		}
		
		deck = new DeckOfCards();
	}

	
//	ich wird do wahrschienlich e ladescreen hinzuefüege bevor poker überhaupt startet	
//	final Task<Void> initializer = new Task<Void>() {
//		protected Void call() throws Exception{
//			Integer i = 0;
//			for (; i<10000000; i++) {
//				if((i%10000) == 0)
//					this.updateProgress(i, 100000);
//			}
//			
//			return null;
//		}
//	};
//	
//	public void initialize() {
//		new Thred(initializer).start();
//		
//	}
	  public void pickWinner() {
			Player currentWinner = players.get(0);
			for(int i = 0; i < PokerGame.NUM_PLAYERS; i++) {
				if(currentWinner.compareTo(players.get(i)) < 0) {
					currentWinner = players.get(i);

				}	
//				else  if (currentWinner.compareTo(players.get(i)) == 0){
//						
//					}    	
			}
			System.out.println(currentWinner.getPlayerName());
//			view.getWinnerLabel().setText("Winner is: " + currentWinner.getPlayerName());
		}
	  
	public Player getPlayer(int i) {
		return players.get(i);
	}

	public DeckOfCards getDeck() {
		return deck;
	}
}
